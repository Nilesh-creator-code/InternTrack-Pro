package com.example.Smart_Education.service.industryService;

import com.example.Smart_Education.entity.industry_entity.Industry;
import com.example.Smart_Education.repository.mysql.industry.IndustryRepository;

import org.springframework.beans.factory.annotation.Autowired;

import com.example.Smart_Education.DTOs.IndustryPackageDto.industryDTO.IndustryProfileDTO;
import com.example.Smart_Education.DTOs.IndustryPackageDto.industryDTO.IndustryUpdateProfileDTO;

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


    /* Update indsutry */
    public String updateIndustryProfile(IndustryUpdateProfileDTO industryUpdateProfileDTO) {
        // Implementation to update industry profile based on email
        String industryEmail = SecurityContextHolder.getContext().getAuthentication().getName();

        Optional<Industry> industryOptional = industryRepository.findByUserEmail(industryEmail);

        if (industryOptional.isPresent()) {
            Industry industry = industryOptional.get();
            industry.setTitle(industryUpdateProfileDTO.getTitle());
            industry.setContactNumber(industryUpdateProfileDTO.getContactNumber());
            industry.setAddress(industryUpdateProfileDTO.getAddress());
            industry.setAboutUs(industryUpdateProfileDTO.getAboutUs());
            industry.setDescription(industryUpdateProfileDTO.getDescription());

            industryRepository.save(industry);
        }

        return "Industry profile updated successfully";
    }



}
