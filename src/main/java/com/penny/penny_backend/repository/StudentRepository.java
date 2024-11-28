package com.penny.penny_backend.repository;

import com.penny.penny_backend.domain.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StudentRepository extends JpaRepository<Student, Long> {
    List<Student> findByClassroom_ClassroomId(Long classroomId); // Classroom 엔티티의 PK를 사용하여 조회
}
