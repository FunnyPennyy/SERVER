package com.penny.penny_backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransferRequestDTO {
    private String fromAccountNum; // 송금자 계좌 번호
    private String toAccountNum;   // 수신자 계좌 번호
    private int amount;            // 이체 금액
}
//