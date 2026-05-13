package com.example.Smart_Education.controller.applicationController;

import com.example.Smart_Education.DTOs.applicationDto.ApplicationDTO;
import com.example.Smart_Education.DTOs.applicationDto.IndustryApplicationViewDTO;
import com.example.Smart_Education.DTOs.applicationDto.StudentApplicationViewDTO;
import com.example.Smart_Education.entity.student_entity.ApplicationStatus;
import com.example.Smart_Education.service.applicationService.ApplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;


@RestController
@RequestMapping("/api/applications-controller")
public class ApplicationController {

    @Autowired
    private ApplicationService applicationService;

    @GetMapping("/student/hello")
    public String sayHello() {
        return "This hello is from student application api";
    }


    /* apply for internship */
    @PostMapping("/student/apply")
    public ResponseEntity<?> apply(@ModelAttribute ApplicationDTO dto) {
        return ResponseEntity.ok(applicationService.applyForInternship(dto));
    }

    /* Get a student's applications where they have applied */
    @GetMapping("/student/applications")
    public ResponseEntity<List<StudentApplicationViewDTO>> getMyApplicationsForStudent() {
        return ResponseEntity.ok(applicationService.getApplicationDtoByStudent());
    }


    //This is student api
    @GetMapping("/industry/hello")
    public String hello() {
        return "This hello is from industry application api";
    }

    //For the industry
    @GetMapping("/industry/applications/{internshipId}")
    public ResponseEntity<List<IndustryApplicationViewDTO>> getApplicationsForIndustry(@PathVariable Long internshipId) {
        return ResponseEntity.ok(applicationService.getInternshipApplication(internshipId));
    }
    
    @PutMapping("/industry/updateApplication/{applicationId}")
    public ApplicationStatus updateApplicationStatus(@PathVariable Long applicationId, @RequestBody ApplicationStatus newStatus) {
        return applicationService.updateApplicationStatus(applicationId, newStatus);
    }

}   
