package com.penny.penny_backend.repository;
import com.penny.penny_backend.domain.TodoContent;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TodoContentRepository extends JpaRepository<TodoContent, Long>{
}
