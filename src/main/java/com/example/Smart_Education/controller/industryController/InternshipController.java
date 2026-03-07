package com.example.Smart_Education.controller.industryController;


import com.example.Smart_Education.DTOs.industryDTO.internshipDTO.IndustryInternshipResponseDTO;
import com.example.Smart_Education.DTOs.industryDTO.internshipDTO.InternshipCreateDTO;
import com.example.Smart_Education.DTOs.industryDTO.internshipDTO.InternshipResposeDTO;
import com.example.Smart_Education.service.authService.AuthService;
import com.example.Smart_Education.service.industryService.InternshipService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("/api/industry/internships")
@AllArgsConstructor
public class InternshipController {

    private final InternshipService internshipService;  

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

    @GetMapping("/all")
    public ResponseEntity<List<InternshipResposeDTO>> getAllInternship() {
            return ResponseEntity.ok(internshipService.getAllInternships());
    }

    @GetMapping("/{id}")
    public ResponseEntity<IndustryInternshipResponseDTO> getMethodName(@PathVariable Long id) {
        IndustryInternshipResponseDTO internship = internshipService.getInternshipById(id);
        return ResponseEntity.ok(internship);
    }

    @GetMapping("/domain/{domain}")
    public ResponseEntity<List<InternshipResposeDTO>> getInternshipsByDomain(@PathVariable String domain) {
        List<InternshipResposeDTO> internships = internshipService.getInternshipsByDomain(domain);
        return ResponseEntity.ok(internships);
    }

}
