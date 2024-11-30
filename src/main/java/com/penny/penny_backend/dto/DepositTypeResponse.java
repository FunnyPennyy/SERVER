package com.penny.penny_backend.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DepositTypeResponse {
    private Long id; // 예금 상품 ID
    private String name; // 예금 상품 이름
    private int duration; // 기간 (개월)
    private int interest; // 이자율 (% 단위)
}
