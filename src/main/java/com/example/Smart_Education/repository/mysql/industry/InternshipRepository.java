package com.example.Smart_Education.repository.mysql.industry;

import com.example.Smart_Education.entity.industry_entity.Internship;
import com.example.Smart_Education.entity.industry_entity.InternshipStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InternshipRepository extends JpaRepository<Internship, Long> {

    List<Internship> findByIndustryId(Long industryId);


    List<Internship> findByDomainIgnoreCase(String domain);

    List<Internship> findByStatus(InternshipStatus status);

    Page<Internship> findAll(Pageable pageable);
    

}
