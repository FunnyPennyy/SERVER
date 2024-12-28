package com.penny.penny_backend.repository;

import com.penny.penny_backend.domain.Homework;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface HomeworkRepository extends JpaRepository<Homework, Long> {
}
