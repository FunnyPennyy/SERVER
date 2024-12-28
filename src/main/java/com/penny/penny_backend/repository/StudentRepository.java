package com.penny.penny_backend.repository;

import com.penny.penny_backend.domain.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
    Optional<Student> findByUsername(String username);

    //List<Student> findByRole(String role);
    List<Student> findByClassroom_ClassroomId(Long classroomId); // 특정 Classroom의 모든 학생 조회}
}