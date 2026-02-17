package com.example.Smart_Education.service.collegeService;

import com.example.Smart_Education.entity.college_entity.College;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.Smart_Education.repository.mysql.CollegeRepository;

@Service
public class CollegeService {

    @Autowired
    private CollegeRepository collegeRepository;

    
    public College addCollege(College college) {
        if (collegeRepository.existsByName(college.getName())) {
            throw new RuntimeException("College with the same name already exists.");
        }
        if (collegeRepository.existsByEmail(college.getEmail())) {
            throw new RuntimeException("College with the same email already exists.");
        }
        return collegeRepository.save(college);
    }
  
}
