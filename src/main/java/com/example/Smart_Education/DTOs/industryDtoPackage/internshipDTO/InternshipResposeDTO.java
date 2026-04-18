package com.example.Smart_Education.DTOs.industryDtoPackage.internshipDTO;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@Builder
public class InternshipResposeDTO {

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
}
