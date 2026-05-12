package com.example.Smart_Education.DTOs.applicationDto;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


//This DTO return student where they have applied
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class StudentApplicationViewDTO {
    private Long applicationId;
    // Internship info (minimal)
    private Long internshipId;
    private String internshipTitle;
    private String domain;
    private String location;

    private String industryName;

    private LocalDate applicationDate;
    private String status;

}
