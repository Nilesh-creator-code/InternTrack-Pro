package com.example.Smart_Education.repository.mysql.industry;

import com.example.Smart_Education.entity.industry_entity.Industry;
import com.example.Smart_Education.entity.industry_entity.Internship;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IndustryRepository extends JpaRepository<Industry, Long>{
    Optional<Industry> findByName(String name);

    Optional<Industry> findByUserEmail(String industryEmail);

//    Optional<Industry> findByUser_Email(String industryEmail);
}