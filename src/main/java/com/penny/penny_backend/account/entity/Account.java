package com.penny.penny_backend.account.entity;

import jakarta.persistence.*;
import java.util.List;

@Entity
public class Account {
    @Id
    private Long studentID;

    private String nickname;
    private int amount;
    private String accountNum;

    @OneToOne
    @JoinColumn(name = "student_id", nullable = false)
    private StudentUser studentUser;

    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<AccountHistory> accountHistories;

    // Constructor
    public Account(String nickname, int amount, String accountNum, StudentUser studentUser) {
        this.nickname = nickname;
        this.amount = amount;
        this.accountNum = accountNum;
        this.studentUser = studentUser;
    }

    public Account() {
    }

    // getter & setter
    public Long getStudentID() {
        return studentID;
    }

    public void setStudentID(Long studentID) {
        this.studentID = studentID;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
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

    public StudentUser getStudentUser() {
        return studentUser;
    }

    public void setStudentUser(StudentUser studentUser) {
        this.studentUser = studentUser;
    }

    public List<AccountHistory> getAccountHistories() {
        return accountHistories;
    }

    public void setAccountHistories(List<AccountHistory> accountHistories) {
        this.accountHistories = accountHistories;
    }

    public void addAccountHistory(AccountHistory accountHistory) {
        accountHistories.add(accountHistory); // Account 엔티티에 AccountHistory 추가
        accountHistory.setAccount(this); // AccountHistory 엔티티에 Account 설정
    }

    @Override
    public String toString() {
        return "Account{" +
                "studentID=" + studentID +
                ", nickname='" + nickname + '\'' +
                ", amount=" + amount +
                ", accountNum='" + accountNum + '\'' +
                ", studentUser=" + studentUser +
                ", accountHistories=" + accountHistories +
                '}';
    }
}
