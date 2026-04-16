package com.example.Smart_Education.DTOs.IndustryPackageDto.industryDTO;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class IndustryProfileDTO {
    private String name;
    private String title;
    private String contactNumber;
    private String address;
    private String aboutUs;

    private String description;

    private String LocalDateTime;

    // Getters and Setters
    
}
