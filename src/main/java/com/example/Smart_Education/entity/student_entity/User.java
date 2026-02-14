package com.example.Smart_Education.entity.student_entity;

import com.example.Smart_Education.entity.Course;
import com.example.Smart_Education.entity.Role;
import com.example.Smart_Education.entity.college_entity.College;
import com.example.Smart_Education.entity.industry_entity.Internship;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "students")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String email;
    private String password;
    private String contactNumber;
    private String department;

    @Enumerated(EnumType.STRING)
    private Role role;

    @ManyToOne
    @JoinColumn(name = "course_id")
    private Course course;

    @ManyToOne
    @JoinColumn(name = "intership_id")
    private Internship internship;


    @ManyToOne
    @JoinColumn(name = "college_id")
    private College college;

}
