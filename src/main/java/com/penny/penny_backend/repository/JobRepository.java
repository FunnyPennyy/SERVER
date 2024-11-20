package com.penny.penny_backend.repository;

import com.penny.penny_backend.domain.Job;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JobRepository extends JpaRepository<Job, Long> {
}
