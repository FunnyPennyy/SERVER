package com.penny.penny_backend.service;

import com.penny.penny_backend.domain.Job;
import com.penny.penny_backend.domain.Student;
import com.penny.penny_backend.domain.Todo;
import com.penny.penny_backend.domain.TodoContent;
import com.penny.penny_backend.repository.JobRepository;
import com.penny.penny_backend.repository.StudentRepository;
import com.penny.penny_backend.repository.TodoContentRepository;
import com.penny.penny_backend.repository.TodoRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class TodoServiceImpl implements TodoService {

    private final JobRepository jobRepository;
    private final TodoRepository todoRepository;
    private final TodoContentRepository todoContentRepository;
    private final StudentRepository studentRepository;

    public TodoServiceImpl(JobRepository jobRepository, TodoRepository todoRepository,
                           TodoContentRepository todoContentRepository, StudentRepository studentRepository) {
        this.jobRepository = jobRepository;
        this.todoRepository = todoRepository;
        this.todoContentRepository = todoContentRepository;
        this.studentRepository = studentRepository;
    }
//
    @Override
    public List<TodoContent> createDefaultTodoList(Long studentId) {
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new IllegalArgumentException("해당 Student ID를 가진 학생이 존재하지 않습니다."));
        Job job = jobRepository.findById(student.getJobId())
                .orElseThrow(() -> new IllegalArgumentException("해당 Job ID를 가진 Job이 존재하지 않습니다."));

        Todo todo = new Todo(student.getStudentId());
        todoRepository.save(todo);

        // 기본 todo 리스트로 TodoContent 생성 및 저장
        for (String todoContent : job.getTodoList()) {
            TodoContent content = new TodoContent(todoContent, false, todo); // 생성
            todoContentRepository.save(content); // 저장
        }

        return todo.getTodoContents();
    }

    @Override
    public void updateTodoContent(Long contentId, String newContent) {
        if (newContent == null || newContent.isEmpty()) {
            throw new IllegalArgumentException("새로운 내용은 비어있을 수 없습니다.");
        }

        TodoContent todoContent = todoContentRepository.findById(contentId)
                .orElseThrow(() -> new IllegalArgumentException("해당 ID의 TodoContent를 찾을 수 없습니다."));

        todoContent.setContent(newContent);
        todoContentRepository.save(todoContent);
    }

    @Override
    public void updateTodoStatus(Long contentId, Boolean newStatus) {
        TodoContent todoContent = todoContentRepository.findById(contentId)
                .orElseThrow(() -> new IllegalArgumentException("해당 ID의 TodoContent를 찾을 수 없습니다."));

        todoContent.setCheck(newStatus);
        todoContentRepository.save(todoContent);
    }

    @Override
    public void deleteTodo(Long contentId) {
        TodoContent todoContent = todoContentRepository.findById(contentId)
                .orElseThrow(() -> new IllegalArgumentException("해당 ID의 TodoContent를 찾을 수 없습니다."));

        todoContent.getTodo().getTodoContents().remove(todoContent);
    }

    @Override
    public void addTodo(Long todoId, String newContent, boolean check) {
        Todo todo = todoRepository.findById(todoId)
                .orElseThrow(() -> new IllegalArgumentException("todo가 존재하지 않습니다."));

        if (newContent == null || newContent.isEmpty()) {
            throw new IllegalArgumentException("새로운 내용은 비어있을 수 없습니다.");
        }

        TodoContent content = new TodoContent(newContent, check, todo);
        todoContentRepository.save(content);
    }

    @Override
    public List<TodoContent> getTodoContentsByStudentIdAndDate(Long studentId, LocalDate date) {
        // 특정 학생 ID와 날짜로 Todo 조회
        Todo todo = todoRepository.findByStudentIdAndDate(studentId, date)
                .orElseThrow(() -> new IllegalArgumentException("해당 학생과 날짜에 대한 Todo가 없습니다."));

        // Todo와 연결된 TodoContents 반환
        return todo.getTodoContents();
    }
}