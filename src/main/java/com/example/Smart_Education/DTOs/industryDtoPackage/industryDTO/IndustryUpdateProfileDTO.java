package com.example.Smart_Education.DTOs.industryDtoPackage.industryDTO;

import lombok.Builder;
import lombok.Data;


@Data
@Builder
public class IndustryUpdateProfileDTO {

    private String title;
    private String contactNumber;
    private String address;
    private String aboutUs;

    private String description;
    
}
