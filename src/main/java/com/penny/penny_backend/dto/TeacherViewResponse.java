package com.penny.penny_backend.dto;

import com.penny.penny_backend.domain.Classroom;
import com.penny.penny_backend.domain.Teacher;
import lombok.Getter;

@Getter
public class TeacherViewResponse {
    private final Long id;
    private final String username;
    private final Classroom classroom;


    public TeacherViewResponse(Teacher teacher){
        this.id = teacher.getId();
        this.username = teacher.getUsername();
        this.classroom = teacher.getClassroom();
    }
}
