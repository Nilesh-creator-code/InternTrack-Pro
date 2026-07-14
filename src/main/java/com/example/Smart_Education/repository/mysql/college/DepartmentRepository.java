package com.example.Smart_Education.repository.mysql.college;


import com.example.Smart_Education.entity.college_entity.Department;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DepartmentRepository extends JpaRepository<Department, Long> {

    boolean existsByName(String name);

}
