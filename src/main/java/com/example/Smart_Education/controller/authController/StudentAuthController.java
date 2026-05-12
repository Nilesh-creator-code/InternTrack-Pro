package com.example.Smart_Education.controller.authController;


import com.example.Smart_Education.DTOs.centralAuthDTO.AuthResponse;
import com.example.Smart_Education.DTOs.centralAuthDTO.LoginRequest;
import com.example.Smart_Education.DTOs.centralAuthDTO.OtpRequest;
import com.example.Smart_Education.DTOs.centralAuthDTO.ResetPasswordRequest;
import com.example.Smart_Education.DTOs.studentDtoPackage.StudentRegistrationDTO;
import com.example.Smart_Education.entity.student_entity.Student;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.Smart_Education.service.authService.AuthService;

import java.util.Map;


@RestController
@RequestMapping("/api/auth/student")
public class StudentAuthController {

    @Autowired
    private AuthService authService;


    @PostMapping("/register")
    public ResponseEntity<Student> register(@RequestBody StudentRegistrationDTO dto) {
        Student student = authService.registerStudent(dto);
        return ResponseEntity.status(201).body(student);
    }


    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody LoginRequest request) {
        AuthResponse response = authService.login(request);
        return ResponseEntity.ok(response);
    }

    //For reset the password through the otp
    @PostMapping("/forgot-password")
    public ResponseEntity<?> forgotPassword(@RequestParam String email) {
        authService.forgotPassword(email);
        return ResponseEntity.status(202).body("OTP sent to email");    }

    @PostMapping("/verify-otp")
    public ResponseEntity<?> verifyOtp(@RequestBody OtpRequest otpRequest) {
        String verificationToken = authService.verifyAndGenerateToken(otpRequest.getEmail(), otpRequest.getOtp());
        return ResponseEntity.ok(
                Map.of(
                        "message", "OTP verified successfully",
                        "verificationToken", verificationToken
                )
        );
    }

    @PostMapping("/reset-password")
    public ResponseEntity<?> resetPassword(@RequestBody ResetPasswordRequest request) {
        authService.resetPassword(request.getVerificationToken(), request.getNewPassword());
        return ResponseEntity.ok(Map.of("message", "Password Updated Successfully"));
    }

}