package com.penny.penny_backend.controller;

import com.penny.penny_backend.domain.*;
import com.penny.penny_backend.service.TodoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/todo")
public class TodoController {

    private final TodoService todoService;

    @Autowired
    public TodoController(TodoService todoService) { this.todoService = todoService; }

    @PostMapping("/{studentId}")
    public ResponseEntity<List<TodoContent>> createDefaultTodoList(@PathVariable Long studentId) {
        try {
            System.out.println("studentId = " + studentId);
            List<TodoContent> todo = todoService.createDefaultTodoList(studentId);
            System.out.println("todo_created\n");
            return new ResponseEntity<>(todo, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/{studentId}/contents")
    public ResponseEntity<List<TodoContent>> getTodoContentsByStudentIdAndDate(@PathVariable Long studentId) {
        try {
            LocalDate currentDate = LocalDate.now();
            List<TodoContent> todoContents = todoService.getTodoContentsByStudentIdAndDate(studentId, currentDate);
            return new ResponseEntity<>(todoContents, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    // 1. TodoContent 내용 업데이트
    @PatchMapping("/{contentId}/content")
    public ResponseEntity<String> updateTodoContent(@PathVariable Long contentId,
                                                    @RequestBody String newContent) {
        try {
            todoService.updateTodoContent(contentId, newContent);
            return new ResponseEntity<>("TodoContent 내용이 성공적으로 업데이트되었습니다.", HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    // 2. TodoContent 상태 업데이트
    @PatchMapping("/{contentId}/status")
    public ResponseEntity<String> updateTodoStatus(@PathVariable Long contentId,
                                                   @RequestBody Boolean newStatus) {
        try {
            todoService.updateTodoStatus(contentId, newStatus);
            return new ResponseEntity<>("TodoContent 상태가 성공적으로 업데이트되었습니다.", HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    // 3. TodoContent 삭제
    @DeleteMapping("/{contentId}")
    public ResponseEntity<String> deleteTodoContent(@PathVariable Long contentId) {
        try {
            todoService.deleteTodo(contentId);
            return new ResponseEntity<>("TodoContent가 성공적으로 삭제되었습니다.", HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    // 4. TodoContent 추가
    @PostMapping("/{todoId}/add")
    public ResponseEntity<String> addTodoContent(@PathVariable Long todoId,
                                                 @RequestBody String newTodo,
                                                 @RequestBody boolean check) {
        try {
            todoService.addTodo(todoId, newTodo, check);
            return new ResponseEntity<>("새 TodoContent가 성공적으로 추가되었습니다.", HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
