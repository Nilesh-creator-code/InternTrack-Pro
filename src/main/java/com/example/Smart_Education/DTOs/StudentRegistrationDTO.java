package com.example.Smart_Education.DTOs;

import com.example.Smart_Education.entity.EducationStatus;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class StudentRegistrationDTO {

    @NotBlank(message = "Name is required")
    private String name;

    private String department;

    @NotBlank(message = "Contact number is required")
    private String contactNumber;

    private Long collegeId;

    // User fields (Authentication)
    @NotBlank(message = "Email is required")
    @Email(message = "Enter a valid email")
    private String email;

    @NotBlank(message = "Password is required")
    @Size(min = 8, message = "Password must be at least 8 characters long")
    private String password;

    @Enumerated(EnumType.STRING)
    private EducationStatus educationStatus;
}
