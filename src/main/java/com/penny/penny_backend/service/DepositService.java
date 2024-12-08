package com.penny.penny_backend.service;


import com.penny.penny_backend.domain.Deposit;
import com.penny.penny_backend.domain.DepositType;
import com.penny.penny_backend.domain.Account;
import com.penny.penny_backend.domain.Student;
import com.penny.penny_backend.dto.DepositRequest;
import com.penny.penny_backend.dto.DepositResponse;
import com.penny.penny_backend.dto.DepositTypeRequest;
import com.penny.penny_backend.dto.DepositTypeResponse;
import com.penny.penny_backend.repository.DepositRepository;
import com.penny.penny_backend.repository.DepositTypeRepository;
import com.penny.penny_backend.repository.AccountRepository;
import com.penny.penny_backend.repository.StudentRepository;
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
    private final StudentRepository studentRepository;

    public DepositService(DepositRepository depositRepository, DepositTypeRepository depositTypeRepository, AccountRepository accountRepository, StudentRepository studentRepository) {
        this.depositRepository = depositRepository;
        this.depositTypeRepository = depositTypeRepository;
        this.accountRepository = accountRepository;
        this.studentRepository = studentRepository;
    }

    // 존재하는 모든 예금 조회
    public List<DepositResponse> getAllDeposits() {
        return depositRepository.findAll().stream()
                .map(this::convertToDepositResponse)
                .collect(Collectors.toList());
    }

    // ownerId로 특정 사용자의 모든 예금 조회
    public List<DepositResponse> getDepositsByOwner(Long ownerId) {
        Student owner = studentRepository.findById(ownerId)
                .orElseThrow(() -> new IllegalArgumentException("Student not found: " + ownerId));

        List<Deposit> deposits = depositRepository.findByOwner(owner);

        return deposits.stream()
                .map(this::convertToDepositResponse)
                .collect(Collectors.toList());
    }

    public DepositResponse createDeposit(DepositRequest depositRequest) {

        Student owner = studentRepository.findById(depositRequest.getOwnerId())
                .orElseThrow(() -> new RuntimeException("Owner student not found"));

        Account account = accountRepository.findById(owner.getId())
                .orElseThrow(() -> new RuntimeException("Account not found for student ID"));

        DepositType depositType = depositTypeRepository.findById(depositRequest.getDepositTypeId())
                .orElseThrow(() -> new RuntimeException("DepositType not found"));

        if (depositRequest.getAmount() > account.getAmount()) {
            throw new RuntimeException("Deposit amount cannot exceed account balance.");
        }

        account.setAmount(account.getAmount() - depositRequest.getAmount());
        accountRepository.save(account);

        // 현재 날짜를 생성일로 설정
        LocalDate createdDate = LocalDate.now();

        // 만기일 계산 로직
        LocalDate maturityDate = calculateMaturityDate(LocalDate.now(), depositType.getDuration());

        Deposit deposit = Deposit.builder()
                .owner(owner)
                .account(account)
                .amount(depositRequest.getAmount())
                .depositType(depositType)
                .createdDate(createdDate)
                .maturityDate(maturityDate)
                .build();

        depositRepository.save(deposit);

        return convertToDepositResponse(deposit);
    }

    private LocalDate calculateMaturityDate(LocalDate createdDate, int durationMonths) {
        if (durationMonths > 0) {
            return createdDate.plusMonths(durationMonths);
        } else {
            return null; // 기간이 0 이하일 경우 null
        }
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
            account.setAmount(account.getAmount() + deposit.getAmount());
            accountRepository.save(account);

            //deposit.setAmount(0); // 중도해지: 이자 지급 안함
            return "중도해지 처리되었습니다.";
        } else {
            int interestsum = (int) (deposit.getAmount() * (deposit.getDepositType().getInterest() / 100.0));
            int totalAmount = deposit.getAmount() + interestsum;

            // 기존 계좌 잔액에 지급 금액 추가
            account.setAmount(account.getAmount() + totalAmount);
            accountRepository.save(account);

            // 사용자의 account 모델에 totalAmount 추가 로직 구현 필요
            return "만기해지 처리되었습니다. 지급 금액: " + totalAmount;
        }
    }

    public List<DepositTypeResponse> getAllDepositTypes() {
        return depositTypeRepository.findAll().stream()
                .map(this::convertToDepositTypeResponse) // 공통 변환 메서드 호출
                .collect(Collectors.toList());
    }

    public DepositTypeResponse createDepositType(DepositTypeRequest depositTypeRequest) {
        DepositType depositType = DepositType.builder()
                .name(depositTypeRequest.getName())
                .duration(depositTypeRequest.getDuration())
                .interest(depositTypeRequest.getInterest())
                .build();

        depositTypeRepository.save(depositType);

        return convertToDepositTypeResponse(depositType);
    }

    private DepositResponse convertToDepositResponse(Deposit deposit) {
        return DepositResponse.builder()
                .id(deposit.getId())
                .ownerId(deposit.getOwner().getId())
                .accountAmount(deposit.getAccount().getAmount())
                .amount(deposit.getAmount())
                .createdDate(deposit.getCreatedDate())
                .maturityDate(deposit.getMaturityDate())
                .terminationDate(deposit.getTerminationDate())
                .depositTypeName(deposit.getDepositType().getName())
                .build();
    }

    private DepositTypeResponse convertToDepositTypeResponse(DepositType depositType) {
        return DepositTypeResponse.builder()
                .id(depositType.getId())
                .name(depositType.getName())
                .duration(depositType.getDuration())
                .interest(depositType.getInterest())
                .build();
    }

}