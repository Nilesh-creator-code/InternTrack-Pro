package com.example.Smart_Education.entity.student_entity;

import org.springframework.data.mongodb.core.mapping.Document;

import org.springframework.data.annotation.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/* MongoDB Databases */

@Document(collection = "student_reports")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StudentReport {

    @Id
    private String id;

    private String studentId;
    private String title;
    private String description;
    private String submissionDate;
    
}
