package com.penny.penny_backend.service;

import com.penny.penny_backend.domain.*;
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
//
    public AccountServiceImpl(AccountRepository accountRepository, AccountHistoryRepository accountHistoryRepository,
                              TeacherAccountService teacherAccountService, TeacherAccountRepository teacherAccountRepository) {
        this.accountRepository = accountRepository;
        this.accountHistoryRepository = accountHistoryRepository;
        this.teacherAccountService = teacherAccountService;
        this.teacherAccountRepository = teacherAccountRepository;
    }

    @Override
    public Account createAccount(Long studentId, String nickname) {
        // initialAmount 몇으로 설정? 일단 0으로
        // 학생이 존재하는지 확인 필요?

        // 이미 계좌가 존재하는지 확인
        if (accountRepository.findById(studentId).isPresent()) {
            throw new IllegalArgumentException("해당 학생은 이미 계좌를 가지고 있습니다.");
        }

        // 계좌번호 생성 알고리즘 작성
        String accountNum = "1111-11-1111";

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
        // 사용자가 사유 입력하게 하려면 content를 인자로 받도록 수정 필요.

        // 송금자 계좌 조회 (항상 학생 계좌)
        Account fromAccount = accountRepository.findByAccountNum(fromAccountNum)
                .orElseThrow(() -> new IllegalArgumentException("송금자 계좌가 존재하지 않습니다."));
        // 수신자 계좌 조회
        Account toAccount = accountRepository.findByAccountNum(toAccountNum)
                .orElseThrow(() -> new IllegalArgumentException("수신자 계좌가 존재하지 않습니다."));

        Student fromStudent = studentRepository.findById(fromAccount.getStudentId());
        Student toStudent = studentRepository.findById(toAccount.getStudentId());

        // 잔액 확인 및 이체 처리
        if (fromAccount.getAmount() < amount) {
            throw new IllegalArgumentException("잔액이 부족합니다.");
        }

        fromAccount.setAmount(fromAccount.getAmount() - amount);
        toAccount.setAmount(toAccount.getAmount() + amount);

        // 송금자 계좌 내역 추가
        addAccountHistory(fromAccount, "송금", amount, true, toAccount.getStudentId(), fromStudent.getName(), toStudent.getName());
        // 수신자 계좌 내역 추가
        addAccountHistory(toAccount, "입금", amount, false, fromAccount.getStudentId(), toStudent.getName(), fromStudent.getName());

        // 변경된 계좌와 내역 저장
        accountRepository.save(fromAccount);
        accountRepository.save(toAccount);
    }

    @Override
    @Transactional
    public void transferToTeacher(String fromAccountNum, String toAccountNum, int amount) {
        // 학생 계좌 조회 및 잔액 확인
        Account fromAccount = accountRepository.findByAccountNum(fromAccountNum)
                .orElseThrow(() -> new IllegalArgumentException("학생 계좌가 존재하지 않습니다."));
        TeacherAccount toAccount = teacherAccountRepository.findByAccountNum(toAccountNum)
                .orElseThrow(() -> new IllegalArgumentException("선생님 계좌가 존재하지 않습니다."));

        Student fromStudent = studentRepository.findById(fromAccount.getStudentId());
        Teacher teacher = teacherRepository.findById(toAccount.getTeacherId());

        if (fromAccount.getAmount() < amount) {
            throw new IllegalArgumentException("잔액이 부족합니다.");
        }

        // 학생 계좌 잔액 업데이트 및 계좌 내역 생성
        fromAccount.setAmount(fromAccount.getAmount() - amount);

        // 송금한 학생 계좌 내역 추가
        addAccountHistory(fromAccount, "송금", amount, true, toAccount.getTeacherId(), fromStudent.getName(), teacher.getName());
        // 선생님 계좌 내역 추가
//        teacherAccountService.addTeacherAccountHistory(toAccount, "입금", amount, false, fromAccount.getStudentId(), teacher.getName(), fromStudent.getName());

        accountRepository.save(fromAccount);

        // 선생님 계좌 업데이트와 계좌 내역 생성은 TeacherAccountService에 위임
        teacherAccountService.receiveFromStudent(toAccount, fromAccount, amount, teacher.getName(), fromStudent.getName());
    }

    @Override
    public void deleteAccount(Long studentId) {
        // 계좌 존재 여부 확인
        Account account = accountRepository.findById(studentId)
                .orElseThrow(() -> new IllegalArgumentException("해당 ID의 계좌를 찾을 수 없습니다."));

        // 계좌 삭제
        accountRepository.delete(account);
    }

    @Override
    public boolean isStudentAccount(String accountNum) {
        return accountRepository.findByAccountNum(accountNum).isPresent();
    }

    private void addAccountHistory(Account account, String content, int amount, boolean inOrOut, Long counterpartyId,
                                   String myName, String counterpartyName) {
        AccountHistory accountHistory = new AccountHistory(content, amount, inOrOut, counterpartyId, myName, counterpartyName, account);
//        account.addAccountHistory(accountHistory);
        accountHistoryRepository.save(accountHistory);
    }
}
