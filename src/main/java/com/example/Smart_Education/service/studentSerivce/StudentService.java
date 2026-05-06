package com.example.Smart_Education.service.studentSerivce;

import com.example.Smart_Education.DTOs.studentDtoPackage.studentDTO.StudentProfileDTO;
import com.example.Smart_Education.entity.Role;
import com.example.Smart_Education.entity.User;
import com.example.Smart_Education.entity.student_entity.Student;
import com.example.Smart_Education.repository.mongodb.student.MongodbStudentRepository;
import com.example.Smart_Education.repository.mysql.college.CollegeRepository;
import com.example.Smart_Education.repository.mysql.student.StudentRepository;
import com.example.Smart_Education.repository.mysql.UserRepository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StudentService {


//    @Autowired
//    private Authentication authentication;

/*     MYSQL repository
 */ @Autowired
    private UserRepository userRepository;

    //MongoDB repository
    @Autowired
    private MongodbStudentRepository mongodbStudentRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private CollegeRepository collegeRepository;

    
    /* Create Student (User + Student profile) */
    public Student createStudent(Student student) {
        //First I will check if email already exists in User table
        if (userRepository.existsByEmail(student.getUser().getEmail())) {
            throw new RuntimeException("Email already in use");
        }

        //Set role automatically to STUDENT
        student.getUser().setRole(Role.STUDENT);
        //Save the User entity first
        User savedUser = userRepository.save(student.getUser());

        //Attach the saved User to the Student entity
        student.setUser(savedUser);
        
        //Save the Student entity
        return studentRepository.save(student);
    }
    
   /* Get Student profile*/
   public StudentProfileDTO getStudentProfile(String email) {
        // Find the student by email
        Student student = studentRepository.findByUser_Email(email)
                .orElseThrow(() -> new RuntimeException("Student with email " + email + " not found"));

        // Map the Student entity to StudentProfileDTO
        return StudentProfileDTO.builder()
                .id(student.getId())
                .name(student.getName())
                .department(student.getDepartment())
                .collegeName(student.getCollegeName())
                .email(student.getUser().getEmail())
                .contactNumber(student.getUser().getContactNumber())
                .educationStatus(student.getEducationStatus())
                .build();
    }


    /* Get Student by id */
    public Student getStudentById(Long id) {
        return studentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Student with id " + id + " not found"));
    }

    /* Get all students */
    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    /* Update student */
    public Student updateStudent(Long id, Student updatedStudent) {
        Student existingStudent = studentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Student with id " + id + " not found"));

        // Update fields
        existingStudent.setName(updatedStudent.getName());
        existingStudent.setDepartment(updatedStudent.getDepartment());
        existingStudent.setCollegeName(updatedStudent.getCollegeName());

        // 🔥 Update contact number from User
        if (updatedStudent.getUser() != null) {
            existingStudent.getUser().setContactNumber(
                    updatedStudent.getUser().getContactNumber()
            );
        }

        return studentRepository.save(existingStudent);
    }

    /* Delete student */
    public String deleteStudent(Long id) {
        studentRepository.deleteById(id);
        return "Student deleted successfully";
    }

}
