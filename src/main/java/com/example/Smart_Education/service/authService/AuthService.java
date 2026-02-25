package com.example.Smart_Education.service.authService;

import com.example.Smart_Education.DTOs.AuthResponse;
import com.example.Smart_Education.DTOs.LoginRequest;
import com.example.Smart_Education.DTOs.StudentRegistrationDTO;
import com.example.Smart_Education.config.JwtService;
import com.example.Smart_Education.entity.EducationStatus;
import com.example.Smart_Education.entity.Role;
import com.example.Smart_Education.entity.User;
import com.example.Smart_Education.entity.college_entity.College;
import com.example.Smart_Education.entity.student_entity.Student;
import com.example.Smart_Education.repository.mysql.CollegeRepository;
import com.example.Smart_Education.repository.mysql.StudentRepository;
import com.example.Smart_Education.repository.mysql.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;



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

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        UserDetails userDetails =
                userDetailsService.loadUserByUsername(request.getEmail());

        String token = jwtService.generateToken(userDetails);

        return new AuthResponse(token);
    }

}
