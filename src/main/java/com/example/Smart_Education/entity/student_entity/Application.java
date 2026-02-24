package com.example.Smart_Education.entity.student_entity;

import java.time.LocalDate;

import com.example.Smart_Education.entity.User;
import com.example.Smart_Education.entity.industry_entity.Internship;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "applications")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public abstract class Application {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate applicationDate;
    private String status;              // APPLIED, APPROVED, REJECTED, ONGOING, COMPLETED

    @ManyToOne
    @JoinColumn(name = "student_id")
    private User student;

    @ManyToOne
    @JoinColumn(name = "internship_id")
    private Internship internship;
    
}
