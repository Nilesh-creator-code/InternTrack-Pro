package com.example.Smart_Education.controller.industryController;


import com.example.Smart_Education.DTOs.industryDTO.internshipDTO.IndustryInternshipResponseDTO;
import com.example.Smart_Education.DTOs.industryDTO.internshipDTO.InternshipCreateDTO;
import com.example.Smart_Education.DTOs.industryDTO.internshipDTO.InternshipResposeDTO;
import com.example.Smart_Education.config.CustomUserDetails;
import com.example.Smart_Education.service.authService.AuthService;
import com.example.Smart_Education.service.internshipservice.InternshipServiceImpl;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/industry/internships")
@AllArgsConstructor
public class InternshipController {

    private final InternshipServiceImpl internshipService;

    private final AuthService authService;

    @GetMapping("/hello")
    public String sayHello() {
        return "Hello from Internship Controller";
    }

    @PostMapping("/create")
    public ResponseEntity<?> createInternship(
            @Valid @RequestBody InternshipCreateDTO dto,
            Authentication authentication
    ) {

        String email = authentication.getName();

        internshipService.createInternship(dto, email);

        return ResponseEntity.ok("Internship created successfully");
    }

    //For student
    @GetMapping("/all")
    public ResponseEntity<List<InternshipResposeDTO>> getAllInternship() {
            return ResponseEntity.ok(internshipService.getAllInternships());
    }

    @GetMapping("/view/{id}")
    public ResponseEntity<IndustryInternshipResponseDTO> getMethodName(@PathVariable Long id) {
        IndustryInternshipResponseDTO internship = internshipService.getInternshipById(id);
        return ResponseEntity.ok(internship);
    }

    @GetMapping("/domain/{domain}")
    public ResponseEntity<List<InternshipResposeDTO>> getInternshipsByDomain(@PathVariable String domain) {
        List<InternshipResposeDTO> internships = internshipService.getInternshipsByDomain(domain);
        return ResponseEntity.ok(internships);
    }
    
    /* Update Internship */
    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateInternship(
            @PathVariable Long id,
            @Valid @RequestBody InternshipCreateDTO dto,
            Authentication authentication
    ) {

        String email = authentication.getName();

        internshipService.updateInternship(id, dto, email);

        return ResponseEntity.ok("Internship updated successfully");
    }

    /* Delete internship */
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteInternship(@PathVariable Long id, Authentication authentication) {

        String email = authentication.getName();

        internshipService.deleteInternship(id, email);

        return ResponseEntity.ok("Internship deleted successfully");
    }   

    @GetMapping("/all/pagination")
    public ResponseEntity<List<InternshipResposeDTO>> getAllInternshipsByPage(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        List<InternshipResposeDTO> response = internshipService.getAllInternshipsByPage(page, size).getContent();

        return ResponseEntity
                .status(200)
                .body(response);
    }

    /* Get all internships posted by a specific industry */
    @GetMapping("/my-internships")
    public ResponseEntity<List<InternshipResposeDTO>> getMyInternships(Authentication authentication) {

        CustomUserDetails userDetails =
        (CustomUserDetails) authentication.getPrincipal();

        String email = userDetails.getUsername(); // Assuming username is the email
        List<InternshipResposeDTO> internships = internshipService.getMyInternships(email);
        return ResponseEntity.ok(internships);
    }

}
