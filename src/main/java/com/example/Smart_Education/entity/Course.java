package com.example.Smart_Education.entity;

import com.example.Smart_Education.entity.student_entity.Enrollment;
import jakarta.persistence.*;

import com.example.Smart_Education.entity.college_entity.College;
import com.example.Smart_Education.entity.industry_entity.Industry;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String code; // e.g., CS101

    private String name; // e.g., Introduction to Computer Science
    private String duration;
    private String fees;
    private String description;

    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL)
    private List<Enrollment> enrollments;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "college_id")
    private College college;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "industry_id")
    private Industry industry;
    
}
