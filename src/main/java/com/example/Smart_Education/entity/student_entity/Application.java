package com.example.Smart_Education.entity.student_entity;

import java.time.LocalDate;

import com.example.Smart_Education.entity.User;
import com.example.Smart_Education.entity.industry_entity.Industry;
import com.example.Smart_Education.entity.industry_entity.Internship;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "applications")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Application {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "student_id")
    private Student student;

    @ManyToOne
    @JoinColumn(name = "internship_id")
    private Internship internship;

    @Column(name = "application_date")
    private LocalDate applicationDate;

    @NotBlank
    private String location;


    @NotBlank
    private String resumeLink;


    private String githubLink;

    @NotBlank
    @Pattern(
    regexp = "^(https?:\\/\\/)?(www\\.)?linkedin\\.com\\/.*$",
    message = "Please enter a valid LinkedIn URL"
)
    private String linkedinLink;

    @Enumerated(EnumType.STRING)
    private ApplicationStatus status;              // APPLIED, APPROVED, REJECTED, ONGOING, COMPLETED


}
