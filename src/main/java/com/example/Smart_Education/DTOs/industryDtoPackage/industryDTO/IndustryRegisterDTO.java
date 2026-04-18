package com.example.Smart_Education.DTOs.industryDtoPackage.industryDTO;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;


@Data
public class IndustryRegisterDTO {

    // ===== User Fields =====
    @NotBlank(message = "Email is required")
    @Email(message = "Enter valid email")
    private String email;

    @NotBlank(message = "Password is required")
    @Size(min = 8, message = "Password must be at least 8 characters")
    private String password;

    @NotBlank(message = "Contact number is required")
    @Pattern(regexp = "^[0-9]{10}$", message = "Enter valid 10 digit number")
    private String userContactNumber;

    // ===== Industry Fields =====
    @NotBlank(message = "Industry name is required")
    private String name;

    @NotBlank(message = "Industry contact number is required")
    @Pattern(regexp = "^[0-9]{10}$", message = "Enter valid 10 digit number")
    private String industryContactNumber;

    @NotBlank(message = "Title is required")
    private String title;
    private String address;
    private String aboutUs;
    private String description;

    private String verificationToken;

}
