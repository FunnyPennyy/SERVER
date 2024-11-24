package com.penny.penny_backend.service;

import com.penny.penny_backend.domain.School;
import com.penny.penny_backend.repository.SchoolRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SchoolService {

    @Autowired(required = false)
    private SchoolRepository schoolRepository;

    public List<School> getAllSchools() {
        return schoolRepository.findAll();
    }
}

