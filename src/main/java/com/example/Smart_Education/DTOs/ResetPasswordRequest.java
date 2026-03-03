package com.example.Smart_Education.DTOs;

import lombok.Data;

@Data
public class ResetPasswordRequest {

    String verificationToken;
    String newPassword;

}
