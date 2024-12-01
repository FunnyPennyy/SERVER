package com.penny.penny_backend.domain;

//import java.util.*;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;
//import org.hibernate.annotations.CreationTimestamp;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Entity
public class Deposit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // 예금통장 id

    @ManyToOne
    @JoinColumn(name = "student_id", nullable = false) // Student FK
    private Student owner; // 예금통장 주인

    @ManyToOne
    @JoinColumn(name = "account_id", nullable = false) // Account FK
    private Account account; // 연결된 계좌

    private int amount; // 예금 금액 (사용자가 금액 얼마 넣을지 설정)

    private LocalDate createdDate; // 생성일: 현재 날짜

    private LocalDate maturityDate; // 만기일
    private LocalDate terminationDate = null; // 해지일

    @ManyToOne
    @JoinColumn(name = "depositType_id", nullable = false) // FK for deposit type
    private DepositType depositType;


    @Builder
    public Deposit(Long id, Student owner, Account account, int amount, DepositType depositType) {
        this.id = id;
        this.owner = owner;
        this.account = account;
        this.amount = amount;
        this.depositType = depositType;
        this.createdDate = LocalDate.now();


    }


}