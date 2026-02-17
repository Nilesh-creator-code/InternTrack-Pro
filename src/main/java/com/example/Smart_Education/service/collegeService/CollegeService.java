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

    /* Update college */
    public College updatCollege(String name, College updatedCollege) {

        //First find the college exist or not
        College existingCollege = collegeRepository.findByName(name)
                .orElseThrow(() -> new RuntimeException(
                        "College with name " + name + " not found"));


        //I will check if new name already exists in another college
        if (collegeRepository.existsByNameAndIdNot(updatedCollege.getName(), existingCollege.getId())) {
            throw new RuntimeException("Name already in use");
        }

        //I will check if new email already exists in another college
        if (collegeRepository.existsByEmailAndIdNot(updatedCollege.getEmail(), existingCollege.getId())) {
            throw new RuntimeException("Email already in use");
        }

//        existingCollege.setName(updatedCollege.getName());

        existingCollege.setContactNumber(updatedCollege.getContactNumber());
        existingCollege.setEmail(updatedCollege.getEmail());
        existingCollege.setAddress(updatedCollege.getAddress());
        existingCollege.setAboutUs(updatedCollege.getAboutUs());
        existingCollege.setDescription(updatedCollege.getDescription());

        return collegeRepository.save(existingCollege);
    }

}
