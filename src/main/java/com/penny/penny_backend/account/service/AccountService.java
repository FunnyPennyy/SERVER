package com.penny.penny_backend.account.service;

import com.penny.penny_backend.account.entity.Account;
import com.penny.penny_backend.account.entity.AccountHistory;

import java.util.List;
import java.util.Optional;

public interface AccountService {
    // 개인 통장 생성 (create) - 이미 생성자 있으니까 필요 없나?
    Account createAccount(Long studentId, String nickname, int initialAmount, String accountNum);

    // 개인 통장 조회
    Optional<Account> getAccountByStudentId(Long studentId);

    // 개인 통장 사용 내역 조회 (계좌 사용 내역 - 입출금)
    List<AccountHistory> getAccountHistoryByStudentId(Long studentId);

    // 계좌 이체 (변경 사항 저장)
    void transfer(Long fromAccountId, Long toAccountId, int amount);
}
