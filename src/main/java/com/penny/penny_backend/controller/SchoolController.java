package com.penny.penny_backend.controller;

import com.penny.penny_backend.domain.School;
import com.penny.penny_backend.service.SchoolService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping("/api/schools")
@RequiredArgsConstructor
public class SchoolController {
    private SchoolService schoolService;

    @GetMapping
    public ResponseEntity<List<School>> getAllSchools() {
        List<School> schools = schoolService.getAllSchools();
        return ResponseEntity.ok(schools);
    }
}
