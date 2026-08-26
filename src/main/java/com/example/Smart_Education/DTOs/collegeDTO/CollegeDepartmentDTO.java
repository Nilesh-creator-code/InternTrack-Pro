package com.example.Smart_Education.DTOs.collegeDTO;


import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CollegeDepartmentDTO {

    @NotBlank(message = "Name is required")
    private String name;

    @NotBlank(message = "Description is required")
    private String description;

    @NotBlank(message = "Head of Department is required")
    private String headOfDepartment;

    private Long collegeId;
    
}
