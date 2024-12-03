package com.penny.penny_backend.service;

import com.penny.penny_backend.domain.Classroom;
import com.penny.penny_backend.domain.Member;
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
    public Student createStudent(String username, String password) {
        if (studentRepository.findByUsername(username).isPresent()) {
            throw new IllegalStateException("Username already exists.");
        }

        Student student = new Student();
        student.setUsername(username);
        student.setPassword(passwordEncoder.encode(password));
        student.setRole(Member.Role.USER); // Role 자동 설정

        return studentRepository.save(student);
    }
}

