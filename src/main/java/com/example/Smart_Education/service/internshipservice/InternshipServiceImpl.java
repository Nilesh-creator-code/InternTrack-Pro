package com.example.Smart_Education.service.internshipservice;

import com.example.Smart_Education.DTOs.industryDTO.internshipDTO.IndustryInternshipResponseDTO;
import com.example.Smart_Education.DTOs.industryDTO.internshipDTO.InternshipCreateDTO;
import com.example.Smart_Education.DTOs.industryDTO.internshipDTO.InternshipResposeDTO;
import com.example.Smart_Education.entity.industry_entity.Industry;
import com.example.Smart_Education.entity.industry_entity.Internship;
import com.example.Smart_Education.entity.industry_entity.InternshipDetails;
import com.example.Smart_Education.entity.industry_entity.InternshipStatus;
import com.example.Smart_Education.entity.student_entity.Application;
import com.example.Smart_Education.entity.student_entity.Applicationstatus;
import com.example.Smart_Education.entity.student_entity.Student;
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
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class InternshipServiceImpl implements InternshipService{

    //Mysql repo
    private final InternshipRepository internshipRepository;
    //Mongodb repo
    private final InternshipDetailsRepository detailsRepository;
    //MySql repo
    private final IndustryRepository industryRepository;
    //Student repo
    private final StudentRepository studentRepository;
    //Application repo
    private final ApplicationRepository applicationRepository;

    

    @Transactional
    @Override
    public void createInternship(InternshipCreateDTO dto, String email) {

        if (dto.getEndDate().isBefore(dto.getStartDate())) {
            throw new RuntimeException("End date must be after start date");
        }

        if (dto.getLastDateToApply().isAfter(dto.getStartDate())) {
            throw new RuntimeException("Last date to apply must be before start date");
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
    public List<InternshipResposeDTO> getAllInternships() {

        List<Internship> internships = internshipRepository.findAll();

        if (internships.isEmpty()) {
            throw new ResourceNotFoundException("No internships found");
        }

        return internships.stream()
                .map(internship -> InternshipResposeDTO.builder()
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

    /* Get internship by ID */
    @Transactional
    @Override
    public IndustryInternshipResponseDTO getInternshipById(Long id) {
        Internship internship = internshipRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Internship not found"));

        InternshipDetails details = detailsRepository.findByInternshipId(internship.getId())
                .orElseThrow(() -> new RuntimeException("Internship details not found"));

        return IndustryInternshipResponseDTO.builder()
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
    public List<InternshipResposeDTO> getInternshipsByDomain(String domain) {
        List<Internship> internships = internshipRepository.findByDomainIgnoreCase(domain);
        return internships.stream()
                .map(internship -> InternshipResposeDTO.builder()
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

    /*Update internship*/
    @Transactional
    @Override
    public IndustryInternshipResponseDTO updateInternship(Long id, InternshipCreateDTO dto, String email) {

        Industry industry = industryRepository
                .findByUserEmail(email)
                .orElseThrow(() -> new RuntimeException("Industry not found"));

        Internship internship = internshipRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Internship not found"));

        // Ownership validation
        if (!internship.getIndustry().getId().equals(industry.getId())) {
            throw new RuntimeException("You are not authorized to update this internship");
        }

        // Date validation
        if (dto.getEndDate().isBefore(dto.getStartDate())) {
            throw new RuntimeException("End date must be after start date");
        }

        if (dto.getLastDateToApply().isAfter(dto.getStartDate())) {
            throw new RuntimeException("Last date to apply must be before start date");
        }

        // Update SQL data
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

        // Update MongoDB details
        InternshipDetails details = detailsRepository.findByInternshipId(id)
                .orElseThrow(() -> new RuntimeException("Internship details not found"));

        details.setFullDescription(dto.getFullDescription());
        details.setSkillsRequired(dto.getSkillsRequired());
        details.setResponsibilities(dto.getResponsibilities());

        detailsRepository.save(details);

        return getInternshipById(id);
    }

    public Page<InternshipResposeDTO> getAllInternshipsByPage(int page, int size){

        Pageable pageable = PageRequest.of(page, size, Sort.by("id").descending());

        Page<Internship> internshipPage = internshipRepository.findAll(pageable);

        return internshipPage.map(internship ->
                InternshipResposeDTO.builder()
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
                        .build()
        );
    }

@Override
public String applyForInternship(Long internshipId) {

    // Get logged-in user
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    String email = authentication.getName();

    // Assuming StudentRepository and ApplicationRepository are injected
    // private final StudentRepository studentRepository;
    // private final ApplicationRepository applicationRepository;

    Student student = studentRepository.findByUser_Email(email)
            .orElseThrow(() -> new RuntimeException("Student not found"));

    Internship internship = internshipRepository.findById(internshipId)
            .orElseThrow(() -> new RuntimeException("Internship not found"));

    if (internship.getStatus() != InternshipStatus.OPEN || internship.getLastDateToApply().isBefore(LocalDate.now())) {
        throw new RuntimeException("Cannot apply for this internship");
    }

    if (applicationRepository.existsByStudentAndInternship(student, internship)) {
        throw new RuntimeException("Already applied for this internship");
    }

Application application = Application.builder()
        .student(student)
        .internship(internship)
        .status(Applicationstatus.APPLIED)
        .applicationDate(LocalDate.now())
        .build();

applicationRepository.save(application);

    return "Applied for internship successfully";
}


}