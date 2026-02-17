package com.example.Smart_Education.controller.collegeController;

import com.example.Smart_Education.entity.college_entity.College;
import com.example.Smart_Education.service.collegeService.CollegeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("college-controller")
public class CollegeController {


    @Autowired
    private CollegeService collegeService;

/*     Add College  */
    @PostMapping("/api/addCollege")
    public ResponseEntity<College> addCollege(@RequestBody College college) {
        College savedCollege = collegeService.addCollege(college);
        System.out.println(college);
        return ResponseEntity.ok(savedCollege);
    }

    /* Get college by name */
    @GetMapping("/api/getCollegeByName")
    public ResponseEntity<College> getCollegeByName(@RequestParam String name) {
        College college = collegeService.getCollegeByName(name);
        return ResponseEntity.ok(college);
    }

    /* Get all colleges */
    @GetMapping("/api/getAllColleges")
    public ResponseEntity<List<College>> getAllColleges() {
        List<College> colleges = collegeService.getAllColleges();
        return ResponseEntity.ok(colleges);
    }

}