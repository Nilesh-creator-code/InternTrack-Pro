package com.example.Smart_Education.DTOs.industryDtoPackage.industryDTO;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class IndustryApplicationResponseDTO {

    // Application info
    private Long applicationId;
    private LocalDate applicationDate;
    private String status;

    // Student info
    private Long studentId;
    private String studentName;
    private String studentEmail;

    // Internship info
    private Long internshipId;
    private String internshipTitle;
}
