package com.penny.penny_backend.repository;

import com.penny.penny_backend.domain.DepositType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface DepositTypeRepository extends JpaRepository<DepositType, Long> {

}
