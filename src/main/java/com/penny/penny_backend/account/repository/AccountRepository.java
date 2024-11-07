package com.penny.penny_backend.account.repository;
import com.penny.penny_backend.account.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, Long> {
}
