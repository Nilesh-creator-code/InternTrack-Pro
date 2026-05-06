package com.example.Smart_Education.controller.applicationController;

import com.example.Smart_Education.DTOs.industryDtoPackage.industryDTO.IndustryApplicationResponseDTO;
import com.example.Smart_Education.DTOs.studentDtoPackage.studentDTO.ApplicationDTO;
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

    //This is student api
    /* apply for internship */
    @PostMapping("/student/apply")
    public ResponseEntity<?> apply(@ModelAttribute ApplicationDTO dto) {
        return ResponseEntity.ok(applicationService.applyForInternship(dto));
    }


//    /* get student's applications where they have applied */
//    @GetMapping("/applications")
//    public ResponseEntity<List<ApplicationResponseDTO>> getStudentApplications(Authentication authentication) {
//        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
//        String email = userDetails.getUsername(); // Assuming username is the email
//
//        List<ApplicationResponseDTO> applications = applicationService.getApplicationsForMyInternship(email);
//        return ResponseEntity.ok(applications);
//    }

//For the industry
    @GetMapping("/industry/applications")
    public List<IndustryApplicationResponseDTO> getMyApplications() {
        return applicationService.getApplicationsForMyIndustry();
    }

}
