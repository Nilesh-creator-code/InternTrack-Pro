package com.example.Smart_Education.controller.studentController;

import com.example.Smart_Education.entity.student_entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.Smart_Education.entity.student_entity.StudentReport;
import com.example.Smart_Education.service.studentSerivce.StudentService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("student-controller")
public class StudentController {

    @Autowired
    private StudentService studentService;

    @PostMapping("/api/addStudent")
    public ResponseEntity<User> addStudent(@RequestBody User user) {
        User savedUser = studentService.addStudentUser(user);
        return ResponseEntity.ok(savedUser);
    }


    @PostMapping("/api/report")
    public ResponseEntity<StudentReport> addStudentReport(@RequestBody StudentReport studentReport) {
        StudentReport report = studentService.addStudentReport(studentReport);
        System.out.println("Request Comes Successfully..");
        return ResponseEntity.ok(report);
    }
}
