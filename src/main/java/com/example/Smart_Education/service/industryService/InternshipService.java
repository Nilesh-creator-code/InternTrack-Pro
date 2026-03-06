package com.example.Smart_Education.service.industryService;

import com.example.Smart_Education.DTOs.industryDTO.internshipDTO.InternshipCreateDTO;
import com.example.Smart_Education.entity.industry_entity.Industry;
import com.example.Smart_Education.entity.industry_entity.Internship;
import com.example.Smart_Education.entity.industry_entity.InternshipDetails;
import com.example.Smart_Education.entity.industry_entity.InternshipStatus;
import com.example.Smart_Education.repository.mongodb.industry.InternshipDetailsRepository;
import com.example.Smart_Education.repository.mysql.industry.IndustryRepository;
import com.example.Smart_Education.repository.mysql.industry.InternshipRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class InternshipService {

    //Mysql repo
    private final InternshipRepository internshipRepository;
    //Mongodb repo
    private final InternshipDetailsRepository detailsRepository;
    //MySql repo
    private final IndustryRepository industryRepository;

    @Transactional
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
    public List<Internship> getAllInternships() {
        if (internshipRepository.count() == 0) {
            throw new RuntimeException("No internships found");
        }
        return internshipRepository.findAll();
    }



}