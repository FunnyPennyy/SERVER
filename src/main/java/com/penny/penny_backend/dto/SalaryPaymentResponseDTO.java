package com.penny.penny_backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SalaryPaymentResponseDTO {
    private String message; // 성공 또는 실패 메시지
}