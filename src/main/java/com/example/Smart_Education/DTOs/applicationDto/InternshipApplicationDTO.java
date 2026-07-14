package com.example.Smart_Education.DTOs.applicationDto;

import com.example.Smart_Education.entity.student_entity.ApplicationStatus;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class InternshipApplicationDTO {

    private Long applicationId;
    private ApplicationStatus applicationStatus;
    private String studentName;
    private String studentEmail;
    private String department;
    private String collegeName;
    private String educationStatus;


    private String location;
    private String resumeLink; // Assuming you want to include the resume file in the DTO
    private String githubLink;
    private String linkedinLink;

    //Internship detail
    private String domain;
    private String title;

}
