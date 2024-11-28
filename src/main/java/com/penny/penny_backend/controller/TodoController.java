package com.penny.penny_backend.controller;

import com.penny.penny_backend.domain.*;
import com.penny.penny_backend.dto.ApiResponseDTO;
import com.penny.penny_backend.dto.TodoContentDTO;
import com.penny.penny_backend.dto.TodoRequestDTO;
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
    public ResponseEntity<ApiResponseDTO<List<TodoContentDTO>>> createDefaultTodoList(@PathVariable Long studentId) {
        try {
            System.out.println("studentId = " + studentId);
            List<TodoContent> todo = todoService.createDefaultTodoList(studentId);
            System.out.println("todo_created\n");
            // TodoContent -> TodoContentDTO 변환
            List<TodoContentDTO> todoContentDTOList = todo.stream()
                    .map(todoContent -> new TodoContentDTO(
                            todoContent.getContentId(),
                            todoContent.getContent(),
                            false
                    ))
                    .toList();

            ApiResponseDTO<List<TodoContentDTO>> response = new ApiResponseDTO<>(
                    "success",
                    "Default todo list created successfully",
                    todoContentDTOList
            );
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            ApiResponseDTO<List<TodoContentDTO>> response = new ApiResponseDTO<>(
                    "error",
                    e.getMessage(),
                    null
            );
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/{studentId}/contents")
    public ResponseEntity<ApiResponseDTO<List<TodoContentDTO>>> getTodoContentsByStudentIdAndDate(@PathVariable Long studentId) {
        try {
            LocalDate currentDate = LocalDate.now();
            List<TodoContent> todoContents = todoService.getTodoContentsByStudentIdAndDate(studentId, currentDate);

            // TodoContent 엔티티 리스트를 DTO 리스트로 변환
            List<TodoContentDTO> todoContentDTOList = todoContents.stream()
                    .map(todoContent -> new TodoContentDTO(
                            todoContent.getContentId(),
                            todoContent.getContent(),
                            todoContent.isCheck()
                    )).toList();

            ApiResponseDTO<List<TodoContentDTO>> response = new ApiResponseDTO<>(
                    "success",
                    "Todo contents retrieved successfully",
                    todoContentDTOList
            );
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            ApiResponseDTO<List<TodoContentDTO>> response = new ApiResponseDTO<>(
                    "error",
                    e.getMessage(),
                    null
            );
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    // 1. TodoContent 내용 업데이트
    @PatchMapping("/{contentId}/content")
    public ResponseEntity<ApiResponseDTO<String>> updateTodoContent(@PathVariable Long contentId,
                                                    @RequestBody String newContent) {
        try {
            todoService.updateTodoContent(contentId, newContent);
            ApiResponseDTO<String> response = new ApiResponseDTO<>(
                    "success",
                    "TodoContent updated successfully",
                    null
            );
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            ApiResponseDTO<String> response = new ApiResponseDTO<>(
                    "error",
                    e.getMessage(),
                    null
            );
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    // 2. TodoContent 상태 업데이트
    @PatchMapping("/{contentId}/status")
    public ResponseEntity<ApiResponseDTO<String>> updateTodoStatus(@PathVariable Long contentId,
                                                   @RequestBody Boolean newStatus) {
        try {
            todoService.updateTodoStatus(contentId, newStatus);
            ApiResponseDTO<String> response = new ApiResponseDTO<>(
                    "success",
                    "TodoContent updated successfully",
                    null
            );
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            ApiResponseDTO<String> response = new ApiResponseDTO<>(
                    "error",
                    e.getMessage(),
                    null
            );
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    // 3. TodoContent 삭제
    @DeleteMapping("/{contentId}")
    public ResponseEntity<ApiResponseDTO<String>> deleteTodoContent(@PathVariable Long contentId) {
        try {
            todoService.deleteTodo(contentId);
            ApiResponseDTO<String> response = new ApiResponseDTO<>(
                    "success",
                    "TodoContent deleted successfully",
                    null
            );
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            ApiResponseDTO<String> response = new ApiResponseDTO<>(
                    "error",
                    e.getMessage(),
                    null
            );
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    // 4. TodoContent 추가
    @PostMapping("/{todoId}/add")
    public ResponseEntity<ApiResponseDTO<String>> addTodoContent(@PathVariable Long todoId,
                                                 @RequestBody TodoRequestDTO requestDTO) {
        try {
            todoService.addTodo(todoId, requestDTO.getContent(), requestDTO.getCheck());
            ApiResponseDTO<String> response = new ApiResponseDTO<>(
                    "success",
                    "New TodoContent added successfully",
                    null
            );
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            ApiResponseDTO<String> response = new ApiResponseDTO<>(
                    "error",
                    e.getMessage(),
                    null
            );
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }
}
