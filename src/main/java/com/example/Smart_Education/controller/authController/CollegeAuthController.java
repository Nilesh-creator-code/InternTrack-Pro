package com.example.Smart_Education.controller.authController;

import com.example.Smart_Education.DTOs.centralAuthDTO.AuthResponse;
import com.example.Smart_Education.DTOs.centralAuthDTO.LoginRequest;
import com.example.Smart_Education.DTOs.centralAuthDTO.OtpRequest;
import com.example.Smart_Education.DTOs.centralAuthDTO.ResetPasswordRequest;
import com.example.Smart_Education.DTOs.collegeDTO.CollegeRegisterDTO;
import com.example.Smart_Education.entity.OTP.OtpVerificationResponse;
import com.example.Smart_Education.service.authService.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth/college")
@RequiredArgsConstructor
public class CollegeAuthController {

    private final AuthService authService;

    @GetMapping("/ok")
    public String getHello() {
        return "Hello College api is OK";
    }

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


//    3 register the college
    @PostMapping("/register")
    public ResponseEntity<String> registerCollege(
            @Valid @RequestBody CollegeRegisterDTO dto) {

        String response = authService.registerCollege(dto);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }


    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(
            @Valid @RequestBody LoginRequest request) {

        AuthResponse response = authService.login(request);
        System.out.println("This for the api is coming or not");

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(response);
    }


    /* Forget password */
    @PostMapping("/forgot-password")
    public ResponseEntity<?> forgotPassword(@RequestParam String email) {

        authService.forgotPassword(email);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(Map.of(
                        "message", "OTP sent successfully to registered email"
                ));
    }

    @PostMapping("/verify-reset-otp")
    public ResponseEntity<?> verifyResetOtp(
            @Valid @RequestBody OtpRequest request) {

        String verificationToken =
                authService.verifyAndGenerateToken(
                        request.getEmail(),
                        request.getOtp()
                );

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(Map.of(
                        "message", "OTP verified successfully",
                        "verificationToken", verificationToken
                ));
    }

    @PostMapping("/reset-password")
    public ResponseEntity<?> resetPassword(
            @Valid @RequestBody ResetPasswordRequest request) {

        authService.resetPassword(
                request.getVerificationToken(),
                request.getNewPassword()
        );

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(Map.of(
                        "message", "Password updated successfully"
                ));
    }
    

    
    


}
