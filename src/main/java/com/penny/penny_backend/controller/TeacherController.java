//package com.penny.penny_backend.controller;
//
//
//import lombok.RequiredArgsConstructor;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import com.penny.penny_backend.service.TeacherService;
//import com.penny.penny_backend.domain.Teacher;
//
//
//@RestController
//@RequestMapping("/api/teachers")
//@RequiredArgsConstructor
//public class TeacherController {
//    private TeacherService teacherService;
//
//    @PostMapping("/register")
//    public ResponseEntity<Teacher> registerTeacher(@RequestBody Teacher teacher) {
//        Teacher savedTeacher = teacherService.registerTeacher(teacher);
//        return ResponseEntity.status(HttpStatus.CREATED).body(savedTeacher);
//    }
//}
