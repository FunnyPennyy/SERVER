package com.penny.penny_backend.repository;

import com.penny.penny_backend.domain.Job;
import com.penny.penny_backend.domain.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Student, Long> {
}
