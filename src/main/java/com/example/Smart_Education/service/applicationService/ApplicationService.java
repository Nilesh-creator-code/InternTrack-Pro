package com.example.Smart_Education.service.applicationService;

import com.example.Smart_Education.DTOs.applicationDto.IndustryApplicationViewDTO;
import com.example.Smart_Education.DTOs.industryDtoPackage.industryDTO.IndustryApplicationResponseDTO;
import com.example.Smart_Education.DTOs.applicationDto.ApplicationDTO;
import com.example.Smart_Education.DTOs.applicationDto.StudentApplicationViewDTO;
import com.example.Smart_Education.entity.industry_entity.Industry;
import com.example.Smart_Education.entity.industry_entity.Internship;
import com.example.Smart_Education.entity.industry_entity.InternshipStatus;
import com.example.Smart_Education.entity.student_entity.Application;
import com.example.Smart_Education.entity.student_entity.ApplicationStatus;
import com.example.Smart_Education.entity.student_entity.Student;
import com.example.Smart_Education.repository.mysql.industry.IndustryRepository;
import com.example.Smart_Education.repository.mysql.industry.InternshipRepository;
import com.example.Smart_Education.repository.mysql.student.ApplicationRepository;
import com.example.Smart_Education.repository.mysql.student.StudentRepository;
import com.example.Smart_Education.service.fileService.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class ApplicationService {

    @Autowired
    private ApplicationRepository applicationRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private InternshipRepository internshipRepository;

    @Autowired
    private IndustryRepository industryRepository;

    @Autowired
    private FileService fileService;


    /* Apply for an internship */
    public String applyForInternship(ApplicationDTO applicationDTO) {

        String email = SecurityContextHolder.getContext()
                .getAuthentication()
                .getName();

        Student student = studentRepository.findByUser_Email(email)
                .orElseThrow(() -> new RuntimeException("Student not found"));

        Internship internship = internshipRepository.findById(applicationDTO.getInternshipId())
                .orElseThrow(() -> new RuntimeException("Internship not found"));

        // ✅ Extract once (clean code)
        LocalDate lastDate = internship.getLastDateToApply();

        // ✅ Safe validation
        if (internship.getStatus() != InternshipStatus.OPEN) {
            throw new RuntimeException("Internship is not open");
        }


        if (lastDate.isBefore(LocalDate.now())) {
            throw new RuntimeException("Application deadline has passed");
        }

        if (applicationRepository.existsByStudentAndInternship(student, internship)) {
            throw new RuntimeException("Already applied for this internship");
        }

        String resumeUrl = fileService.saveFile(applicationDTO.getResume()); // ✅ upload

        Application application = Application.builder()
                .student(student)
                .internship(internship)
                .location(applicationDTO.getLocation())
                .resumeLink(resumeUrl)
                .githubLink(applicationDTO.getGithubLink())
                .linkedinLink(applicationDTO.getLinkedinLink())
                .status(ApplicationStatus.APPLIED)
                .applicationDate(LocalDate.now())
                .build();

        applicationRepository.save(application);

        return "Applied successfully";
    }
    

    /* Get application dto where student has applied */
    public List<StudentApplicationViewDTO> getApplicationDtoByStudent() {

        // 1. Get logged-in user (JWT)
        String email = SecurityContextHolder.getContext()
                .getAuthentication()
                .getName();

        // 2. Find student by email
        Student student = studentRepository.findByUser_Email(email)
                .orElseThrow(() -> new RuntimeException("Student not found"));

        // 3. Get applications
        List<Application> applications = applicationRepository.findByStudentId(student.getId());



        // 4. Map to DTO
        return applications.stream()
                .map(application -> {
                    Internship internship = application.getInternship();
                    Industry industry = internship.getIndustry();

                    return StudentApplicationViewDTO.builder()
                            .internshipTitle(internship.getTitle())
                            .domain(internship.getDomain())
                            .location(internship.getLocation())
                            .industryName(industry.getName())
                            .status(application.getStatus().name())
                            .applicationDate(application.getApplicationDate())
                            .build();
                })
                .toList();
    }


    /* For the industry to get that student's applications who have applied */
    public List<IndustryApplicationViewDTO> getInternshipApplication(Long internshipId) {

        // 1. Get logged-in user (JWT)
        String email = SecurityContextHolder.getContext()
                .getAuthentication()
                .getName();

        // 2. Find industry by email
        Industry industry = industryRepository.findByUserEmail(email)
                .orElseThrow(() -> new RuntimeException("Industry not found"));

        // 3. Find internship by id and industry

        Internship internship = internshipRepository.findByIdAndIndustry(internshipId, industry)
                .orElseThrow(() -> new RuntimeException("Internship not found for this industry"));

        System.out.println("Internship ID: " + internshipId);
        System.out.println("Logged user email: " + email);
        System.out.println("Industry: " + industry);
        System.out.println("Internship: " + internship);


        // 4. Get applications for the internship
        List<Application> applications = applicationRepository.findByInternshipId(internship.getId());
        System.out.println("Applications: " + applications);

        // 5. Map to DTO
        return applications.stream()
                .map(application -> {
                    Student student = application.getStudent();
                    return IndustryApplicationViewDTO.builder()
                            .studentName(student.getName())
                            .studentEmail(student.getUser().getEmail())
                            .department(student.getDepartment())
                            .collegeName(student.getCollegeName())
                            .educationStatus(String.valueOf(student.getEducationStatus()))
                            .applicationId(application.getId())
                            .location(application.getLocation())
                            .resumeLink(application.getResumeLink()) // Assuming you want to include the resume file in the DTO
                            .githubLink(application.getGithubLink())
                            .linkedinLink(application.getLinkedinLink())
                            .build();
                })
                .toList();

    }



    /* Now the industry can view applications for their internships and approve or reject them */
    public ApplicationStatus updateApplicationStatus(Long applicationId, ApplicationStatus newStatus) {

        // 1. Get logged-in user (JWT)
        String email = SecurityContextHolder.getContext()
                .getAuthentication()
                .getName();

        // 2. Find industry by email
        Industry industry = industryRepository.findByUserEmail(email)
                .orElseThrow(() -> new RuntimeException("Industry not found"));

        // 3. Find application by id
        Application application = applicationRepository.findById(applicationId)
                .orElseThrow(() -> new RuntimeException("Application not found"));
                
        // Get internship from application
        Internship internship = application.getInternship();

        //4. Validate industry belong to that internship
        if(!internship.getIndustry().getId().equals(industry.getId())) {
            throw new RuntimeException("You are not authorized to update applications for this internship");
        }

        // 6. Update status
        application.setStatus(newStatus);
        applicationRepository.save(application);
        return application.getStatus();
             
    }

    

}
