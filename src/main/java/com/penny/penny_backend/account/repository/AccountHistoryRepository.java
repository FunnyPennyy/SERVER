package com.penny.penny_backend.account.repository;

import com.penny.penny_backend.account.entity.AccountHistory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AccountHistoryRepository extends JpaRepository<AccountHistory, Long> {
    // 필요 없을 수도?
//    List<AccountHistory> findByAccount_StudentId(Long studentId);
}
