package com.penny.penny_backend.service;

import com.penny.penny_backend.domain.Account;
import com.penny.penny_backend.domain.AccountHistory;

import java.util.List;
import java.util.Optional;

public interface AccountService {
    // 개인 통장 생성 (create) - 이미 생성자 있으니까 필요 없나?
    Account createAccount(Long studentId, String nickname, int initialAmount, String accountNum);

    // 개인 통장 조회
    Optional<Account> getAccountByStudentId(Long studentId);

    // 개인 통장 사용 내역 조회 (계좌 사용 내역 - 입출금)
    List<AccountHistory> getAccountHistoryByStudentId(Long studentId);

    // 계좌 이체 (변경 사항 저장) + 계좌 내역 추가
    void transferToStudent(String fromAccountNum, String toAccountNum, int amount);

    // 선생님한테 계좌 이체
    void transferToTeacher(String fromAccountNum, String toAccountNum, int amount);
}
