package com.example.Smart_Education.controller.authController;


import com.example.Smart_Education.entity.student_entity.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.Smart_Education.DTOs.StudentRegistrationDTO;
import com.example.Smart_Education.service.authService.AuthService;


@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<Student> register(@RequestBody StudentRegistrationDTO dto) {
        Student student = authService.registerStudent(dto);
        return ResponseEntity.ok(student);
    }
}