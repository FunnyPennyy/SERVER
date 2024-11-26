package com.penny.penny_backend.repository;

import com.penny.penny_backend.domain.Deposit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface DepositRepository extends JpaRepository<Deposit, Long> {

}
