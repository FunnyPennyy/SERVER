package com.penny.penny_backend.service;

import com.penny.penny_backend.domain.*;
import com.penny.penny_backend.repository.*;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class AccountServiceImpl implements AccountService{
    private final AccountRepository accountRepository;
    private final TeacherAccountService teacherAccountService;
    private final TeacherAccountRepository teacherAccountRepository;
    private final StudentRepository studentRepository;
    private final TeacherRepository teacherRepository;
    private final AccountHistoryService accountHistoryService;

    public AccountServiceImpl(AccountRepository accountRepository, TeacherAccountService teacherAccountService,
                              TeacherAccountRepository teacherAccountRepository,
                              StudentRepository studentRepository, TeacherRepository teacherRepository,
                              AccountHistoryService accountHistoryService) {
        this.accountRepository = accountRepository;
        this.teacherAccountService = teacherAccountService;
        this.teacherAccountRepository = teacherAccountRepository;
        this.studentRepository = studentRepository;
        this.teacherRepository = teacherRepository;
        this.accountHistoryService = accountHistoryService;
    }

    @Override
    public Account createAccount(Long studentId, String nickname) {
        // initialAmount 몇으로 설정? 일단 500,000으로
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new IllegalArgumentException("학생이 존재하지 않습니다"));

        // 이미 계좌가 존재하는지 확인
        if (accountRepository.findByStudent_StudentId(studentId).isPresent()) {
            throw new IllegalArgumentException("해당 학생은 이미 계좌를 가지고 있습니다.");
        }

        // 계좌번호 생성 알고리즘 작성
        String accountNum = "1111-11-1111";

        Account account = new Account(nickname, 500000, accountNum, student);
        return accountRepository.save(account);
    }

    @Override
    public Optional<Account> getAccountByStudentId(Long studentId) {
        return accountRepository.findById(studentId);
    }

    @Override
    public List<AccountHistory> getAccountHistoryByStudentId(Long studentId) {
        // 계좌 존재 여부 확인
        Account account = accountRepository.findById(studentId)
                .orElseThrow(() -> new IllegalArgumentException("해당 Student ID의 계좌가 존재하지 않습니다."));

        // 존재하는 경우, 계좌 사용 내역 반환
        return account.getAccountHistories();
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

        Student fromStudent = studentRepository.findById(fromAccount.getStudent().getStudentId())
                .orElseThrow(() -> new IllegalArgumentException("학생이 존재하지 않습니다."));
        Student toStudent = studentRepository.findById(toAccount.getStudent().getStudentId())
                .orElseThrow(() -> new IllegalArgumentException("학생이 존재하지 않습니다."));

        // 잔액 확인 및 이체 처리
        if (fromAccount.getAmount() < amount) {
            throw new IllegalArgumentException("잔액이 부족합니다.");
        }

        fromAccount.setAmount(fromAccount.getAmount() - amount);
        toAccount.setAmount(toAccount.getAmount() + amount);

        // 송금자 계좌 내역 추가
        accountHistoryService.addStudentAccountHistory(fromAccount, "송금", amount, true, toAccount.getStudent().getStudentId(),
                fromStudent.getStudentName(), toStudent.getStudentName());
        // 수신자 계좌 내역 추가
        accountHistoryService.addStudentAccountHistory(toAccount, "입금", amount, false, fromAccount.getStudent().getStudentId(),
                toStudent.getStudentName(), fromStudent.getStudentName());

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

        Student fromStudent = studentRepository.findById(fromAccount.getStudent().getStudentId())
                .orElseThrow(() -> new IllegalArgumentException("학생이 존재하지 않습니다."));
        Teacher teacher = teacherRepository.findById(toAccount.getTeacher().getTeacherId())
                .orElseThrow(() -> new IllegalArgumentException("선생님이 존재하지 않습니다."));

        if (fromAccount.getAmount() < amount) {
            throw new IllegalArgumentException("잔액이 부족합니다.");
        }

        // 학생 계좌 잔액 업데이트 및 계좌 내역 생성
        fromAccount.setAmount(fromAccount.getAmount() - amount);

        // 송금한 학생 계좌 내역 추가
        accountHistoryService.addStudentAccountHistory(fromAccount, "송금", amount, true, toAccount.getTeacher().getTeacherId(),
                fromStudent.getStudentName(), teacher.getTeacherName());
        // 선생님 계좌 내역 추가
//        teacherAccountService.addTeacherAccountHistory(toAccount, "입금", amount, false, fromAccount.getStudentId(), teacher.getName(), fromStudent.getName());

        accountRepository.save(fromAccount);

        // 선생님 계좌 업데이트와 계좌 내역 생성은 TeacherAccountService에 위임
        teacherAccountService.receiveFromStudent(toAccount, fromAccount, amount,
                teacher.getTeacherName(), fromStudent.getStudentName());
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

//    @Override
//    public void addAccountHistory(Account account, String content, int amount, boolean inOrOut, Long counterpartyId,
//                                   String myName, String counterpartyName) {
//        AccountHistory accountHistory = new AccountHistory(content, amount, inOrOut, counterpartyId, myName, counterpartyName, account);
////        account.addAccountHistory(accountHistory);
//        accountHistoryRepository.save(accountHistory);
//    }
}
