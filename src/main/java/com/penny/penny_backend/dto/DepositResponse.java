package com.penny.penny_backend.dto;

import lombok.*;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DepositResponse {
    private Long id; // 예금 통장 ID
    private String user; // 사용자 ID
    private double amount; // 예치 금액
    private LocalDate createdDate; // 생성일
    private LocalDate maturityDate; // 만기일
    private LocalDate terminationDate; // 해지일
    private String depositTypeName; // 예금 상품 이름
    private int depositTypeDuration; // 예금 상품 기간
    private int depositTypeInterest; // 예금 상품 이자율
}
