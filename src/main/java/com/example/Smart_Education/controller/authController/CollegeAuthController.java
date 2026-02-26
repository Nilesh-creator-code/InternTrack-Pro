package com.example.Smart_Education.controller.authController;

import com.example.Smart_Education.DTOs.CollegeRegisterDTO;
import com.example.Smart_Education.DTOs.OtpRequest;
import com.example.Smart_Education.entity.OTP.OtpVerificationResponse;
import com.example.Smart_Education.service.authService.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth/college")
@RequiredArgsConstructor
public class CollegeAuthController {

    private final AuthService authService;

    //1 They will sent the otp
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

        OtpVerificationResponse response =
                authService.verifyOtp(request.getEmail(), request.getOtp());

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(response);
    }

    @PostMapping("/register")
    public ResponseEntity<String> registerCollege(
            @Valid @RequestBody CollegeRegisterDTO dto) {

        String response = authService.registerCollege(dto);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }


}
