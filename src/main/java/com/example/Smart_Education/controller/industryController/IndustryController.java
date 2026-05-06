package com.example.Smart_Education.controller.industryController;

import com.example.Smart_Education.DTOs.industryDtoPackage.industryDTO.IndustryUpdateProfileDTO;
import com.example.Smart_Education.service.industryService.IndustryService;
import com.example.Smart_Education.service.internshipservice.InternshipService;
import com.example.Smart_Education.service.studentSerivce.StudentService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.Smart_Education.DTOs.industryDtoPackage.industryDTO.IndustryApplicationResponseDTO;
import com.example.Smart_Education.entity.student_entity.Student;

import java.util.List;


@RestController
@RequestMapping("/api/industry-controller")
public class IndustryController {
    
    private final InternshipService internshipService;

    private final StudentService studentService;

    private final IndustryService industryService;

    public IndustryController(InternshipService internshipService, StudentService studentService, IndustryService industryService) {
        this.internshipService = internshipService;
        this.studentService = studentService;
        this.industryService = industryService;
    }

    @GetMapping("/hello")
    public String sayHello() {
        return "Hello from Industry Controller";
    }



    @GetMapping("/industry/student")
    public ResponseEntity<Student> getStudentById(@RequestParam Long id) {
        return ResponseEntity.ok(studentService.getStudentById(id));
    }

    /* Get industry profile */
    @GetMapping("/profile")
    public ResponseEntity<?> getIndustryProfile() {
        return ResponseEntity.ok(industryService.getIndustryProfile());
    }

    /* Update industry profile */
    @PutMapping("/profile/update")
    public ResponseEntity<?> updateIndustryProfile(
            @RequestBody IndustryUpdateProfileDTO industryUpdateProfileDTO) {

        return ResponseEntity.ok(
                industryService.updateIndustryProfile(industryUpdateProfileDTO)
        );
    }


}
