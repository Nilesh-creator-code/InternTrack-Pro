package com.example.Smart_Education.service.collegeService;

import com.example.Smart_Education.entity.college_entity.College;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.Smart_Education.repository.mysql.CollegeRepository;

import java.util.List;
import java.util.Optional;

@Service
public class CollegeService {

    @Autowired
    private CollegeRepository collegeRepository;



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


    /* Delete college by name */
    public String deleteCollegeByName(String name) {
        College existingCollege = collegeRepository.findByName(name)
                .orElseThrow(() -> new RuntimeException(
                        "College with name " + name + " not found"));
        collegeRepository.delete(existingCollege);
        return "College with name " + name + " has been deleted successfully.";
    }

}
