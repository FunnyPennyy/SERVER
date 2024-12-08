package com.penny.penny_backend.controller;

import com.penny.penny_backend.domain.Teacher;
import com.penny.penny_backend.dto.SchoolResponse;
import com.penny.penny_backend.dto.TeacherRequest;
import com.penny.penny_backend.dto.TeacherViewResponse;
import com.penny.penny_backend.service.TeacherService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

/*
    @GetMapping("/mypage")
    public ResponseEntity<TeacherViewResponse> getMyPage(Authentication authentication){
        String username = authentication.getName();
        TeacherViewResponse teacher = teacherService.findByUsername(username);


        return ResponseEntity.ok()
                .body(teacher);
    }
*/


}
