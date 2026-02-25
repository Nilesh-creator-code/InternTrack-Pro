package com.example.Smart_Education.controller.authController;


import com.example.Smart_Education.DTOs.AuthResponse;
import com.example.Smart_Education.DTOs.LoginRequest;
import com.example.Smart_Education.entity.student_entity.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.Smart_Education.DTOs.StudentRegistrationDTO;
import com.example.Smart_Education.service.authService.AuthService;


@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthService authService;


    @PostMapping("/register")
    public ResponseEntity<Student> register(@RequestBody StudentRegistrationDTO dto) {
        Student student = authService.registerStudent(dto);
        return ResponseEntity.ok(student);
    }


    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(
            @RequestBody LoginRequest request) {

        return ResponseEntity.ok(authService.login(request));
    }

    //For reseting the password through the otp
    @PostMapping("/forgot-password")
    public ResponseEntity<?> forgotPassword(@RequestParam String email) {
        authService.forgotPassword(email);
        return ResponseEntity.ok("OTP sent to email");
    }

    @PostMapping("/verify-otp")
    public ResponseEntity<?> verifyOtp(@RequestParam String email,
                                       @RequestParam String otp) {
        authService.verifyOtp(email, otp);
        return ResponseEntity.ok("OTP verified");
    }

    @PostMapping("/reset-password")
    public ResponseEntity<?> resetPassword(@RequestParam String email,
                                           @RequestParam String newPassword) {
        authService.resetPassword(email, newPassword);
        return ResponseEntity.ok("Password updated successfully");
    }


}