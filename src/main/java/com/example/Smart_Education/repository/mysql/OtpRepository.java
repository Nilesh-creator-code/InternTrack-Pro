package com.example.Smart_Education.repository.mysql;

import com.example.Smart_Education.entity.OTP.Otp;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OtpRepository extends JpaRepository<Otp, Long> {

    Optional<Otp> findByEmail(String email);

    void deleteByEmail(String email);

    Optional<Otp> findTopByEmailOrderByIdDesc(String email);

    Optional<Otp> findByVerificationToken(String verificationToken);


}
