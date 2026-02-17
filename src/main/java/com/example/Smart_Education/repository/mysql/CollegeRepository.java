package com.example.Smart_Education.repository.mysql;

import com.example.Smart_Education.entity.college_entity.College;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CollegeRepository extends JpaRepository<College, Long> {
    
    Optional<College> findByName(String name);

    Optional<College> findByEmail(String email);

    boolean existsByName(String name);

    boolean existsByEmail(String email);

}
