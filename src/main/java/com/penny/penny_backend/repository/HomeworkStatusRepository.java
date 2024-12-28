package com.penny.penny_backend.repository;

import com.penny.penny_backend.domain.HomeworkStatus;
import com.penny.penny_backend.domain.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

import java.util.Optional;

@Repository
public interface HomeworkStatusRepository extends JpaRepository<HomeworkStatus, Long> {
    Optional<HomeworkStatus> findByStudentIdAndHomeworkId(Long studentId, Long homeworkId);

}
