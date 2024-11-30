package com.penny.penny_backend.service;


import com.penny.penny_backend.domain.Deposit;
import com.penny.penny_backend.domain.DepositType;
import com.penny.penny_backend.domain.Account;
import com.penny.penny_backend.dto.DepositRequest;
import com.penny.penny_backend.dto.DepositResponse;
import com.penny.penny_backend.dto.DepositTypeRequest;
import com.penny.penny_backend.dto.DepositTypeResponse;
import com.penny.penny_backend.repository.DepositRepository;
import com.penny.penny_backend.repository.DepositTypeRepository;
import com.penny.penny_backend.repository.AccountRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DepositService {
    private final DepositRepository depositRepository;
    private final DepositTypeRepository depositTypeRepository;
    private final AccountRepository accountRepository;

    public DepositService(DepositRepository depositRepository, DepositTypeRepository depositTypeRepository, AccountRepository accountRepository) {
        this.depositRepository = depositRepository;
        this.depositTypeRepository = depositTypeRepository;
        this.accountRepository = accountRepository;
    }

    public List<DepositResponse> getAllDeposits() {
        return depositRepository.findAll().stream()
                .map(deposit -> DepositResponse.builder()
                        .id(deposit.getId())
                        .user(deposit.getUser())
                        .amount(deposit.getAmount())
                        .createdDate(deposit.getCreatedDate())
                        .maturityDate(deposit.getMaturityDate())
                        .terminationDate(deposit.getTerminationDate())
                        .depositTypeName(deposit.getDepositType().getName())
                        .depositTypeDuration(deposit.getDepositType().getDuration())
                        .depositTypeInterest(deposit.getDepositType().getInterest())
                        .build())
                .collect(Collectors.toList());
    }

    public DepositResponse createDeposit(DepositRequest depositRequest) {
        DepositType depositType = depositTypeRepository.findById(depositRequest.getDepositTypeId())
                .orElseThrow(() -> new RuntimeException("DepositType not found"));

        Deposit deposit = Deposit.builder()
                .user(depositRequest.getUser())
                .amount(depositRequest.getAmount())
                .depositType(depositType)
                .build();

        depositRepository.save(deposit);


        return DepositResponse.builder()
                .id(deposit.getId())
                .user(deposit.getUser())
                .amount(deposit.getAmount())
                .createdDate(deposit.getCreatedDate())
                .maturityDate(deposit.getMaturityDate())
                .terminationDate(deposit.getTerminationDate())
                .depositTypeName(depositType.getName())
                .depositTypeDuration(depositType.getDuration())
                .depositTypeInterest(depositType.getInterest())
                .build();
    }

    @Transactional
    public String terminateDeposit(Long depositId, Long studentId) {
        Deposit deposit = depositRepository.findById(depositId)
                .orElseThrow(() -> new RuntimeException("Deposit not found"));

        // 2. 사용자 ID로 Account 조회
        Account account = accountRepository.findById(studentId) // 수정
                .orElseThrow(() -> new RuntimeException("Account not found for user ID: " + studentId));

        // 해지일 설정
        deposit.setTerminationDate(LocalDate.now());

        // 중도해지 판단
        if (deposit.getTerminationDate().isBefore(deposit.getMaturityDate())) {
            //deposit.setAmount(0); // 중도해지: 이자 지급 안함
            return "중도해지 처리되었습니다.";
        } else {
            double interest = deposit.getAmount() * (deposit.getDepositType().getInterest() / 100.0);
            double totalAmount = deposit.getAmount() + interest;

            // 기존 계좌 잔액에 지급 금액 추가
            account.setAmount((int) (account.getAmount() + totalAmount));
            accountRepository.save(account);

            // 사용자의 account 모델에 totalAmount 추가 로직 구현 필요
            return "만기해지 처리되었습니다. 지급 금액: " + totalAmount;
        }
    }

    public List<DepositTypeResponse> getAllDepositTypes() {
        return depositTypeRepository.findAll().stream()
                .map(depositType -> DepositTypeResponse.builder()
                        .id(depositType.getId())
                        .name(depositType.getName())
                        .duration(depositType.getDuration())
                        .interest(depositType.getInterest())
                        .build())
                .collect(Collectors.toList());
    }

    public DepositTypeResponse createDepositType(DepositTypeRequest depositTypeRequest) {
        DepositType depositType = DepositType.builder()
                .name(depositTypeRequest.getName())
                .duration(depositTypeRequest.getDuration())
                .interest(depositTypeRequest.getInterest())
                .build();
        depositTypeRepository.save(depositType);
        return DepositTypeResponse.builder()
                .id(depositType.getId())
                .name(depositType.getName())
                .duration(depositType.getDuration())
                .interest(depositType.getInterest())
                .build();
    }
}