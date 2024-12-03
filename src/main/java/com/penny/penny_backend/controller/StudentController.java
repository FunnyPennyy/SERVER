/*
package com.penny.penny_backend.controller;

import com.penny.penny_backend.domain.Student;
import com.penny.penny_backend.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/students")
public class StudentController {

    private final StudentService studentService;

    @PostMapping("/sign-up")
    public ResponseEntity<Student> createStudent(@RequestBody StudentRequest request) {
        Student student = studentService.createStudent(
                request.getUsername(),
                request.getPassword(),
                request.getStudentName(),
                request.getCredit(),
                request.getClassroom()
        );
        return ResponseEntity.ok(student);
    }
}

*/
