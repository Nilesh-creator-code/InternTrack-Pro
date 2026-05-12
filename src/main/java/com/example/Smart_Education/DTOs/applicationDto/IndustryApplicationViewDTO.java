package com.example.Smart_Education.DTOs.applicationDto;

import lombok.Builder;
import lombok.Data;


@Data
@Builder
public class IndustryApplicationViewDTO {

    private Long applicationId;
    private String studentName;
    private String studentEmail;
    private String department;
    private String collegeName;
    private String educationStatus;


    private String location;
    private String resumeLink; // Assuming you want to include the resume file in the DTO
    private String githubLink;
    private String linkedinLink;
    
}
