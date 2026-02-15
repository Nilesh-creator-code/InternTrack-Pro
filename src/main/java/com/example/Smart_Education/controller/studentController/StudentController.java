package com.example.Smart_Education.controller.studentController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.Smart_Education.entity.student_entity.StudentReport;
import com.example.Smart_Education.service.studentSerivce.StudentService;

@RestController
@RequestMapping("student-controller")
public class StudentController {

    @Autowired
    private StudentService studentService;

    @GetMapping("/hello")
    public String hello() {
        System.out.println("This is first API");
        return "To identify the project";
    }

    @PostMapping("/api/report")
    public ResponseEntity<StudentReport> addStudentReport(@RequestBody StudentReport studentReport) {
        StudentReport report = studentService.addStudentReport(studentReport);
        System.out.println("Request Comes Successfully..");
        return ResponseEntity.ok(report);
    }
}
