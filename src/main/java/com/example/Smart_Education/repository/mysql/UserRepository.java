package com.example.Smart_Education.repository.mysql;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.Smart_Education.entity.student_entity.User;

import javax.swing.text.html.Option;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
}