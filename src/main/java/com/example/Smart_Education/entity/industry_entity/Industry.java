package com.example.Smart_Education.entity.industry_entity;

import com.example.Smart_Education.entity.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Industry {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Name is required")
    private String name;

    private String code; // e.g., IT, Finance, Healthcare
    @NotBlank(message = "Title is required")
    private String title;
    @NotBlank(message = "About Us is required")
    private String aboutUs;
    @NotBlank
    private String description;
    
    @NotBlank(message = "Contact number is required")
    private String contactNumber;
    @NotBlank(message = "Address is required")
    private String address;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    private User user;

    @CreationTimestamp
    private LocalDateTime createdAt;

    
}
