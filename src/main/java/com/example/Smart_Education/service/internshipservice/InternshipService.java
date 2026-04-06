package com.example.Smart_Education.service.internshipservice;

import com.example.Smart_Education.DTOs.industryDTO.internshipDTO.IndustryInternshipResponseDTO;
import com.example.Smart_Education.DTOs.industryDTO.internshipDTO.InternshipCreateDTO;
import com.example.Smart_Education.DTOs.industryDTO.internshipDTO.InternshipResposeDTO;
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

}
