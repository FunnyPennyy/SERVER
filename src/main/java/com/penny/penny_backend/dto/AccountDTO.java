package com.penny.penny_backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountDTO {
    private Long studentId;  // 학생 ID
    private String studentName; // 학생 이름
    private String accountNum; // 계좌 번호
    private String nickname; // 닉네임
    private int balance;     // 잔액
}
