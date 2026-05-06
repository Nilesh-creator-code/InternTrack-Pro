package com.example.Smart_Education.service.internshipservice;

import com.example.Smart_Education.DTOs.industryDtoPackage.internshipDTO.IndustryInternshipResponseDTO;
import com.example.Smart_Education.DTOs.industryDtoPackage.internshipDTO.InternshipCreateDTO;
import com.example.Smart_Education.DTOs.industryDtoPackage.internshipDTO.InternshipResposeDTO;
import com.example.Smart_Education.DTOs.industryDtoPackage.internshipDTO.UpdateInternshipDTO;

import org.springframework.data.domain.Page;

import java.util.List;


public interface InternshipService {

    void createInternship(InternshipCreateDTO dto, String email);

    List<InternshipResposeDTO> getAllInternships();

    IndustryInternshipResponseDTO getInternshipById(Long id);

    List<InternshipResposeDTO> getInternshipsByDomain(String domain);

    IndustryInternshipResponseDTO updateInternship(Long id, UpdateInternshipDTO dto);

    String deleteInternship(Long id, String email);

    Page<InternshipResposeDTO> getAllInternshipsByPage(int page, int size);

    //For industry dashboard - Get all internships posted by the industry
    public List<InternshipResposeDTO> getMyInternships(String email);


}
