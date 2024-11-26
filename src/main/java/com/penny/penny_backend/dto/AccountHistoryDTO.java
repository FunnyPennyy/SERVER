package com.penny.penny_backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountHistoryDTO {
    private String content;     // 사용 내역 설명
    private int amount;         // 거래 금액
    private boolean inOrOut;    // 입금(true) / 출금(false)
    private LocalDateTime datetime; // 거래 날짜
}
//