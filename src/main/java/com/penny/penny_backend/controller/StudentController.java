package com.penny.penny_backend.controller;

import com.penny.penny_backend.domain.Student;
import com.penny.penny_backend.dto.StudentPageResponse;
import com.penny.penny_backend.dto.StudentRequest;
import com.penny.penny_backend.jwt.JwtTokenProvider;
import com.penny.penny_backend.service.StudentService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/students")
public class StudentController {
    private final JwtTokenProvider jwtTokenProvider;
    private final StudentService studentService;

    @PostMapping("/sign-up")
    public ResponseEntity<Student> createStudent(@RequestBody StudentRequest request) {
        Student student = studentService.createStudent(request.getUsername(), request.getPassword());
        return ResponseEntity.ok(student);
    }

    @GetMapping("/mypage")
    public ResponseEntity<StudentPageResponse> getMyPage(HttpServletRequest request) {
        String username= jwtTokenProvider.getUsername(request);
        StudentPageResponse studentPageResponse = studentService.getStudentInfo(username);
        return ResponseEntity.ok(studentPageResponse);

    }


}

