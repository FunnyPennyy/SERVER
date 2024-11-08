package com.penny.penny_backend.domain;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Account {
    @Id
    private Long studentId;

    private String nickname;
    private int amount;
    private String accountNum;

    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<AccountHistory> accountHistories = new ArrayList<>();

    // Constructor
    public Account(Long studentId, String nickname, int amount, String accountNum) {
        this.studentId = studentId;
        this.nickname = nickname;
        this.amount = amount;
        this.accountNum = accountNum;
    }

    protected Account() {
    }

    // getter & setter
    public Long getStudentId() {
        return studentId;
    }

    public void setStudentId(Long studentId) {
        this.studentId = studentId;
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
                "studentId=" + studentId +
                ", nickname='" + nickname + '\'' +
                ", amount=" + amount +
                ", accountNum='" + accountNum + '\'' +
                ", accountHistories=" + accountHistories +
                '}';
    }
}
