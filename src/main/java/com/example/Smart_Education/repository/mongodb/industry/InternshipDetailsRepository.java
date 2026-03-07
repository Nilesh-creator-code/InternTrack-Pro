package com.example.Smart_Education.repository.mongodb.industry;

import com.example.Smart_Education.entity.industry_entity.InternshipDetails;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

//MongoDB
public interface InternshipDetailsRepository extends MongoRepository<InternshipDetails, String> {

    Optional<InternshipDetails> findByInternshipId(Long internshipId);

    void deleteByInternshipId(Long id);
}
