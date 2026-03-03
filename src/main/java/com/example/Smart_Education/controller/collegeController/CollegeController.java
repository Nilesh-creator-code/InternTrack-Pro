package com.example.Smart_Education.controller.collegeController;

import com.example.Smart_Education.entity.college_entity.College;
import com.example.Smart_Education.service.collegeService.CollegeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/college-controller")
public class CollegeController {


    @Autowired
    private CollegeService collegeService;

    @GetMapping("/hello")
    public String getHello() {
        return "Hello from the College";
    }

    /* Get college by name */
    @GetMapping("/getCollegeByName")
    public ResponseEntity<College> getCollegeByName(@RequestParam String name) {
        College college = collegeService.getCollegeByName(name);
        return ResponseEntity.ok(college);
    }

    /* Get all colleges */
    @GetMapping("/getAllColleges")
    public ResponseEntity<List<College>> getAllColleges() {
        List<College> colleges = collegeService.getAllColleges();
        return ResponseEntity.ok(colleges);
    }


    /* Delete college by name */
    @DeleteMapping("/deleteCollegeByName/{name}")
    public ResponseEntity<String> deleteCollegeByName(@PathVariable String name) {
        String result = collegeService.deleteCollegeByName(name);
        return ResponseEntity.ok(result);
    }




}