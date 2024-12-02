package com.penny.penny_backend.service;

import com.penny.penny_backend.domain.Tax;
import com.penny.penny_backend.repository.TaxRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class TaxService {

    private final TaxRepository taxRepository;

    public Tax getTaxByClassroomId(Long classroomId) {
        return taxRepository.findByClassroom_ClassroomId(classroomId)
                .orElseThrow(() -> new RuntimeException("Tax not found for classroom ID: " + classroomId));
    }
}
