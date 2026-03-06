package com.example.Smart_Education.entity.industry_entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

//Mongodb
@Document(collection = "internship_details")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InternshipDetails {

    @Id
    private String id;

    private Long internshipId; // SQL ID reference

    private String fullDescription;

    private List<String> skillsRequired;

    private List<String> responsibilities;
}
