package com.penny.penny_backend.domain;

import jakarta.persistence.*;
import lombok.*;

import com.penny.penny_backend.domain.Classroom;


@Entity
@Table(name = "student")
@Getter
@Setter
@NoArgsConstructor
public class Student {
    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    private Long studentId;
    private String studentName;
    private Integer credit;

    @ManyToOne(fetch = FetchType.LAZY)
    private Classroom classroom;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "job_id") // 외래 키 컬럼 이름
    private Job job;

    @Builder
    public Student(String studentName, Integer credit, Classroom classroom, Job job) {
        this.studentName = studentName;
        this.credit = credit;
        this.classroom = classroom;
        this.job = job;
    }
}
