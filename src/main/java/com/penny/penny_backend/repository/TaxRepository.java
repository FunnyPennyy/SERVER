package com.penny.penny_backend.repository;

import com.penny.penny_backend.domain.Tax;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TaxRepository extends JpaRepository<Tax, Long> {
    Optional<Tax> findByClassroom_ClassroomId(Long classroomId);
}
