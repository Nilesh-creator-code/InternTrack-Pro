package com.example.Smart_Education.service.internshipservice;

import com.example.Smart_Education.DTOs.industryDtoPackage.internshipDTO.InternshipDetailDTO;
import com.example.Smart_Education.DTOs.industryDtoPackage.internshipDTO.InternshipCreateDTO;
import com.example.Smart_Education.DTOs.industryDtoPackage.internshipDTO.InternshipListDTO;
import com.example.Smart_Education.DTOs.industryDtoPackage.internshipDTO.UpdateInternshipDTO;
import com.example.Smart_Education.entity.industry_entity.Industry;
import com.example.Smart_Education.entity.industry_entity.Internship;
import com.example.Smart_Education.entity.industry_entity.InternshipDetails;
import com.example.Smart_Education.entity.industry_entity.InternshipStatus;
import com.example.Smart_Education.exception.ResourceNotFoundException;
import com.example.Smart_Education.repository.mongodb.industry.InternshipDetailsRepository;
import com.example.Smart_Education.repository.mysql.industry.IndustryRepository;
import com.example.Smart_Education.repository.mysql.industry.InternshipRepository;
import com.example.Smart_Education.repository.mysql.student.ApplicationRepository;
import com.example.Smart_Education.repository.mysql.student.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class InternshipServiceImpl implements InternshipService {

        // Mysql repo
        private final InternshipRepository internshipRepository;
        // Mongodb repo
        private final InternshipDetailsRepository detailsRepository;
        // MySql repo
        private final IndustryRepository industryRepository;
        // Student repo
        private final StudentRepository studentRepository;
        // Application repo
        private final ApplicationRepository applicationRepository;


        /* This is for  industry to create internship */
        @Transactional
        @Override
        public void createInternship(InternshipCreateDTO dto, String email) {

                if (dto.getShortDescription().length() > 255) {
                        throw new IllegalArgumentException("Too long");
                }

                if (dto.getEndDate().isBefore(dto.getStartDate())) {
                        throw new IllegalArgumentException("Application end must be after start");
                }

                if (dto.getLastDateToApply().isAfter(dto.getEndDate())) {
                        throw new IllegalArgumentException("Last date must be before application end");
                }


                Industry industry = industryRepository
                                .findByUserEmail(email)
                                .orElseThrow(() -> new RuntimeException("Industry not found"));

                Internship internship = Internship.builder()
                                .title(dto.getTitle())
                                .shortDescription(dto.getShortDescription())
                                .domain(dto.getDomain())
                                .stipend(dto.getStipend())
                                .location(dto.getLocation())
                                .startDate(dto.getStartDate())
                                .endDate(dto.getEndDate())
                                .lastDateToApply(dto.getLastDateToApply())
                                .type(dto.getType())
                                .status(InternshipStatus.OPEN)
                                .industry(industry)
                                .build();

                Internship savedInternship = internshipRepository.save(internship);

                InternshipDetails details = InternshipDetails.builder()
                                .internshipId(savedInternship.getId())
                                .fullDescription(dto.getFullDescription())
                                .skillsRequired(dto.getSkillsRequired())
                                .responsibilities(dto.getResponsibilities())
                                .build();

                detailsRepository.save(details);
        }


        @Transactional
        @Override
        public InternshipDetailDTO updateInternship(Long id, UpdateInternshipDTO dto) {

                // 🔐 Get logged-in user
                String email = SecurityContextHolder.getContext()
                        .getAuthentication()
                        .getName();

                Industry industry = industryRepository
                        .findByUserEmail(email)
                        .orElseThrow(() -> new RuntimeException("Industry not found"));

                // 🔐 Fetch only own internship
                Internship internship = internshipRepository
                        .findByIdAndIndustryId(id, industry.getId())
                        .orElseThrow(() -> new RuntimeException("Internship not found or unauthorized"));

                // ===== Update SQL Fields =====
                internship.setTitle(dto.getTitle());
                internship.setShortDescription(dto.getShortDescription());
                internship.setDomain(dto.getDomain());
                internship.setStipend(dto.getStipend());
                internship.setLocation(dto.getLocation());
                internship.setStartDate(dto.getStartDate());
                internship.setEndDate(dto.getEndDate());
                internship.setLastDateToApply(dto.getLastDateToApply());
                internship.setType(dto.getType());

                internshipRepository.save(internship);

                // ===== Update MongoDB Fields =====
                InternshipDetails details = detailsRepository
                        .findByInternshipId(internship.getId())
                        .orElseThrow(() -> new RuntimeException("Internship details not found"));

                details.setFullDescription(dto.getFullDescription());
                details.setSkillsRequired(dto.getSkillsRequired());
                details.setResponsibilities(dto.getResponsibilities());

                detailsRepository.save(details);

                // ===== Return Response =====
                return InternshipDetailDTO.builder()
                        .id(internship.getId())
                        .title(internship.getTitle())
                        .shortDescription(internship.getShortDescription())
                        .domain(internship.getDomain())
                        .stipend(internship.getStipend())
                        .location(internship.getLocation())
                        .startDate(internship.getStartDate())
                        .endDate(internship.getEndDate())
                        .lastDateToApply(internship.getLastDateToApply())
                        .type(internship.getType())

                        .fullDescription(details.getFullDescription())
                        .skillRequired(details.getSkillsRequired())
                        .responsibilities(details.getResponsibilities())
                        .build();
        }

        /* Delete internship */
        @Transactional
        @Override
        public String deleteInternship(Long id, String email) {

                Industry industry = industryRepository
                        .findByUserEmail(email)
                        .orElseThrow(() -> new RuntimeException("Industry not found"));

                Internship internship = internshipRepository.findById(id)
                        .orElseThrow(() -> new RuntimeException("Internship not found"));

                // Ownership check
                if (!internship.getIndustry().getId().equals(industry.getId())) {
                        throw new RuntimeException("You are not authorized to delete this internship");
                }

                // delete MongoDB details
                detailsRepository.deleteByInternshipId(id);

                // delete SQL record
                internshipRepository.deleteById(id);

                return "Internship deleted successfully";
        }

        //Get all internship
        @Override
        public List<InternshipListDTO> getMyInternships(String email) {
                Industry industry = industryRepository.findByUserEmail(email)
                        .orElseThrow(() -> new RuntimeException("Industry not found"));
                List<Internship> internships = internshipRepository.findByIndustryId(industry.getId());

                if (internships.isEmpty()) {
                        throw new ResourceNotFoundException("No internships found for this industry");
                }

                return internships.stream()
                        .map(internship -> InternshipListDTO.builder()
                                .id(internship.getId())
                                .title(internship.getTitle())
                                .shortDescription(internship.getShortDescription())
                                .domain(internship.getDomain())
                                .stipend(internship.getStipend())
                                .location(internship.getLocation())
                                .startDate(internship.getStartDate())
                                .endDate(internship.getEndDate())
                                .lastDateToApply(internship.getLastDateToApply())
                                .type(internship.getType())
                                .build())
                        .toList();
        }


        /* Get internship detail by ID for the industry */
        @Override
        public InternshipDetailDTO getInternshipDetailById(Long id) {

                String email = SecurityContextHolder.getContext()
                        .getAuthentication()
                        .getName();

                Industry industry = industryRepository
                        .findByUserEmail(email)
                        .orElseThrow(() -> new RuntimeException("Industry not found"));

                Internship internship = internshipRepository.findById(id)
                        .orElseThrow(() -> new RuntimeException("Internship not found"));

                if (!internship.getIndustry().getId().equals(industry.getId())) {
                        throw new RuntimeException("You are not authorized to view this internship");
                }


                InternshipDetails details = detailsRepository.findByInternshipId(internship.getId())
                        .orElseThrow(() -> new RuntimeException("Internship details not found"));

                return InternshipDetailDTO.builder()
                        .id(internship.getId())
                        .title(internship.getTitle())
                        .shortDescription(internship.getShortDescription())
                        .domain(internship.getDomain())
                        .stipend(internship.getStipend())
                        .location(internship.getLocation())
                        .startDate(internship.getStartDate())
                        .endDate(internship.getEndDate())
                        .lastDateToApply(internship.getLastDateToApply())
                        .type(internship.getType())

                        /* MongoDB data */
                        .fullDescription(details.getFullDescription())
                        .skillRequired(details.getSkillsRequired())
                        .responsibilities(details.getResponsibilities())
                        .build();
        }




        //Student functionalities
        //This is for student to get all internship
        @Transactional
        @Override
        public List<InternshipListDTO> getAllInternships() {

                List<Internship> internships = internshipRepository.findAll();

                if (internships.isEmpty()) {
                        throw new ResourceNotFoundException("No internships found");
                }

                return internships.stream()
                                .map(internship -> InternshipListDTO.builder()
                                                .id(internship.getId())
                                                .title(internship.getTitle())
                                                .shortDescription(internship.getShortDescription())
                                                .domain(internship.getDomain())
                                                .stipend(internship.getStipend())
                                                .location(internship.getLocation())
                                                .startDate(internship.getStartDate())
                                                .endDate(internship.getEndDate())
                                                .lastDateToApply(internship.getLastDateToApply())
                                                .type(internship.getType())
                                                .build())
                                .toList();
        }


        /* Get internship detail by ID for the student */
        @Transactional
        @Override
        public InternshipDetailDTO getInternshipById(Long id) {

                String email = SecurityContextHolder.getContext()
                                .getAuthentication()
                                .getName();

                Internship internship = internshipRepository.findById(id)
                                .orElseThrow(() -> new RuntimeException("Internship not found"));

                Industry industry = industryRepository
                                .findById(internship.getIndustry().getId())
                                .orElseThrow(() -> new RuntimeException("Industry not found"));

                if (!internship.getIndustry().getId().equals(industry.getId())) {
                        throw new RuntimeException("You are not authorized to view this internship");
                }

                InternshipDetails details = detailsRepository.findByInternshipId(internship.getId())
                                .orElseThrow(() -> new RuntimeException("Internship details not found"));


                return InternshipDetailDTO.builder()
                                .id(internship.getId())
                                .title(internship.getTitle())
                                .shortDescription(internship.getShortDescription())
                                .domain(internship.getDomain())
                                .stipend(internship.getStipend())
                                .location(internship.getLocation())
                                .startDate(internship.getStartDate())
                                .endDate(internship.getEndDate())
                                .lastDateToApply(internship.getLastDateToApply())
                                .type(internship.getType())

                                /* MongoDB data */
                                .fullDescription(details.getFullDescription())
                                .skillRequired(details.getSkillsRequired())
                                .responsibilities(details.getResponsibilities())
                                .build();
        }

        /* Get internship by domain */
        @Transactional
        @Override
        public List<InternshipListDTO> getInternshipsByDomain(String domain) {
                List<Internship> internships = internshipRepository.findByDomainIgnoreCase(domain);
                return internships.stream()
                                .map(internship -> InternshipListDTO.builder()
                                                .id(internship.getId())
                                                .title(internship.getTitle())
                                                .shortDescription(internship.getShortDescription())
                                                .domain(internship.getDomain())
                                                .stipend(internship.getStipend())
                                                .location(internship.getLocation())
                                                .startDate(internship.getStartDate())
                                                .endDate(internship.getEndDate())
                                                .lastDateToApply(internship.getLastDateToApply())
                                                .type(internship.getType())
                                                .build())
                                .toList();
        }



        public Page<InternshipListDTO> getAllInternshipsByPage(int page, int size) {

                Pageable pageable = PageRequest.of(page, size, Sort.by("id").descending());

                Page<Internship> internshipPage = internshipRepository.findAll(pageable);

                return internshipPage.map(internship -> InternshipListDTO.builder()
                        .id(internship.getId())
                        .title(internship.getTitle())
                        .shortDescription(internship.getShortDescription())
                        .domain(internship.getDomain())
                        .stipend(internship.getStipend())
                        .location(internship.getLocation())
                        .startDate(internship.getStartDate())
                        .endDate(internship.getEndDate())
                        .lastDateToApply(internship.getLastDateToApply())
                        .type(internship.getType())
                        .build());
        }
}