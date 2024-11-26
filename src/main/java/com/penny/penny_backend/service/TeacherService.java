package com.penny.penny_backend.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.penny.penny_backend.repository.TeacherRepository;
import com.penny.penny_backend.domain.Teacher;

@Service
@RequiredArgsConstructor
public class TeacherService {


    @Autowired(required = false)
    private TeacherRepository teacherRepository;

    public Teacher registerTeacher(Teacher teacher) {
        return teacherRepository.save(teacher);
    }
}

