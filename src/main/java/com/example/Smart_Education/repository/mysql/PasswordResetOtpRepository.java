package com.example.Smart_Education.repository.mysql;

import com.example.Smart_Education.entity.OTP.PasswordResetOtp;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PasswordResetOtpRepository
        extends JpaRepository<PasswordResetOtp, Long> {

    Optional<PasswordResetOtp> findByEmail(String email);

    void deleteByEmail(String email);

    Optional<PasswordResetOtp> findTopByEmailOrderByIdDesc(String email);

}
