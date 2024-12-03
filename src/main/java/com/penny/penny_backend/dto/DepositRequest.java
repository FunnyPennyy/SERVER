package com.penny.penny_backend.dto;

import lombok.*;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DepositRequest {
    private Long ownerId; // 사용자 ID
    private Long depositTypeId; // 예금 상품 ID (DepositType FK)
    private int amount; // 예금 금액
}
