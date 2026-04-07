package com.example.Smart_Education.DTOs.studentDTO;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class StudentApplicationResponseDTO {
    private Long id;
    private LocalDate applicationDate;
    private String status;

    // Internship info (minimal)
    private Long internshipId;
    private String internshipTitle;
    private String companyName;
    
}
