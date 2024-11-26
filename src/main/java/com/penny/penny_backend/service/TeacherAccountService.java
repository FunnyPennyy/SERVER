package com.penny.penny_backend.service;

import com.penny.penny_backend.domain.Account;
import com.penny.penny_backend.domain.TeacherAccount;
import com.penny.penny_backend.domain.TeacherAccountHistory;

import java.util.List;
import java.util.Optional;

public interface TeacherAccountService {
    // 개인 통장 생성 (create) - 이미 생성자 있으니까 필요 없나?
    TeacherAccount createAccount(Long teacherId, String nickname);

    // 개인 통장 조회
    Optional<TeacherAccount> getAccountByTeacherId(Long teacherId);

    // 개인 통장 사용 내역 조회 (계좌 사용 내역 - 입출금)
    List<TeacherAccountHistory> getAccountHistoryByTeacherId(Long teacherId);

    // 학생들 월급 지급
    void paySalaryToStudents(Long teacherId);
//
    void receiveFromStudent(TeacherAccount teacherAccount, Account studentAccount,
                            int amount, String teacherName, String studentName);

    void deleteTeacherAccount(Long teacherId);

    boolean isTeacherAccount(String accountNum);

//    void addTeacherAccountHistory(TeacherAccount account, String content, int amount, boolean inOrOut, Long counterpartyId,
//                                          String myName, String counterpartyName);
}
