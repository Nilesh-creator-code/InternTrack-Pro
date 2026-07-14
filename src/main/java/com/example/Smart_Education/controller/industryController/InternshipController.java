package com.example.Smart_Education.controller.industryController;


import com.example.Smart_Education.DTOs.industryDtoPackage.internshipDTO.InternshipDetailDTO;
import com.example.Smart_Education.DTOs.industryDtoPackage.internshipDTO.InternshipCreateDTO;
import com.example.Smart_Education.DTOs.industryDtoPackage.internshipDTO.InternshipListDTO;
import com.example.Smart_Education.DTOs.industryDtoPackage.internshipDTO.UpdateInternshipDTO;
import com.example.Smart_Education.config.CustomUserDetails;
import com.example.Smart_Education.service.authService.AuthService;
import com.example.Smart_Education.service.industryService.internshipservice.InternshipServiceImpl;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/internships")
@AllArgsConstructor
public class InternshipController {

    private final InternshipServiceImpl internshipService;

    private final AuthService authService;

    @GetMapping("/hello")
    public String sayHello() {
        return "Hello from Internship Controller";
    }

    //Here is industry api
    @PostMapping("/industry/create")
    public ResponseEntity<?> createInternship(
            @Valid @RequestBody InternshipCreateDTO dto,
            Authentication authentication
    ) {

        String email = authentication.getName();

        internshipService.createInternship(dto, email);

        return ResponseEntity.ok("Internship created successfully");
    }

    /* Update Internship by industry*/
    @PutMapping("/industry/update/{id}")
    public ResponseEntity<?> updateInternship(
            @PathVariable Long id,
            @Valid @RequestBody UpdateInternshipDTO dto) {

        internshipService.updateInternship(id, dto);

        return ResponseEntity.ok("Internship updated successfully");
    }

    /* Delete internship */
    @DeleteMapping("/industry/delete/{id}")
    public ResponseEntity<?> deleteInternship(@PathVariable Long id, Authentication authentication) {

        String email = authentication.getName();

        internshipService.deleteInternship(id, email);

        return ResponseEntity.ok("Internship deleted successfully");
    }

    /* Get all internships posted by a specific industry */
    @GetMapping("/industry/my-internships")
    public ResponseEntity<List<InternshipListDTO>> getMyInternships(Authentication authentication) {

        CustomUserDetails userDetails =
                (CustomUserDetails) authentication.getPrincipal();

        String email = userDetails.getUsername(); // Assuming username is the email
        List<InternshipListDTO> internships = internshipService.getMyInternships(email);
        return ResponseEntity.ok(internships);
    }

    @GetMapping("/industry/view/{id}")
    public ResponseEntity<InternshipDetailDTO> getInternshipDetailById(@PathVariable Long id) {
        InternshipDetailDTO internship = internshipService.getInternshipDetailById(id);
        return ResponseEntity.ok(internship);
    }


    



    /*Here is student api*/
    //For student to get all internship
    @GetMapping("/student/all")
    public ResponseEntity<List<InternshipListDTO>> getAllInternship() {
            return ResponseEntity.ok(internshipService.getAllInternships());
    }

    //For the student to see particular internship detail
    @GetMapping("/student/view/{id}")
    public ResponseEntity<InternshipDetailDTO> getInternshipById(@PathVariable Long id) {
        InternshipDetailDTO internship = internshipService.getInternshipById(id);
        return ResponseEntity.ok(internship);
    }

    @GetMapping("/student/domain/{domain}")
    public ResponseEntity<List<InternshipListDTO>> getInternshipsByDomain(@PathVariable String domain) {
        List<InternshipListDTO> internships = internshipService.getInternshipsByDomain(domain);
        return ResponseEntity.ok(internships);
    }


    //This is for student to get all internships in the pagination
    @GetMapping("/student/all/pagination")
    public ResponseEntity<List<InternshipListDTO>> getAllInternshipsByPage(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        List<InternshipListDTO> response = internshipService.getAllInternshipsByPage(page, size).getContent();

        return ResponseEntity
                .status(200)
                .body(response);
    }

}
