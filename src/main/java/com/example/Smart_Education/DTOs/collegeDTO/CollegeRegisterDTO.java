package com.example.Smart_Education.DTOs.collegeDTO;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CollegeRegisterDTO {

    // ===== User Fields =====
    @NotBlank(message = "Email is required")
    @Email(message = "Enter valid email")
    private String email;

    @NotBlank(message = "Password is required")
    @Size(min = 8, message = "Password must be at least 8 characters")
    private String password;

    @NotBlank(message = "Contact number is required")
    private String userContactNumber;

    // ===== College Fields =====
    @NotBlank(message = "College name is required")
    private String name;

    @NotBlank(message = "College contact number is required")
    private String collegeContactNumber;

    private String address;
    private String aboutUs;
    private String description;

    private String verificationToken;

}
