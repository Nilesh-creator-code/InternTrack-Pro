package com.example.Smart_Education.repository.mysql;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.Smart_Education.entity.student_entity.User;

import java.util.Optional;


//MYSQL Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

    Optional<User> findByName(String name);

    boolean existsByEmail(String email);

    boolean existsByName(String name);
    
}