package com.example.Smart_Education.DTOs.industryDtoPackage.internshipDTO;

import jakarta.validation.constraints.*;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;


@Data
public class UpdateInternshipDTO {

    // ===== SQL Fields =====
    @NotBlank
    private String title;

    @NotBlank
    private String shortDescription;

    @NotBlank
    private String domain;

    @NotNull
    private BigDecimal stipend;

    @NotBlank
    private String location;

    @NotBlank
    private LocalDate startDate;

    @NotBlank
    private LocalDate endDate;

    @NotBlank
    private LocalDate lastDateToApply;

    @NotBlank
    private String type;

    // ===== Mongo Fields =====
    @NotBlank
    private String fullDescription;

    private List<String> skillsRequired;

    private List<String> responsibilities;
}
