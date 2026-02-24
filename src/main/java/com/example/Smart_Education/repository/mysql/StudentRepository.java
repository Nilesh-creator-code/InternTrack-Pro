package com.example.Smart_Education.repository.mysql;

import com.example.Smart_Education.entity.User;
import com.example.Smart_Education.entity.student_entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.logging.LogManager;

public interface StudentRepository extends JpaRepository <Student, Long>{

//    Optional<User> findByEmail(String email);

    Optional<User> findByName(String name);

//    boolean existsByEmail(String email);

    boolean existsByName(String name);

}
