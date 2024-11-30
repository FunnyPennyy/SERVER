package com.penny.penny_backend.repository;

import com.penny.penny_backend.domain.AccountHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountHistoryRepository extends JpaRepository<AccountHistory, Long> {
    // 필요 없을 수도?
//    List<AccountHistory> findByAccount_StudentId(Long studentId);
}
//