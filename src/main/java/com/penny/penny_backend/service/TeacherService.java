package com.penny.penny_backend.service;

import com.penny.penny_backend.domain.Classroom;
import com.penny.penny_backend.domain.Member;
import com.penny.penny_backend.domain.Teacher;
import com.penny.penny_backend.repository.TeacherRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class TeacherService {

    private final TeacherRepository teacherRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public Teacher createTeacher(String username, String password) {
        if (teacherRepository.findByUsername(username).isPresent()) {
            throw new IllegalStateException("Username already exists.");
        }

        Teacher teacher = new Teacher();
        teacher.setUsername(username);
        teacher.setPassword(passwordEncoder.encode(password));
        teacher.setRole(Member.Role.ADMIN); // Role 자동 설정

        return teacherRepository.save(teacher);
    }
}

