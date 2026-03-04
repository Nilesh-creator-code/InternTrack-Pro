package com.example.Smart_Education.controller.authController;


import com.example.Smart_Education.DTOs.AuthResponse;
import com.example.Smart_Education.DTOs.IndustryRegisterDTO;
import com.example.Smart_Education.DTOs.LoginRequest;
import com.example.Smart_Education.DTOs.OtpRequest;
import com.example.Smart_Education.entity.OTP.OtpVerificationResponse;
import com.example.Smart_Education.service.authService.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth/industry")
@RequiredArgsConstructor
public class IndustryAuthController {

    private final AuthService authService;


    //1 They will send the otp
    @PostMapping("/send-otp")
    public ResponseEntity<?> sendOtp(@RequestParam String email) {

        String response = authService.sendRegistrationOtp(email);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(response);
    }


    //2 Verify the otp
    @PostMapping("/verify-otp")
    public ResponseEntity<OtpVerificationResponse> verifyOtp(
            @Valid @RequestBody OtpRequest request) {

        String verificationToken =
                authService.verifyAndGenerateToken(
                        request.getEmail(),
                        request.getOtp()
                );

        OtpVerificationResponse response =
                new OtpVerificationResponse(true, verificationToken);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(response);
    }

    /*3.  Register Industry */
    @PostMapping("/register")
    public ResponseEntity<String> registerIndustry(@Valid  @RequestBody IndustryRegisterDTO dto) {
        String response = authService.registerIndustry(dto);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(
            @Valid @RequestBody LoginRequest request) {

        AuthResponse response = authService.login(request);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(response);
    }


}
