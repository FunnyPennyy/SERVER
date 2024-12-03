package com.penny.penny_backend.service;

import com.penny.penny_backend.domain.Classroom;
import com.penny.penny_backend.domain.Student;
import com.penny.penny_backend.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class StudentService {

    private final StudentRepository studentRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public Student createStudent(String username, String password, String studentName, Integer credit, Classroom classroom) {
        Student student = new Student(username, passwordEncoder.encode(password), studentName, credit, classroom);
        return studentRepository.save(student);
    }
}
