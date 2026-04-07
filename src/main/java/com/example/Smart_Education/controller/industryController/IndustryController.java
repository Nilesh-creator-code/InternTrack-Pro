package com.example.Smart_Education.controller.industryController;

import com.example.Smart_Education.service.internshipservice.InternshipService;
import com.example.Smart_Education.service.studentSerivce.StudentService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.Smart_Education.DTOs.industryDTO.IndustryApplicationResponseDTO;
import com.example.Smart_Education.entity.student_entity.Student;

import java.util.List;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("/api/industry-controller")
public class IndustryController {
    
    private final InternshipService internshipService;

    private final StudentService studentService;

    public IndustryController(InternshipService internshipService, StudentService studentService) {
        this.internshipService = internshipService;
        this.studentService = studentService;
    }

    @GetMapping("/hello")
    public String sayHello() {
        return "Hello from Industry Controller";
    }

    @GetMapping("/industry/applications")
    public List<IndustryApplicationResponseDTO> getMyApplications() {
        return internshipService.getApplicationsForMyIndustry();
    }


    @GetMapping("/industry/student")
    public ResponseEntity<Student> getStudentById(@RequestParam Long id) {
        return ResponseEntity.ok(studentService.getStudentById(id));
    }

}
