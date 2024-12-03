package com.penny.penny_backend.service;

import com.penny.penny_backend.domain.Classroom;
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
    public Teacher createTeacher(String username, String password, String teacherName, Classroom classroom) {
        Teacher teacher = new Teacher(username, passwordEncoder.encode(password), teacherName, classroom);
        return teacherRepository.save(teacher);
    }
}
