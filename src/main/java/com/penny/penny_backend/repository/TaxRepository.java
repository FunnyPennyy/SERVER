package com.penny.penny_backend.repository;

import com.penny.penny_backend.domain.Tax;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaxRepository extends JpaRepository<Tax, Long> {
    // Tax 의 첫 번째 레코드를 조회 (단일 Tax 조회에 사용)
    Tax findFirstByOrderById();
}
