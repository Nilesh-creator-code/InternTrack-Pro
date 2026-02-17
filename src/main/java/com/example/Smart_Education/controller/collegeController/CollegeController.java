package com.example.Smart_Education.controller.collegeController;

import com.example.Smart_Education.entity.college_entity.College;
import com.example.Smart_Education.service.collegeService.CollegeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

}