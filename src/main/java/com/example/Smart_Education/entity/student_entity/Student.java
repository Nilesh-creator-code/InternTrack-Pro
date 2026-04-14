package com.example.Smart_Education.entity.student_entity;

import com.example.Smart_Education.entity.User;
import com.example.Smart_Education.entity.college_entity.College;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "students")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Name is required")
    private String name;

    private String department;

    @Column(name = "college_name")
    private String CollegeName;


    @OneToOne
    @JoinColumn(name = "user_id", unique = true)
    private User user;


    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL)
    private List<Enrollment> enrollments;

    // @ManyToOne
    // @JoinColumn(name = "intership_id")
    // private Internship internship;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EducationStatus educationStatus;

//    @ManyToOne
//    @JoinColumn(name = "college_id",nullable = true)
//    private College college;



}

