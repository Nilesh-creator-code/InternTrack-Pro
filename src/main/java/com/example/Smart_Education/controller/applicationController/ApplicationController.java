package com.example.Smart_Education.controller.applicationController;

import com.example.Smart_Education.DTOs.applicationDto.ApplicationDTO;
import com.example.Smart_Education.DTOs.applicationDto.IndustryApplicationViewDTO;
import com.example.Smart_Education.DTOs.applicationDto.InternshipApplicationDTO;
import com.example.Smart_Education.DTOs.applicationDto.StudentApplicationViewDTO;
import com.example.Smart_Education.entity.student_entity.ApplicationStatus;
import com.example.Smart_Education.service.applicationService.ApplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


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


    //This is industry api

    @GetMapping("/industry/hello")
    public String hello() {
        return "This hello is from industry application api";
    }


    
    //For the industry
    /* Get all applications for internship  */
    @GetMapping("/industry/applications")
    public ResponseEntity<List<InternshipApplicationDTO>> getAllApplicationsForIndustry() {
        List<InternshipApplicationDTO> applications = applicationService.getIndustryApplications();
        return ResponseEntity.ok(applications);
    }

     /* Get all applications for internship by internship id*/
     @GetMapping("/industry/applications/{internshipId}")
     public ResponseEntity<List<IndustryApplicationViewDTO>> getApplicationsForInternship(@PathVariable Long internshipId) {
         List<IndustryApplicationViewDTO> applications = applicationService.getInternshipApplication(internshipId);
         return ResponseEntity.ok(applications);
     }

     @PutMapping("/industry/updateApplication/{applicationId}")
     public ApplicationStatus updateApplicationStatus (@PathVariable Long applicationId, @RequestBody ApplicationStatus newStatus){
         return applicationService.updateApplicationStatus(applicationId, newStatus);
     }


}
