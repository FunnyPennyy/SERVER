package com.penny.penny_backend.domain;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@ToString
public class TeacherAccount {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long teacherAccountId;

    private String nickname;
    private int amount;
    private String accountNum;

    @OneToMany(mappedBy = "teacherAccount", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TeacherAccountHistory> teacherAccountHistories = new ArrayList<>();

    @OneToOne
    @JoinColumn(name = "teacher_id", referencedColumnName = "teacherId", unique = true, nullable = false)
    private Teacher teacher;

    @Builder
    public TeacherAccount(String nickname, int amount, String accountNum, Teacher teacher) {
        this.nickname = nickname;
        this.amount = amount;
        this.accountNum = accountNum;
        this.teacher = teacher;
    }
}