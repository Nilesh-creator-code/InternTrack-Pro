package com.example.Smart_Education.entity.industry_entity;


import java.math.BigDecimal;
import java.time.LocalDate;

import jakarta.persistence.*;
import lombok.*;


@Entity
@Table(name = "internships")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Internship {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String title;
    private String shortDescription;
    private String domain;
    private BigDecimal stipend;
    private String location;
    private LocalDate startDate;
    private LocalDate endDate;
    @Column(name = "last_date_to_apply")
    private LocalDate lastDateToApply;
    private String type;
    @Enumerated(EnumType.STRING)
    private InternshipStatus status;

    @ManyToOne
    @JoinColumn(name = "industry_id")
    private Industry industry;
}
