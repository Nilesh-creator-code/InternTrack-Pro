package com.example.Smart_Education.controller.applicationController;

import com.example.Smart_Education.DTOs.industryDtoPackage.industryDTO.IndustryApplicationResponseDTO;
import com.example.Smart_Education.DTOs.studentDtoPackage.studentDTO.ApplicationDTO;
import com.example.Smart_Education.DTOs.studentDtoPackage.studentDTO.StudentApplicationViewDTO;
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

    //This is student api
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



    //For the industry
    @GetMapping("/industry/applications")
    public List<IndustryApplicationResponseDTO> getMyApplications() {
        return applicationService.getApplicationsForMyIndustry();
    }

}
