package com.penny.penny_backend.controller;

import com.penny.penny_backend.domain.School;
import com.penny.penny_backend.dto.CreateSchoolRequest;
import com.penny.penny_backend.dto.SchoolResponse;
import com.penny.penny_backend.repository.SchoolRepository;
import com.penny.penny_backend.service.SchoolService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RequiredArgsConstructor
@RestController
@RequestMapping("/schools")
public class SchoolController{
    private final SchoolService schoolService;

    @PostMapping("/create")
    public ResponseEntity<School> createSchool(@RequestBody CreateSchoolRequest request){
        School addSchool = schoolService.save(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(addSchool);
    }

    @GetMapping("/")
    public ResponseEntity<List<SchoolResponse>> findAllSchools(){
        List<SchoolResponse> schools = schoolService.findAll()
                .stream()
                .map(SchoolResponse::new)
                .toList();

        return ResponseEntity.ok()
                .body(schools);
    }

}

