package com.penny.penny_backend.service;

import com.penny.penny_backend.domain.Account;
import com.penny.penny_backend.domain.AccountHistory;
import com.penny.penny_backend.repository.AccountHistoryRepository;
import com.penny.penny_backend.repository.AccountRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class AccountServiceImpl implements AccountService{
    private final AccountRepository accountRepository;
//    private final AccountHistoryRepository accountHistoryRepository;

    public AccountServiceImpl(AccountRepository accountRepository, AccountHistoryRepository accountHistoryRepository) {
        this.accountRepository = accountRepository;
//        this.accountHistoryRepository = accountHistoryRepository;
    }

    @Override
    public Account createAccount(Long studentId, String nickname, int initialAmount, String accountNum) {
        // 비즈니스 로직: 통장 생성 전 유효성 검사 또는 기타 작업
        // initialAmount 몇으로 설정? 일단 0으로
        // 이미 계좌가 존재하는지 확인
        if (accountRepository.findById(studentId).isPresent()) {
            throw new IllegalArgumentException("해당 학생은 이미 계좌를 가지고 있습니다.");
        }

        Account account = new Account(studentId, nickname, 0, accountNum);
        return accountRepository.save(account);
    }

    @Override
    public Optional<Account> getAccountByStudentId(Long studentId) {
        return accountRepository.findById(studentId);
    }

    @Override
    public List<AccountHistory> getAccountHistoryByStudentId(Long studentId) {
        // 특정 studentId와 accountId로 사용 내역을 조회
        return accountRepository.findById(studentId)
                .map(Account::getAccountHistories)
                .orElse(Collections.emptyList());
//        return accountHistoryRepository.findByAccount_StudentId(studentId);
    }

    @Override
    @Transactional
    public void transfer(Long fromAccountId, Long toAccountId, int amount) {
        // 비즈니스 로직: 이체 로직을 구현
        Account fromAccount = accountRepository.findById(fromAccountId)
                .orElseThrow(() -> new IllegalArgumentException("송금자 계좌가 존재하지 않습니다."));
        Account toAccount = accountRepository.findById(toAccountId)
                .orElseThrow(() -> new IllegalArgumentException("수신자 계좌가 존재하지 않습니다."));

        if (fromAccount.getAmount() >= amount) {
            fromAccount.setAmount(fromAccount.getAmount() - amount);
            toAccount.setAmount(toAccount.getAmount() + amount);
            accountRepository.save(fromAccount);
            accountRepository.save(toAccount);
        } else {
            throw new IllegalArgumentException("잔액이 부족합니다.");
        }
    }
}
