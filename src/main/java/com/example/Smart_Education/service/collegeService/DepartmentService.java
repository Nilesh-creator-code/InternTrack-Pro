package com.example.Smart_Education.service.collegeService;

import com.example.Smart_Education.DTOs.collegeDTO.CollegeDepartmentDTO;
import com.example.Smart_Education.entity.college_entity.College;
import com.example.Smart_Education.entity.college_entity.Department;
import com.example.Smart_Education.repository.mysql.college.CollegeRepository;
import com.example.Smart_Education.repository.mysql.college.DepartmentRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class DepartmentService {

    @Autowired
    private DepartmentRepository departmentRepository;

    @Autowired
    private CollegeRepository collegeRepository;


    @Transactional
    public String createDepartment(CollegeDepartmentDTO collegeDepartmentDTO) {

        if (departmentRepository.existsByName(collegeDepartmentDTO.getName())) {
            throw new RuntimeException(
                    "Department with name " +
                            collegeDepartmentDTO.getName() +
                            " already exists"
            );
        }

        String email = SecurityContextHolder.getContext().getAuthentication().getName();

        College college = collegeRepository.findByUserEmail(email);

        if (college == null) {
            throw new RuntimeException("College not found");
        }

        Department department = Department.builder()
                .name(collegeDepartmentDTO.getName())
                .description(collegeDepartmentDTO.getDescription())
                .headOfDepartment(collegeDepartmentDTO.getHeadOfDepartment())
                .college(college)
                .build();

        Department savedDepartment = departmentRepository.save(department);

        System.out.println("Department ID: " + savedDepartment.getId());
        System.out.println("Department name: " + savedDepartment.getName());


        return "Department created successfully";
    }

    

}
