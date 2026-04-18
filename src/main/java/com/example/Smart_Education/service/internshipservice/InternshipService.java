package com.example.Smart_Education.service.internshipservice;

import com.example.Smart_Education.DTOs.industryDTO.IndustryApplicationResponseDTO;
import com.example.Smart_Education.DTOs.industryDTO.internshipDTO.IndustryInternshipResponseDTO;
import com.example.Smart_Education.DTOs.industryDTO.internshipDTO.InternshipCreateDTO;
import com.example.Smart_Education.DTOs.industryDTO.internshipDTO.InternshipResposeDTO;
import com.example.Smart_Education.DTOs.studentDTO.ApplicationResponseDTO;
import com.example.Smart_Education.DTOs.studentDTO.StudentApplicationResponseDTO;
import com.example.Smart_Education.entity.industry_entity.Internship;

import org.springframework.data.domain.Page;

import java.util.List;


public interface InternshipService {

    void createInternship(InternshipCreateDTO dto, String email);

    List<InternshipResposeDTO> getAllInternships();

    IndustryInternshipResponseDTO getInternshipById(Long id);

    List<InternshipResposeDTO> getInternshipsByDomain(String domain);

    IndustryInternshipResponseDTO updateInternship(Long id, InternshipCreateDTO dto, String email);

    String deleteInternship(Long id, String email);

    Page<InternshipResposeDTO> getAllInternshipsByPage(int page, int size);

    String applyForInternship(Long internshipId);

    /* Get application dto where student has applied */
    List<StudentApplicationResponseDTO> getApplicationDtoByStudent();

    /* Get application dto where student has applied */
    public List<IndustryApplicationResponseDTO> getApplicationsForMyIndustry();

    //For industry dashboard - Get all internships posted by the industry
    public List<InternshipResposeDTO> getMyInternships(String email);

    
    public List<ApplicationResponseDTO> getApplicationsForMyInternship(String email);

}
