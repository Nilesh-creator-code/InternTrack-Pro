package com.example.Smart_Education.service.industryService;

import com.example.Smart_Education.entity.industry_entity.Industry;
import com.example.Smart_Education.repository.mysql.industry.IndustryRepository;
import org.springframework.beans.factory.annotation.Autowired;

import com.example.Smart_Education.DTOs.industryDTO.IndustryProfileDTO;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class IndustryService {

    @Autowired
    private IndustryRepository industryRepository;


    /* Get industry profile */
    public IndustryProfileDTO getIndustryProfile() {
        // Implementation to retrieve industry profile based on email
        String industryEmail = SecurityContextHolder.getContext().getAuthentication().getName();

        Optional<Industry> industry = industryRepository.findByUserEmail(industryEmail);


        return IndustryProfileDTO.builder()
                .name(industry.get().getName())
                .title(industry.get().getTitle())
                .contactNumber(industry.get().getContactNumber())
                .address(industry.get().getAddress())
                .aboutUs(industry.get().getAboutUs())
                .description(industry.get().getDescription())
                .LocalDateTime(industry.get().getCreatedAt().toString())
                .build();



    }

}
