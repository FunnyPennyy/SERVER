package com.penny.penny_backend.dto;

import lombok.*;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DepositRequest {
    private String user; // 사용자 ID
    private double amount; // 예치 금액
    private Long depositTypeId; // 예금 상품 ID (DepositType FK)
}
