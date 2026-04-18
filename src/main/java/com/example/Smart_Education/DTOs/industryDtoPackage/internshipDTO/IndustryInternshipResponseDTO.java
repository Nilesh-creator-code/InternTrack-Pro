package com.example.Smart_Education.DTOs.industryDtoPackage.internshipDTO;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class IndustryInternshipResponseDTO {

    private Long id;
    private String title;
    private String shortDescription;
    private String domain;
    private BigDecimal stipend;
    private String location;
    private LocalDate startDate;
    private LocalDate endDate;
    private LocalDate lastDateToApply;
    private String type;
    private String fullDescription;
    private List<String> skillRequired;
    private List<String> responsibilities;
}
