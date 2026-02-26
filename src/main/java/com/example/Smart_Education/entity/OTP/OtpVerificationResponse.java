package com.example.Smart_Education.entity.OTP;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class OtpVerificationResponse {
    private boolean verified;
    private String verificationToken;
}
