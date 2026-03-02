package com.example.Smart_Education.service.authService;

import com.example.Smart_Education.DTOs.AuthResponse;
import com.example.Smart_Education.DTOs.CollegeRegisterDTO;
import com.example.Smart_Education.DTOs.LoginRequest;
import com.example.Smart_Education.DTOs.StudentRegistrationDTO;
import com.example.Smart_Education.config.JwtService;
import com.example.Smart_Education.entity.EducationStatus;
import com.example.Smart_Education.entity.OTP.Otp;
import com.example.Smart_Education.entity.OTP.OtpVerificationResponse;
import com.example.Smart_Education.entity.Role;
import com.example.Smart_Education.entity.User;
import com.example.Smart_Education.entity.college_entity.College;
import com.example.Smart_Education.entity.student_entity.Student;
import com.example.Smart_Education.repository.mysql.CollegeRepository;
import com.example.Smart_Education.repository.mysql.PasswordResetOtpRepository;
import com.example.Smart_Education.repository.mysql.StudentRepository;
import com.example.Smart_Education.repository.mysql.UserRepository;
import com.example.Smart_Education.service.mailService.EmailService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Random;
import java.util.UUID;


@Service
@RequiredArgsConstructor
public class AuthService {

    private final PasswordEncoder passwordEncoder;

    /*     MYSQL repository */
    private final UserRepository userRepository;

    private final StudentRepository studentRepository;

    private final CollegeRepository collegeRepository;


    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final CustomUserDetailsService userDetailsService;

    //For forgetting the password
    private final PasswordResetOtpRepository otpRepository;
    private final EmailService emailService;


    //    For the registration of the student
    public Student registerStudent(StudentRegistrationDTO dto) {


        // 1️⃣ Check duplicate email
        if (userRepository.existsByEmail(dto.getEmail())) {
            throw new RuntimeException("Email already registered");
        }



        // 1️⃣ Validate Education Status
        if (dto.getEducationStatus() == null) {
            throw new RuntimeException("Education status is required");
        }


        // 3️⃣ Handle College Based on Education Status
        College college = null;

        if (dto.getEducationStatus() == EducationStatus.CURRENT) {
            if (dto.getCollegeId() == null) {
                throw new RuntimeException("College is required for current students");
            }

            college = collegeRepository.findById(dto.getCollegeId())
                    .orElseThrow(() -> new RuntimeException("College not found"));
        }

        // 2. Create User
        User user = User.builder()
                .email(dto.getEmail())
                .password(passwordEncoder.encode(dto.getPassword()))
                .contactNumber(dto.getContactNumber())
                .role(Role.STUDENT)
                .build();

        userRepository.save(user);

        // 4️⃣ Create Student
        Student student = Student.builder()
                .name(dto.getName())
                .department(dto.getDepartment())
                .educationStatus(dto.getEducationStatus())
                .user(user)
                .college(college)
                .build();

        return studentRepository.save(student);
    }


    //For login the student
    public AuthResponse login(LoginRequest request) {

        // 1️⃣ Authenticate user (email + password)
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        // 2️⃣ Fetch user from DB
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));

        // 3️⃣ Load Spring Security UserDetails
        UserDetails userDetails =
                userDetailsService.loadUserByUsername(request.getEmail());

        // 4️⃣ Generate JWT Token
        String token = jwtService.generateToken(userDetails);

        // 5️⃣ Return token + role
        return new AuthResponse(
                token,
                user.getRole().name()
        );
    }


    //For forgetting the password for student they send the otp
    @Transactional
    public void forgotPassword(String email) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Email not found"));

        // 🔥 DELETE old OTP first
        otpRepository.deleteByEmail(email);

        String otp = String.valueOf(
                new Random().nextInt(900000) + 100000
        );

        Otp resetOtp = Otp.builder()
                .email(email)
                .otp(otp)
                .expiryTime(LocalDateTime.now().plusMinutes(5))
                .verified(false)
                .build();

        otpRepository.save(resetOtp);

        emailService.sendOtp(email, otp);
    }

    //For sending otp
    public void sendOtp(String email) {

        // 1️⃣ Check if email already exists
        if (userRepository.existsByEmail(email)) {
            throw new RuntimeException("Email already registered");
        }

        // 2️⃣ Generate OTP
        String otpCode = String.valueOf(new Random().nextInt(900000) + 100000);

        // 3️⃣ Save OTP in DB
        Otp otp = Otp.builder()
                .email(email)
                .otp(otpCode)
                .expiryTime(LocalDateTime.now().plusMinutes(5))
                .verified(false)
                .build();

        otpRepository.save(otp);

        // 4️⃣ Send OTP to email (you will implement mail service)
        emailService.sendOtp(email, otpCode);
    }

    //For verifying the OTP
    @Transactional
    public boolean verifyOtpForStudent(String email, String otp) {
        Otp resetOtp = otpRepository
                .findTopByEmailOrderByIdDesc(email)
                .orElseThrow(() -> new RuntimeException("OTP not found for email: " + email));

        if (resetOtp.isVerified()) {            //this is boolean
            throw new RuntimeException("OTP already verified");
        }

        if (!resetOtp.getOtp().equals(otp)) {
            throw new RuntimeException("Invalid OTP");
        }

        if (resetOtp.getExpiryTime().isBefore(LocalDateTime.now())) {
            throw new RuntimeException("OTP has expired");
        }

        resetOtp.setVerified(true);
        otpRepository.save(resetOtp);

        return true;
    }

    //Reset Password
    public void resetPassword(String email, String newPassword) {

        Otp resetOtp = otpRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("OTP not found"));

        if (!resetOtp.isVerified())
            throw new RuntimeException("OTP not verified");

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);

        otpRepository.delete(resetOtp);
    }

    @Transactional
    public String sendRegistrationOtp(String email) {

        // Check duplicate email
        if (userRepository.existsByEmail(email)) {
            throw new RuntimeException("Email already registered");
        }

        // Delete old OTP if exists
        otpRepository.deleteByEmail(email);

        // Generate 6-digit OTP
        String otpCode = String.valueOf(
                new Random().nextInt(900000) + 100000
        );

        Otp otp = Otp.builder()
                .email(email)
                .otp(otpCode)
                .expiryTime(LocalDateTime.now().plusMinutes(5))
                .verified(false)
                .build();

        otpRepository.save(otp);

        emailService.sendOtp(email, otpCode);

        return "OTP sent successfully";
    }

    //They take email and return response with boolean and token
    @Transactional
    public OtpVerificationResponse verifyOtp(String email, String otpInput) {

        Otp otp = otpRepository
                .findTopByEmailOrderByIdDesc(email)
                .orElseThrow(() ->
                        new RuntimeException("OTP not found for email: " + email)
                );

        if (otp.isVerified()) {
            throw new RuntimeException("OTP already verified");
        }

        if (!otp.getOtp().equals(otpInput)) {
            throw new RuntimeException("Invalid OTP");
        }

        if (otp.getExpiryTime().isBefore(LocalDateTime.now())) {
            throw new RuntimeException("OTP has expired");
        }

        // 🔐 Generate secure verification token
        String verificationToken = UUID.randomUUID().toString();

        otp.setVerified(true);
        otp.setVerificationToken(verificationToken);

        otpRepository.save(otp);

        return new OtpVerificationResponse(true, verificationToken);
    }


    //For the college registration
    public String registerCollege(CollegeRegisterDTO dto) {

        if (userRepository.existsByEmail(dto.getEmail())) {
            throw new RuntimeException("Email already registered");
        }

        Otp otp = otpRepository
                .findTopByEmailOrderByIdDesc(dto.getEmail())
                .orElseThrow(() ->
                        new RuntimeException("Email not verified")
                );

        // 🔐 SECURITY CHECK
        if (!otp.isVerified()
                || otp.getVerificationToken() == null
                || !otp.getVerificationToken().equals(dto.getVerificationToken())) {

            throw new RuntimeException("Invalid verification token");
        }

        // Create User
        User user = User.builder()
                .email(dto.getEmail())
                .password(passwordEncoder.encode(dto.getPassword()))
                .contactNumber(dto.getUserContactNumber())
                .role(Role.COLLEGE)
                .build();

        userRepository.save(user);

        // Create College
        College college = College.builder()
                .name(dto.getName())
                .contactNumber(dto.getCollegeContactNumber())
                .address(dto.getAddress())
                .aboutUs(dto.getAboutUs())
                .description(dto.getDescription())
                .user(user)
                .build();

        collegeRepository.save(college);

        // 🔥 Delete OTP after successful registration
        otpRepository.delete(otp);

        return "College registered successfully";
    }

}
