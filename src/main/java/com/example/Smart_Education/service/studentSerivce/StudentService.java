package com.example.Smart_Education.service.studentSerivce;

import com.example.Smart_Education.entity.student_entity.StudentReport;
import com.example.Smart_Education.entity.student_entity.User;
import com.example.Smart_Education.repository.mongodb.StudentRepository;
import com.example.Smart_Education.repository.mysql.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StudentService {

/*     MYSQL repository
 */    @Autowired
    private UserRepository userRepository;
    
    //MongoDB repository
    @Autowired
    private StudentRepository studentRepository;


    //Add Student 
    public User addStudentUser(User user) {
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new RuntimeException("Email already exists: " + user.getEmail());
        }
        if (userRepository.existsByName(user.getName())) {
            throw new RuntimeException("Name already exists: " + user.getName());
        }
        return userRepository.save(user); // Save the user to MySQL
    }

    //Get Student by ID
    public User getStudentById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new RuntimeException("Student not found with ID: " + id)); // Retrieve the user by ID from MySQL
    }






/*     Mysql Database Service
 */    public StudentReport addStudentReport(StudentReport studentReport) {
        studentRepository.save(studentReport); // Save the report to MongoDB
        return studentReport; // Return the saved report (with an ID if generated)
    }
    

}
