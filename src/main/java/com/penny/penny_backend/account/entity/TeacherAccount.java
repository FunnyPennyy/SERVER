package com.penny.penny_backend.account.entity;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
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

    protected TeacherAccount() {
    }

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

    public List<TeacherAccountHistory> getTeacherAccountHistories() {
        return teacherAccountHistories;
    }

    public void setTeacherAccountHistories(List<TeacherAccountHistory> teacherAccountHistories) {
        this.teacherAccountHistories = teacherAccountHistories;
    }

    public void addTeacherAccountHistory(TeacherAccountHistory teacherAccountHistory) {
        teacherAccountHistories.add(teacherAccountHistory); // teacherAccount 엔티티에 teacherAccountHistory 추가
        teacherAccountHistory.setTeacherAccount(this); // teacherAccountHistory 엔티티에 teacherAccount 설정
    }

    @Override
    public String toString() {
        return "Account{" +
                "studentId=" + teacherId +
                ", amount=" + amount +
                ", accountNum='" + accountNum + '\'' +
                ", accountHistories=" + teacherAccountHistories +
                '}';
    }
}
