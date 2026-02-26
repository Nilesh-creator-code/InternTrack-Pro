package com.example.Smart_Education.controller.studentController;

import com.example.Smart_Education.entity.student_entity.Student;
import com.example.Smart_Education.service.studentSerivce.StudentService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/students")
public class StudentController {

    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    /* Create student */
    @PostMapping
    public ResponseEntity<Student> createStudent(@Valid @RequestBody Student student) {

        Student createdStudent = studentService.createStudent(student);

        return ResponseEntity
                .status(201) // HTTP 201 Created
                .body(createdStudent);
    }

    /* Get student by id */
    @GetMapping("/{id}")
    public ResponseEntity<Student> getStudentById(@PathVariable Long id) {

        Student student = studentService.getStudentById(id);
        return ResponseEntity.ok(student); // 200 OK
    }

    /* Get all students */
    @GetMapping
    public ResponseEntity<List<Student>> getAllStudents() {

        return ResponseEntity.ok(studentService.getAllStudents());
    }

    /* Update student */
    @PutMapping("/{id}")
    public ResponseEntity<Student> updateStudent(
            @PathVariable Long id,
            @Valid @RequestBody Student updatedStudent) {

        Student student = studentService.updateStudent(id, updatedStudent);
        return ResponseEntity.ok(student); // 200 OK
    }

    /* Delete student */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStudent(@PathVariable Long id) {

        studentService.deleteStudent(id);
        return ResponseEntity.noContent().build(); // 204 No Content
    }
}
