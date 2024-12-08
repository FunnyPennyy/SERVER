package com.penny.penny_backend.dto;

import com.penny.penny_backend.domain.Account;
import com.penny.penny_backend.domain.Deposit;
import com.penny.penny_backend.domain.DepositType;
import lombok.*;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DepositResponse {
    private Long id; // 예금 ID
    private Long ownerId; // 예금 통장 주인 ID (Student ID)
    private int accountAmount; // 계좌 잔액
    private int amount; // 예금 금액
    private LocalDate createdDate; // 생성일
    private LocalDate maturityDate; // 만기일
    private LocalDate terminationDate; // 해지일
    private String depositTypeName; // 예금 상품 이름

    public DepositResponse(Deposit deposit) {
        this.id = deposit.getId();
        this.ownerId = deposit.getOwner().getId();
        this.accountAmount  = deposit.getAccount().getAmount();
        this.amount  = deposit.getAmount();
        this.createdDate = deposit.getCreatedDate();
        this.maturityDate = deposit.getMaturityDate();
        this.terminationDate = deposit.getTerminationDate();
        this.depositTypeName = deposit.getDepositType().getName();
    }
}
