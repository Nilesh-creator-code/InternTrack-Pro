package com.example.Smart_Education.service.internshipservice;

import com.example.Smart_Education.DTOs.industryDtoPackage.internshipDTO.InternshipDetailDTO;
import com.example.Smart_Education.DTOs.industryDtoPackage.internshipDTO.InternshipCreateDTO;
import com.example.Smart_Education.DTOs.industryDtoPackage.internshipDTO.InternshipListDTO;
import com.example.Smart_Education.DTOs.industryDtoPackage.internshipDTO.UpdateInternshipDTO;

import org.springframework.data.domain.Page;

import java.util.List;


public interface InternshipService {

    void createInternship(InternshipCreateDTO dto, String email);

    List<InternshipListDTO> getAllInternships();

    InternshipDetailDTO getInternshipById(Long id);

    InternshipDetailDTO getInternshipDetailById(Long id);

    List<InternshipListDTO> getInternshipsByDomain(String domain);

    InternshipDetailDTO updateInternship(Long id, UpdateInternshipDTO dto);

    String deleteInternship(Long id, String email);

    Page<InternshipListDTO> getAllInternshipsByPage(int page, int size);

    //For industry dashboard - Get all internships posted by the industry
    public List<InternshipListDTO> getMyInternships(String email);




}
