package com.example.Smart_Education.DTOs.studentDtoPackage;


import com.example.Smart_Education.entity.student_entity.EducationStatus;
import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StudentProfileDTO {

    private Long id;

    private String name;

    private String department;

    private String collegeName;

    // User details (safe fields only)
    private String email;

    private String contactNumber;

    // Education info
    private EducationStatus educationStatus;

    // Optional: Enrollment details (IDs or simplified DTOs)
    private List<Long> enrollmentIds;
}
