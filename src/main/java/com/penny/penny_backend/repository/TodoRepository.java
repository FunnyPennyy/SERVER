package com.penny.penny_backend.repository;
import com.penny.penny_backend.domain.Todo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface TodoRepository extends JpaRepository<Todo, Long> {
    Optional<Todo> findByStudentIdAndDate(Long studentId, LocalDate date);
    boolean existsByStudentIdAndDate(Long studentId, LocalDate date);
    List<Todo> findDistinctByDateBetweenAndStudentId(LocalDate startDate, LocalDate endDate, Long studentId);
}