package com.penny.penny_backend.domain;

import jakarta.persistence.*;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@ToString
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long accountId;

    private String nickname;
    private int amount;
    private String accountNum;

    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<AccountHistory> accountHistories = new ArrayList<>();

    @OneToOne
    @JoinColumn(name = "student_id", referencedColumnName = "studentId", unique = true, nullable = false)
    private Student student;

    @Builder
    public Account(String nickname, int amount, String accountNum, Student student) {
        this.nickname = nickname;
        this.amount = amount;
        this.accountNum = accountNum;
        this.student = student;
    }
}