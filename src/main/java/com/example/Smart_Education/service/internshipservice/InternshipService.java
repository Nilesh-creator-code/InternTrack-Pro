package com.example.Smart_Education.service.internshipservice;

import com.example.Smart_Education.DTOs.IndustryPackageDto.industryDTO.IndustryApplicationResponseDTO;
import com.example.Smart_Education.DTOs.IndustryPackageDto.internshipDTO.IndustryInternshipResponseDTO;
import com.example.Smart_Education.DTOs.IndustryPackageDto.internshipDTO.InternshipCreateDTO;
import com.example.Smart_Education.DTOs.IndustryPackageDto.internshipDTO.InternshipResposeDTO;
import com.example.Smart_Education.DTOs.studentDTO.ApplicationResponseDTO;
import com.example.Smart_Education.DTOs.studentDTO.StudentApplicationResponseDTO;

import org.springframework.data.domain.Page;

import java.util.List;


public interface InternshipService {

    String createInternship(InternshipCreateDTO dto, String email);

    List<InternshipResposeDTO> getAllInternships();

    IndustryInternshipResponseDTO getInternshipById(Long id);

    IndustryInternshipResponseDTO getInternshipForIndustry(Long id);

    IndustryInternshipResponseDTO getInternshipForStudent();

    List<InternshipResposeDTO> getInternshipsByDomain(String domain);

    IndustryInternshipResponseDTO updateInternship(InternshipCreateDTO dto);

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
