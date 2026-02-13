package com.example.Smart_Education.controller.studentController;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StudentController {

    @GetMapping("/hello")
    public String hello() {
        System.out.println("This is first API");
        return "To identify the project";
    }
}
