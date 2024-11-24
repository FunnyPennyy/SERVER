package com.penny.penny_backend.domain;

import jakarta.persistence.*;
import lombok.Data;

import com.penny.penny_backend.domain.Classroom;

@Data
@Entity
public class Teacher {
    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    private Long teacherId;
    private String teacherName;

    @ManyToOne(fetch = FetchType.LAZY)
    private Classroom classroom;

}
