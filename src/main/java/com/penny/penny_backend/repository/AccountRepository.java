package com.penny.penny_backend.repository;
import com.penny.penny_backend.domain.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Long> {
    Optional<Account> findByAccountNum(String accountNum);

    //Optional<Account> findByUserId(String userId);
}
//