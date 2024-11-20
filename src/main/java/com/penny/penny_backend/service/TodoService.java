package com.penny.penny_backend.service;

import com.penny.penny_backend.domain.Job;
import com.penny.penny_backend.domain.Student;
import com.penny.penny_backend.domain.Todo;

import java.util.List;

public interface TodoService {

    // 디폴트 투두 리스트 생성 (Job 조회해서 job의 디폴트 투두로 투두 리스트 생성)
    List<String> createDefaultTodoList(Job job, Student student);

    // 개별 투두 수정
    void updateTodo(Todo todo, int index, String newTodo);

    // 개별 투두 삭제
    void deleteTodo(Todo todo, int index);

    // 개별 투두 추가
    void addTodo(Todo todo, String newTodo);
}