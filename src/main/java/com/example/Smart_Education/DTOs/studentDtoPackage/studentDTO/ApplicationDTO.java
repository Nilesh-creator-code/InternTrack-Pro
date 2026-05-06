package com.example.Smart_Education.DTOs.studentDtoPackage.studentDTO;

import jakarta.validation.constraints.*;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

@Data
public class ApplicationDTO {

    @NotBlank
    private String location;

    @NotBlank(message = "Resume is required")
    private MultipartFile resume;

    @Pattern(
            regexp = "^(https?:\\/\\/)?(www\\.)?github\\.com\\/.*$",
            message = "Please enter a valid GitHub URL"
    )
    private String githubLink;

    @Pattern(
            regexp = "^(https?:\\/\\/)?(www\\.)?linkedin\\.com\\/.*$",
            message = "Invalid LinkedIn URL"
    )
    private String linkedinLink;

    @NotNull(message = "Internship ID is required")
    private Long internshipId;
}
