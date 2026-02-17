package com.example.Smart_Education.service.collegeService;

import com.example.Smart_Education.entity.college_entity.College;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.Smart_Education.repository.mysql.CollegeRepository;

import java.lang.foreign.Linker.Option;
import java.util.List;
import java.util.Optional;

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

    /* Fetch a College by name */
    public College getCollegeByName(String name) {
        return collegeRepository.findByName(name)
                .orElseThrow(() -> new RuntimeException(
                        "College with name " + name + " not found"));
    }

    /* Fetch all Colleges */
    public List<College> getAllColleges() {
        return Optional.of(collegeRepository.findAll())
                .orElseThrow(() -> new RuntimeException("No colleges found"));  
    }

}
