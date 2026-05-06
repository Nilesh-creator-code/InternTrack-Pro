package com.example.Smart_Education.DTOs.studentDtoPackage.applicationdto.industryApplicationDto;

import com.example.Smart_Education.entity.student_entity.ApplicationStatus;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ApplicationResponseDTO {

    private Long id;
    private LocalDate applicationDate;
    private ApplicationStatus status;

    private String IndustryName;     // Name of the industry offering the internship
    private String InternshipTitle; // Title of the internship
    private String InternshipDomain; // Domain of the internship
    private String InternshipDescription; // Description of the internship

    private Long studentId;       // Reference student by ID
    private Long internshipId;    // Reference internship by ID
    private Long industryId;      // Reference industry by ID

}
