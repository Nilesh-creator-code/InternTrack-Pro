package com.example.Smart_Education.entity.industry_entity;

import com.example.Smart_Education.entity.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Industry {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Name is required")
    private String name;

    private String code; // e.g., IT, Finance, Healthcare
    private String title;
    private String aboutUs;
    private String description;
    
    @NotBlank(message = "Contact number is required")
    private String contactNumber;
    private String address;

    @OneToOne
    @JoinColumn(name = "user_id", unique = true)
    private User user;

    
}
