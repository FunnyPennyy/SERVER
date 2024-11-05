package com.penny.penny_backend.account.entity;

import jakarta.persistence.*;
import java.util.List;

@Entity
public class TeacherAccount {
    @Id
    private Long teacherId;

    private int amount;
    private String accountNum;

    @OneToOne
    @JoinColumn(name = "teacher_id", nullable = false)
    private TeacherUser teacherUser;

    @OneToMany(mappedBy = "teacherAccount", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TeacherAccountHistory> teacherAccountHistories;

    // getter & setter
    public Long getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(Long teacherId) {
        this.teacherId = teacherId;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getAccountNum() {
        return accountNum;
    }

    public void setAccountNum(String accountNum) {
        this.accountNum = accountNum;
    }

    public TeacherUser getTeacherUser() {
        return teacherUser;
    }

    public void setTeacherUser(TeacherUser teacherUser) {
        this.teacherUser = teacherUser;
    }

    public List<TeacherAccountHistory> getTeacherAccountHistories() {
        return teacherAccountHistories;
    }

    public void setTeacherAccountHistories(List<TeacherAccountHistory> teacherAccountHistories) {
        this.teacherAccountHistories = teacherAccountHistories;
    }
}
