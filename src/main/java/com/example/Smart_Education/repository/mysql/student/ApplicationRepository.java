package com.example.Smart_Education.repository.mysql.student;

import com.example.Smart_Education.entity.industry_entity.Internship;
import com.example.Smart_Education.entity.student_entity.Application;
import com.example.Smart_Education.entity.student_entity.Student;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ApplicationRepository extends JpaRepository<Application, Long> {

    boolean existsByStudentIdAndInternshipId(Long studentId, Long internshipId);

    List<Application> findByInternshipId(Long internshipId);

    List<Application> findByStudentId(Long studentId);

    boolean existsByStudentAndInternship(Student student, Internship internship);

    List<Application> findByInternship_Industry_Id(Long industryId);

    //For the student to get there internship where they have applied
    List<Application> findByStudent(Student student);


}
