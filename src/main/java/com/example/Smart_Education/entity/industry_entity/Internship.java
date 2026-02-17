package com.example.Smart_Education.entity.industry_entity;


import java.time.LocalDate;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@Table(name = "internships")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Internship {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String companyName;
    private String Name;
    private String email;
    private String phone;
    private String location;
    private String stipend;
    private String requirements;
    private String title;
    private String description;
    private String domain;
    private LocalDate startDate;
    private LocalDate endDate;

    @ManyToOne
    @JoinColumn(name = "industry_id")
    private Industry industry;

}
