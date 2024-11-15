package com.penny.penny_backend.domain;

import jakarta.persistence.*;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@ToString
public class Account {
    @Id
    private Long studentId;

    private String nickname;
    private int amount;
    private String accountNum;

    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<AccountHistory> accountHistories = new ArrayList<>();

//    @OneToOne(mappedBy = "")

    // Constructor
    public Account(Long studentId, String nickname, int amount, String accountNum) {
        this.studentId = studentId;
        this.nickname = nickname;
        this.amount = amount;
        this.accountNum = accountNum;
    }

//    public void addAccountHistory(AccountHistory accountHistory) {
//        accountHistories.add(accountHistory); // Account 엔티티에 AccountHistory 추가
//        accountHistory.setAccount(this); // AccountHistory 엔티티에 Account 설정
//    }
}
