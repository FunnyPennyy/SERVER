package com.penny.penny_backend.domain;

import jakarta.persistence.*;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@ToString
public class Todo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long todoId;

    private LocalDate date;
    private Long studentId;

    @OneToMany(mappedBy = "todo", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TodoContent> todoContents = new ArrayList<>();

    public Todo(Long todoId, LocalDate date, Long studentId) {
        this.todoId = todoId;
        this.date = LocalDate.now();
        this.studentId = studentId;
    }
}
