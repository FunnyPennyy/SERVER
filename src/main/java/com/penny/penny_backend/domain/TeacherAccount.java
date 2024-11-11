package com.penny.penny_backend.domain;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@NoArgsConstructor
@ToString
public class TeacherAccount {
    @Id
    private Long teacherId;

    private String nickname;
    private int amount;
    private String accountNum;

    @OneToMany(mappedBy = "teacherAccount", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TeacherAccountHistory> teacherAccountHistories = new ArrayList<>();

    public TeacherAccount(Long teacherId, String nickname, int amount, String accountNum) {
        this.teacherId = teacherId;
        this.nickname = nickname;
        this.amount = amount;
        this.accountNum = accountNum;
    }

//    public void addTeacherAccountHistory(TeacherAccountHistory teacherAccountHistory) {
//        teacherAccountHistories.add(teacherAccountHistory); // teacherAccount 엔티티에 teacherAccountHistory 추가
//        teacherAccountHistory.setTeacherAccount(this); // teacherAccountHistory 엔티티에 teacherAccount 설정
//    }
}
