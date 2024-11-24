package com.penny.penny_backend.service;

import com.penny.penny_backend.domain.School;
import com.penny.penny_backend.dto.CreateSchoolRequest;
import com.penny.penny_backend.repository.SchoolRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;



import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class SchoolService {

    private SchoolRepository schoolRepository;

    public School save(CreateSchoolRequest request){
        return schoolRepository.save(request.toEntity());
    }
    public List<School> findAll(){
        return schoolRepository.findAll();
    }

}
