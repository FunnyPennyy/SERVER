package com.penny.penny_backend.service;

import com.penny.penny_backend.domain.TodoContent;

import java.time.LocalDate;
import java.util.List;

public interface TodoService {

    // 디폴트 투두 리스트 생성 (Job 조회해서 job의 디폴트 투두로 투두 리스트 생성)
    List<TodoContent> createDefaultTodoList(Long studentId);

    // 개별 투두 수정 - 내용
    void updateTodoContent(Long contentId, String newContent);

    // 개별 투두 수정 - 상태
    void updateTodoStatus(Long contentId, Boolean newStatus);

    // 개별 투두 삭제
    void deleteTodo(Long contentId);

    // 개별 투두 추가
    void addTodo(Long todoId, String newTodo, boolean check);
//
    // 날짜로 조회
    List<TodoContent> getTodoContentsByStudentIdAndDate(Long studentId, LocalDate date);
}