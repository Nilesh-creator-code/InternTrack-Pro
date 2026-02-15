package com.example.Smart_Education.entity.student_entity;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.springframework.data.mongodb.core.mapping.Document;

import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Document(collection = "progress_logs")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProgressLog {

    @Id
    private String id;

    private Long studentId;
    private String activity;
    private LocalDateTime date;
    
}
