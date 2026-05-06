package com.example.Smart_Education.service.applicationService;

import com.example.Smart_Education.DTOs.industryDtoPackage.industryDTO.IndustryApplicationResponseDTO;
import com.example.Smart_Education.DTOs.studentDtoPackage.studentDTO.ApplicationDTO;
import com.example.Smart_Education.DTOs.studentDtoPackage.studentDTO.StudentApplicationViewDTO;
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
                            .industryName(industry.getName())
                            .status(application.getStatus().name())
                            .applicationDate(application.getApplicationDate())
                            .build();
                })
                .toList();
    }

    /* For the industry to get that student's applications who have applied */
    public List<IndustryApplicationResponseDTO> getApplicationsForMyIndustry() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();

        Industry industry = industryRepository.findByUserEmail(email)
                .orElseThrow(() -> new RuntimeException("Industry not found"));

        List<Application> applications = applicationRepository.findByInternship_Industry_Id(industry.getId());

        return applications.stream().map(app -> {
            IndustryApplicationResponseDTO dto = new IndustryApplicationResponseDTO();

            // Application info
            dto.setApplicationId(app.getId());
            dto.setApplicationDate(app.getApplicationDate());
            dto.setStatus(app.getStatus().name());

            // Student info
            dto.setStudentId(app.getStudent().getId());
            dto.setStudentName(app.getStudent().getName());
            dto.setStudentEmail(app.getStudent().getUser().getEmail());

            // Internship info
            dto.setInternshipId(app.getInternship().getId());
            dto.setInternshipTitle(app.getInternship().getTitle());

            return dto;
        }).toList();
    }


    /* get Student application where they have applied for internship of that industry - for industry dashboard
//     */
//    public List<ApplicationResponseDTO> getApplicationsForMyInternship(String email) {
//
////                Industry industry = industryRepository.findByUserEmail(email)
////                                .orElseThrow(() -> new RuntimeException("Industry not found"));
//
////                List<Application> applications = applicationRepository.findByInternship_Industry_Id(industry.getId());
//
//        Student student = studentRepository.findByUser_Email(email)
//                .orElseThrow(() -> new RuntimeException("Student not found"));
//
//        List<Application> applications = applicationRepository.findByStudentId(student.getId());
//
//
//        return applications.stream().map(app -> {
//            ApplicationResponseDTO dto = new ApplicationResponseDTO();
//
//            // Application info
//            dto.setId(app.getId());
//            dto.setApplicationDate(app.getApplicationDate());
//            dto.setStatus(app.getStatus());
//
//            dto.setInternshipTitle(app.getInternship().getTitle());
//            dto.setInternshipDomain(app.getInternship().getDomain());
//            dto.setInternshipDescription(app.getInternship().getShortDescription());
//
//            // Student info
//            dto.setStudentId(app.getStudent().getId());
//
//            // Internship info
//            dto.setInternshipId(app.getInternship().getId());
//
//            return dto;
//        }).toList();
//    }


}
