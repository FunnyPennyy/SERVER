package com.penny.penny_backend.controller;

import com.penny.penny_backend.domain.Teacher;
import com.penny.penny_backend.dto.TeacherRequest;
import com.penny.penny_backend.service.TeacherService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/teachers")
public class TeacherController {

    private final TeacherService teacherService;

    @PostMapping(value = "/sign-up")
    public ResponseEntity<Teacher> createTeacher(@RequestBody TeacherRequest request) {
        Teacher teacher = teacherService.createTeacher(request.getUsername(), request.getPassword());
        return ResponseEntity.ok(teacher);
    }

}
