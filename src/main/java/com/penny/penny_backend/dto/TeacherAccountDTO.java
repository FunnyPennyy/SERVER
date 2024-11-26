package com.penny.penny_backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TeacherAccountDTO {
    private Long teacherId;   // 선생님 ID
    private String nickname;  // 계좌 닉네임
    private int balance;      // 계좌 잔액
}