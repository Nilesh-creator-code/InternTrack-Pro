package com.example.Smart_Education.DTOs.centralAuthDTO;

import lombok.Data;

@Data
public class ResetPasswordRequest {

    String verificationToken;
    String newPassword;

}
