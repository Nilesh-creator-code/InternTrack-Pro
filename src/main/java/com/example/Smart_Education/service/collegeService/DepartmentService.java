package com.example.Smart_Education.service.collegeService;

import com.example.Smart_Education.DTOs.collegeDTO.CollegeDepartmentDTO;
import com.example.Smart_Education.entity.college_entity.Department;
import com.example.Smart_Education.repository.mysql.college.DepartmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DepartmentService {

    @Autowired
    private DepartmentRepository departmentRepository;

    public String createDepartment(CollegeDepartmentDTO collegeDepartmentDTO) {

        if (departmentRepository.existsByName(collegeDepartmentDTO.getName())) {
            throw new RuntimeException("Department with name " + collegeDepartmentDTO.getName() + " already exists");
        }

        Department department = Department.builder()
                .name(collegeDepartmentDTO.getName())
                .description(collegeDepartmentDTO.getDescription())
                .headOfDepartment(collegeDepartmentDTO.getHeadOfDepartment())
                .build();

        departmentRepository.save(department);

        return "Department created successfully";
    }

}
