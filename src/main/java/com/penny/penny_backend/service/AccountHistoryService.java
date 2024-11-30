package com.penny.penny_backend.service;

import com.penny.penny_backend.domain.Account;
import com.penny.penny_backend.domain.TeacherAccount;

public interface AccountHistoryService {
    void addTeacherAccountHistory(TeacherAccount account, String content, int amount, boolean inOrOut, Long counterpartyId,
                                  String myName, String counterpartyName);

    void addStudentAccountHistory(Account account, String content, int amount, boolean inOrOut, Long counterpartyId,
                                  String myName, String counterpartyName);
}
