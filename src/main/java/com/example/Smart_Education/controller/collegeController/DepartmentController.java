package com.example.Smart_Education.controller.collegeController;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.Smart_Education.DTOs.collegeDTO.CollegeDepartmentDTO;

import lombok.AllArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/api/departments")
@AllArgsConstructor
public class DepartmentController {


    @PostMapping("/create")
    public ResponseEntity<String> createDepartment(@RequestBody CollegeDepartmentDTO collegeDepartmentDTO) {
        // Implement logic to create department
        return ResponseEntity.ok("Department created successfully");
    }

}
