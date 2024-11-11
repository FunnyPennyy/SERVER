package com.penny.penny_backend.service;

import com.penny.penny_backend.domain.Account;
import com.penny.penny_backend.domain.AccountHistory;
import com.penny.penny_backend.domain.TeacherAccount;
import com.penny.penny_backend.repository.AccountHistoryRepository;
import com.penny.penny_backend.repository.AccountRepository;
import com.penny.penny_backend.repository.TeacherAccountRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class AccountServiceImpl implements AccountService{
    private final AccountRepository accountRepository;
    private final AccountHistoryRepository accountHistoryRepository;
    private final TeacherAccountService teacherAccountService;
    private final TeacherAccountRepository teacherAccountRepository;

    public AccountServiceImpl(AccountRepository accountRepository, AccountHistoryRepository accountHistoryRepository,
                              TeacherAccountService teacherAccountService, TeacherAccountRepository teacherAccountRepository) {
        this.accountRepository = accountRepository;
        this.accountHistoryRepository = accountHistoryRepository;
        this.teacherAccountService = teacherAccountService;
        this.teacherAccountRepository = teacherAccountRepository;
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
    public void transferToStudent(String fromAccountNum, String toAccountNum, int amount) {
        // 송금자 계좌 조회 (항상 학생 계좌)
        Account fromAccount = accountRepository.findByAccountNum(fromAccountNum)
                .orElseThrow(() -> new IllegalArgumentException("송금자 계좌가 존재하지 않습니다."));
        // 수신자 계좌 조회
        Account toAccount = accountRepository.findByAccountNum(toAccountNum)
                .orElseThrow(() -> new IllegalArgumentException("수신자 계좌가 존재하지 않습니다."));

        // 잔액 확인 및 이체 처리
        if (fromAccount.getAmount() >= amount) {
            fromAccount.setAmount(fromAccount.getAmount() - amount);
            toAccount.setAmount(toAccount.getAmount() + amount);

            // 송금자 계좌 내역 추가
            addAccountHistory(fromAccount, "송금", amount, true, toAccount.getStudentId());
            // 수신자 계좌 내역 추가
            addAccountHistory(toAccount, "입금", amount, false, fromAccount.getStudentId());

            // 변경된 계좌와 내역 저장
            accountRepository.save(fromAccount);
            accountRepository.save(toAccount);
        } else {
            throw new IllegalArgumentException("잔액이 부족합니다.");
        }
    }

    @Override
    @Transactional
    public void transferToTeacher(String fromAccountNum, String toAccountNum, int amount) {
        // 학생 계좌 조회 및 잔액 확인
        Account fromAccount = accountRepository.findByAccountNum(fromAccountNum)
                .orElseThrow(() -> new IllegalArgumentException("학생 계좌가 존재하지 않습니다."));
        TeacherAccount toAccount = teacherAccountRepository.findByAccountNum(toAccountNum)
                .orElseThrow(() -> new IllegalArgumentException("선생님 계좌가 존재하지 않습니다."));

        if (fromAccount.getAmount() < amount) {
            throw new IllegalArgumentException("잔액이 부족합니다.");
        }

        // 학생 계좌 잔액 업데이트 및 계좌 내역 생성
        fromAccount.setAmount(fromAccount.getAmount() - amount);

        addAccountHistory(fromAccount, "송금", amount, true, toAccount.getTeacherId());

        accountRepository.save(fromAccount);

        // 선생님 계좌 업데이트와 계좌 내역 생성은 TeacherAccountService에 위임
        teacherAccountService.receiveFromStudent(toAccount, fromAccount, amount);
    }

    @Override
    public boolean isStudentAccount(String accountNum) {
        return accountRepository.findByAccountNum(accountNum).isPresent();
    }

    private void addAccountHistory(Account account, String content, int amount, boolean inOrOut, Long counterpartyId) {
        AccountHistory accountHistory = new AccountHistory(content, amount, inOrOut, counterpartyId, account);
//        account.addAccountHistory(accountHistory);
        accountHistoryRepository.save(accountHistory);
    }
}
