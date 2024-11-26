package com.penny.penny_backend.service;


import com.penny.penny_backend.domain.Deposit;
import com.penny.penny_backend.domain.DepositType;
import com.penny.penny_backend.repository.DepositRepository;
import com.penny.penny_backend.repository.DepositTypeRepository;
import com.penny.penny_backend.dto.DepositDto;
import com.penny.penny_backend.dto.DepositTypeDto;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DepositService {
    private final DepositRepository depositRepository;
    private final DepositTypeRepository depositTypeRepository;

    public DepositService(DepositRepository depositRepository, DepositTypeRepository depositTypeRepository) {
        this.depositRepository = depositRepository;
        this.depositTypeRepository = depositTypeRepository;
    }

    public List<DepositDto> getAllDeposits() {
        return depositRepository.findAll().stream()
                .map(deposit -> DepositDto.builder()
                        .id(deposit.getId())
                        .user(deposit.getUser())
                        .amount(deposit.getAmount())
                        .createdDate(deposit.getCreatedDate())
                        .maturityDate(deposit.getMaturityDate())
                        .terminationDate(deposit.getTerminationDate())
                        .depositTypeId(deposit.getDepositType().getId())
                        .build())
                .collect(Collectors.toList());
    }

    public DepositDto createDeposit(DepositDto depositDto) {
        DepositType depositType = depositTypeRepository.findById(depositDto.getDepositTypeId())
                .orElseThrow(() -> new RuntimeException("DepositType not found"));
        Deposit deposit = Deposit.builder()
                .user(depositDto.getUser())
                .amount(depositDto.getAmount())
                .depositType(depositType)
                .build();
        depositRepository.save(deposit);
        return DepositDto.builder()
                .id(deposit.getId())
                .user(deposit.getUser())
                .amount(deposit.getAmount())
                .createdDate(deposit.getCreatedDate())
                .maturityDate(deposit.getMaturityDate())
                .terminationDate(deposit.getTerminationDate())
                .depositTypeId(deposit.getDepositType().getId())
                .build();
    }

    @Transactional
    public String terminateDeposit(Long depositId, String userId) {
        Deposit deposit = depositRepository.findById(depositId)
                .orElseThrow(() -> new RuntimeException("Deposit not found"));

        // 해지일 설정
        deposit.setTerminationDate(LocalDate.now());

        // 중도해지 판단
        if (deposit.getTerminationDate().isBefore(deposit.getMaturityDate())) {
            deposit.setAmount(0); // 중도해지: 이자 지급 안함
            return "중도해지 처리되었습니다.";
        } else {
            double interest = deposit.getAmount() * (deposit.getDepositType().getInterest() / 100.0);
            double totalAmount = deposit.getAmount() + interest;
            // 사용자의 account 모델에 totalAmount 추가 로직 구현 필요
            return "만기해지 처리되었습니다. 지급 금액: " + totalAmount;
        }
    }

    public List<DepositTypeDto> getAllDepositTypes() {
        return depositTypeRepository.findAll().stream()
                .map(depositType -> DepositTypeDto.builder()
                        .id(depositType.getId())
                        .name(depositType.getName())
                        .duration(depositType.getDuration())
                        .interest(depositType.getInterest())
                        .build())
                .collect(Collectors.toList());
    }

    public DepositTypeDto createDepositType(DepositTypeDto depositTypeDto) {
        DepositType depositType = DepositType.builder()
                .name(depositTypeDto.getName())
                .duration(depositTypeDto.getDuration())
                .interest(depositTypeDto.getInterest())
                .build();
        depositTypeRepository.save(depositType);
        return DepositTypeDto.builder()
                .id(depositType.getId())
                .name(depositType.getName())
                .duration(depositType.getDuration())
                .interest(depositType.getInterest())
                .build();
    }
}