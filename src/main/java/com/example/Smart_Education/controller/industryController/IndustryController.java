package com.example.Smart_Education.controller.industryController;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/industry-controller")
public class IndustryController {

    @GetMapping("/hello")
    public String sayHello() {
        return "Hello from College Controller";
    }

}
