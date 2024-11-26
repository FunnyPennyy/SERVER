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

    @Getter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // 예금통장 id

    //@ManyToOne
    //@JoinColumn(name = "user_id", nullable = false) // User FK
    private String user; // 통장 주인

    private double amount;

    private LocalDate createdDate = LocalDate.now(); // 생성일: 현재 날짜

    private LocalDate maturityDate; // 만기일
    private LocalDate terminationDate = null; // 해지일

    @ManyToOne
    @JoinColumn(name = "depositType_id", nullable = false) // FK for deposit type
    private DepositType depositType;


    @Builder
    public Deposit(Long id, String user, double amount, DepositType depositType) {
        this.id = id;
        this.amount= amount;
        this.createdDate = LocalDate.now();
        this.user = user;

        this.depositType = depositType;

        // 만기일 계산: 생성일에 DepositType 의 기간(duration)을 추가
        if (depositType != null && depositType.getDuration() > 0) {
            this.maturityDate = this.createdDate.plusMonths(depositType.getDuration());
        } else {
            this.maturityDate = null; // 기간이 0 이하라면 null 로 설정
        }
    }


}