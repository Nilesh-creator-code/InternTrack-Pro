package com.example.Smart_Education.repository.mysql;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.Smart_Education.entity.User;

import java.util.Optional;


//MYSQL Repository
public interface UserRepository extends JpaRepository<User, Long> {

    boolean existsByEmail(String email);

    Optional<User> findByEmail(String email);


}