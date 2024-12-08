package com.penny.penny_backend.repository;

import com.penny.penny_backend.domain.TaxUsage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaxUsageRepository extends JpaRepository<TaxUsage, Long> {
}
