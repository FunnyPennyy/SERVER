package com.penny.penny_backend.service;

import com.penny.penny_backend.domain.Account;
import com.penny.penny_backend.domain.AccountHistory;
import com.penny.penny_backend.domain.TeacherAccount;
import com.penny.penny_backend.domain.TeacherAccountHistory;
import com.penny.penny_backend.repository.*;
import org.springframework.stereotype.Service;

@Service
public class AccountHistoryServiceImpl implements AccountHistoryService {
    private final TeacherAccountHistoryRepository teacherAccountHistoryRepository;
    private final AccountHistoryRepository accountHistoryRepository;

    public AccountHistoryServiceImpl(TeacherAccountHistoryRepository teacherAccountHistoryRepository,
                                     AccountHistoryRepository accountHistoryRepository) {
        this.teacherAccountHistoryRepository = teacherAccountHistoryRepository;
        this.accountHistoryRepository = accountHistoryRepository;
    }

    @Override
    public void addTeacherAccountHistory(TeacherAccount account, String content, int amount, boolean inOrOut, Long counterpartyId,
                                         String myName, String counterpartyName) {
        TeacherAccountHistory teacherAccountHistory = new TeacherAccountHistory(content, amount, inOrOut, counterpartyId, myName, counterpartyName, account);
        teacherAccountHistoryRepository.save(teacherAccountHistory);
    }

    @Override
    public void addStudentAccountHistory(Account account, String content, int amount, boolean inOrOut, Long counterpartyId,
                                  String myName, String counterpartyName) {
        AccountHistory accountHistory = new AccountHistory(content, amount, inOrOut, counterpartyId, myName, counterpartyName, account);
        accountHistoryRepository.save(accountHistory);
    }
}
