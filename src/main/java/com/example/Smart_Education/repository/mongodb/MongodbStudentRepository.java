package com.example.Smart_Education.repository.mongodb;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.example.Smart_Education.entity.student_entity.StudentReport;


/* MongoDB Repository */ 
@Repository
public interface MongodbStudentRepository extends MongoRepository<StudentReport, String>{
    
}
