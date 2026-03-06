package com.example.Smart_Education.controller.industryController;


import com.example.Smart_Education.DTOs.industryDTO.InternshipCreateDTO;
import com.example.Smart_Education.service.authService.AuthService;
import com.example.Smart_Education.service.industryService.InternshipService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

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

}
