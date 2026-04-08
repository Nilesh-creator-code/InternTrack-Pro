package com.example.Smart_Education.service.authService;

import com.example.Smart_Education.DTOs.centralAuthDTO.AuthResponse;
import com.example.Smart_Education.DTOs.centralAuthDTO.LoginRequest;
import com.example.Smart_Education.DTOs.collegeDTO.CollegeRegisterDTO;
import com.example.Smart_Education.DTOs.industryDTO.IndustryRegisterDTO;
import com.example.Smart_Education.DTOs.studentDTO.StudentRegistrationDTO;
import com.example.Smart_Education.config.JwtService;
import com.example.Smart_Education.entity.student_entity.EducationStatus;
import com.example.Smart_Education.entity.OTP.Otp;
import com.example.Smart_Education.entity.Role;
import com.example.Smart_Education.entity.User;
import com.example.Smart_Education.entity.college_entity.College;
import com.example.Smart_Education.entity.industry_entity.Industry;
import com.example.Smart_Education.entity.student_entity.Student;
import com.example.Smart_Education.repository.mysql.*;
import com.example.Smart_Education.repository.mysql.college.CollegeRepository;
import com.example.Smart_Education.repository.mysql.industry.IndustryRepository;
import com.example.Smart_Education.repository.mysql.student.StudentRepository;
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

    private final IndustryRepository industryRepository;


    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final CustomUserDetailsService userDetailsService;

    //For forgetting the password
    private final OtpRepository otpRepository;
    private final EmailService emailService;


    //    For the registration of the student
    @Transactional
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
//        College college = null;

//        if (dto.getEducationStatus() == EducationStatus.CURRENT) {
//            if (dto.getCollegeId() == null) {
//                throw new RuntimeException("College is required for current students");
//            }
//
//            college = collegeRepository.findById(dto.getCollegeId())
//                    .orElseThrow(() -> new RuntimeException("College not found"));
//        }

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
                .CollegeName(dto.getCollegeName())
                .build();

        return studentRepository.save(student);
    }


    //For login the industry and student
    @Transactional
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

        // DELETE old OTP first
        otpRepository.deleteByEmail(email);

        String otp = String.valueOf(
                new Random().nextInt(900000) + 100000
        );

        Otp resetOtp = Otp.builder()
                .email(email)
                .otp(otp)
                .expiryTime(LocalDateTime.now().plusMinutes(5))
                .verified(false)
                .verificationToken(null)
                .tokenExpiryTime(null)
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

    //Reset Password
    @Transactional
    public void resetPassword(String verificationToken, String newPassword) {

        //  FIND BY TOKEN (NOT EMAIL)
        Otp resetOtp = otpRepository
                .findByVerificationToken(verificationToken)
                .orElseThrow(() -> new RuntimeException("Invalid verification token"));

        //  CHECK TOKEN EXPIRY
        if (resetOtp.getTokenExpiryTime().isBefore(LocalDateTime.now())) {
            throw new RuntimeException("Reset token expired");
        }

        //  CHECK IF OTP WAS VERIFIED
        if (!resetOtp.isVerified()) {
            throw new RuntimeException("OTP not verified");
        }

        // GET USER
        User user = userRepository.findByEmail(resetOtp.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));

        // 🔥 UPDATE PASSWORD
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);

        // 🔥🔥🔥 DELETE OTP RECORD AFTER SUCCESS
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
    public String verifyAndGenerateToken(String email, String otpInput) {

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

        //  Mark verified
        otp.setVerified(true);

        //  Generate secure token
        String verificationToken = UUID.randomUUID().toString();
        otp.setVerificationToken(verificationToken);

        //  Token expiry (10 min)
        otp.setTokenExpiryTime(LocalDateTime.now().plusMinutes(10));

        otpRepository.save(otp);

        return verificationToken;
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
    
    /* Register Industry */
    @Transactional
    public String registerIndustry(IndustryRegisterDTO dto) {

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
                || !otp.getVerificationToken().equals(dto.getVerificationToken())
                || otp.getTokenExpiryTime().isBefore(LocalDateTime.now())) {

            throw new RuntimeException("Invalid or expired verification token");
        }

        // Create User
        User user = User.builder()
                .email(dto.getEmail())
                .password(passwordEncoder.encode(dto.getPassword()))
                .contactNumber(dto.getUserContactNumber())
                .role(Role.INDUSTRY)
                .build();

        userRepository.save(user);

        // Create Industry
        Industry industry = Industry.builder()
                .name(dto.getName())
                .title(dto.getTitle())
                .contactNumber(dto.getIndustryContactNumber())
                .address(dto.getAddress())
                .aboutUs(dto.getAboutUs())
                .description(dto.getDescription())
                .user(user)
                .build();

        industryRepository.save(industry);

        // 🔥 Clean OTP records
        otpRepository.deleteByEmail(dto.getEmail());

        return "Industry registration successfully !!!";
    }


}
