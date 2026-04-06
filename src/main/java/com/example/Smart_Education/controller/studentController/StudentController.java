package com.example.Smart_Education.controller.studentController;

import com.example.Smart_Education.DTOs.industryDTO.internshipDTO.IndustryInternshipResponseDTO;
import com.example.Smart_Education.DTOs.industryDTO.internshipDTO.InternshipResposeDTO;
import com.example.Smart_Education.entity.student_entity.Student;
import com.example.Smart_Education.service.internshipservice.InternshipServiceImpl;
import com.example.Smart_Education.service.studentSerivce.StudentService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/students")
public class StudentController {

    private final StudentService studentService;

    private final InternshipServiceImpl internshipService;
    

    public StudentController(StudentService studentService, InternshipServiceImpl internshipService) {
        this.studentService = studentService;
        this.internshipService = internshipService;
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

    /* get all internships */
    @GetMapping("/all/pagination")
    public ResponseEntity<List<InternshipResposeDTO>> getAllInternshipsByPage(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        List<InternshipResposeDTO> response = internshipService.getAllInternshipsByPage(page, size).getContent();

        return ResponseEntity
                .status(200)
                .body(response);
    }

    /* get internships by domain */
    @GetMapping("/domain/{domain}")
    public ResponseEntity<List<InternshipResposeDTO>> getInternshipsByDomain(@PathVariable String domain) {
        List<InternshipResposeDTO> internships = internshipService.getInternshipsByDomain(domain);
        return ResponseEntity.ok(internships);
    }

     /* get internship by id */
     @GetMapping("/view/{id}")
     public ResponseEntity<IndustryInternshipResponseDTO> getMethodName(@PathVariable Long id) {
         IndustryInternshipResponseDTO internship = internshipService.getInternshipById(id);
         return ResponseEntity.ok(internship);
     }

        /* apply for internship */
    @PostMapping("/apply/{internshipId}")
    public ResponseEntity<String> applyForInternship(@PathVariable Long internshipId) {
        String response = internshipService.applyForInternship(internshipId);
        return ResponseEntity.ok(response); // 200 OK with response message
    }

}
