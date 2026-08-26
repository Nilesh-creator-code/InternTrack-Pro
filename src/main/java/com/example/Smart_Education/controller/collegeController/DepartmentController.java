package com.example.Smart_Education.controller.collegeController;

import com.example.Smart_Education.service.collegeService.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.example.Smart_Education.DTOs.collegeDTO.CollegeDepartmentDTO;

import lombok.AllArgsConstructor;

import org.springframework.http.ResponseEntity;


@RestController
@RequestMapping("/api/departments")
@AllArgsConstructor
public class DepartmentController {

    @Autowired
    private DepartmentService departmentService;


    @GetMapping("/ok")
    public ResponseEntity<String> getOK() {
        return ResponseEntity.ok("College Department is OK");
    }

    @PostMapping("/create")
    public ResponseEntity<String> createDepartment(@RequestBody CollegeDepartmentDTO collegeDepartmentDTO) {
        // Implement logic to create department
        departmentService.createDepartment(collegeDepartmentDTO);

        return ResponseEntity.ok("Department created successfully");
    }

}
