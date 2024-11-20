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

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

    @Override
    public List<String> createDefaultTodoList(Job job, Student student) {
//        Optional<Job> jobOptional = jobRepository.findById(job.getJobId());
//        Optional<Student> student = studentRepository.fin
//        if (jobOptional.isPresent()) {
        List<String> defaultTodos = new ArrayList<>(job.getTodoList());
        Todo todo = new Todo(student.getId());
        todoRepository.save(todo);

        for (String todoContent : defaultTodos) {
            TodoContent content = new TodoContent(todoContent, false, todo);
            todoContentRepository.save(content);
//            todo.getTodoContents().add(content);
        }
        todoRepository.save(todo);
        return defaultTodos;
//        }
//        return new ArrayList<>();
    }

    @Override
    public void updateTodo(Todo todo, int index, String newTodo) {
        List<TodoContent> todoContents = todo.getTodoContents();
        if (index >= 0 && index < todoContents.size()) {
            TodoContent content = todoContents.get(index);
            content.setContent(newTodo);
            todoContentRepository.save(content);
        }
    }

    @Override
    public void deleteTodo(Todo todo, int index) {
        List<TodoContent> todoContents = todo.getTodoContents();
        if (index >= 0 && index < todoContents.size()) {
            TodoContent content = todoContents.get(index);
            todoContents.remove(index);
            todoContentRepository.delete(content);
        }
    }

    @Override
    public void addTodo(Todo todo, String newTodo) {
        TodoContent content = new TodoContent(newTodo, false, todo);
        todo.getTodoContents().add(content);
        todoContentRepository.save(content);
    }
}