package com.penny.penny_backend.repository;

import com.penny.penny_backend.domain.Deposit;
import com.penny.penny_backend.domain.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface DepositRepository extends JpaRepository<Deposit, Long> {
    List<Deposit> findByOwner(Student owner); // 특정 Owner의 예금 조회
}
