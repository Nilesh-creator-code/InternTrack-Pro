package com.example.Smart_Education.repository.mysql;

import com.example.Smart_Education.entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseRepository extends JpaRepository<Course, Long> {
    
}