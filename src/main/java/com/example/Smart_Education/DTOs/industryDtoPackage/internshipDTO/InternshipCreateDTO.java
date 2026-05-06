package com.example.Smart_Education.DTOs.industryDtoPackage.internshipDTO;

import jakarta.validation.constraints.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InternshipCreateDTO {

    // ===== SQL Fields =====
    @NotNull
    @Pattern(regexp = "^[A-Za-z ]+$", message = "Name must contain only letters")
    private String name;

    @NotBlank
    private String title;

    @NotBlank
    @Size(min = 3, max = 100, message = "Title must be between 3 and 100 characters")
    private String shortDescription;

    @NotBlank
    private String domain;

    @NotNull
    private BigDecimal stipend;

    @NotBlank
    private String location;

    @NotNull
    @FutureOrPresent(message = "Start date must be today or future")
    private LocalDate startDate;

    @NotNull
    @Future(message = "End date must be in the future")
    private LocalDate endDate;

    @NotNull
    @FutureOrPresent(message = "Last date to apply must be today or future")
    private LocalDate lastDateToApply;

    @NotBlank
    private String type;

    // ===== Mongo Fields =====
    @NotBlank
    private String fullDescription;

    private List<String> skillsRequired;

    private List<String> responsibilities;
}