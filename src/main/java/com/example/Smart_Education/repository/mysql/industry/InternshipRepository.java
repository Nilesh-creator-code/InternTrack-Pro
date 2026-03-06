package com.example.Smart_Education.repository.mysql.industry;

import com.example.Smart_Education.entity.industry_entity.Internship;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InternshipRepository extends JpaRepository<Internship, Long> {
    List<Internship> findByIndustryId(Long industryId);
}
