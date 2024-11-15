package com.penny.penny_backend.domain;

import jakarta.persistence.*;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor
@ToString
public class TodoContent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long contentId;

    private String content;
    private boolean check;

    @ManyToOne
    @JoinColumn(name = "todo_id", nullable = false)
    private Todo todo;

    public TodoContent(Long contentId, String content, boolean check, Todo todo) {
        this.contentId = contentId;
        this.content = content;
        this.check = check;
        setTodo(todo);
    }

    public void setTodo(Todo todo) {
        this.todo = todo;
        if (todo != null && !todo.getTodoContents().contains(this)) {
            todo.getTodoContents().add(this);
        }
    }
}
