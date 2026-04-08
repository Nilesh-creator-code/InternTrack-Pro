package com.example.Smart_Education.DTOs.studentDTO;


import com.example.Smart_Education.entity.student_entity.EducationStatus;
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

    private String collegeName;

    // User fields (Authentication)
    @NotBlank(message = "Email is required")
    @Email(message = "Enter a valid email")
    private String email;

    @NotBlank(message = "Password is required")
    @Size(min = 8, message = "Password must be at least 8 characters long")
    private String password;


    @NotNull(message = "Education status is required")
    private EducationStatus educationStatus;
}
