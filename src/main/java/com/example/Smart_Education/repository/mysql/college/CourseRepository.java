package com.example.Smart_Education.repository.mysql.college;

import com.example.Smart_Education.entity.college_entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CourseRepository extends JpaRepository<Course, Long> {

    List<Course> findByCollegeId(Long collegeId);

}