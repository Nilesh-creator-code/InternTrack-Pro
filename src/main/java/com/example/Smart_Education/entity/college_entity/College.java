package com.example.Smart_Education.entity.college_entity;

import com.example.Smart_Education.entity.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class College {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotBlank(message = "Name is required")
    private String name;

    @NotBlank(message = "Contact number is required")
    private String contactNumber;
    @NotBlank(message = "Address is required")
    private String address;
    @NotBlank(message = "About Us is required")
    private String aboutUs;
    @NotBlank(message = "Description is required")
    private String description;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "college")
    private List<Department> department;

}