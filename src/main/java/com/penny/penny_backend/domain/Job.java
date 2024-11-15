package com.penny.penny_backend.domain;

import jakarta.persistence.*;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class Job {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long jobId;

    private String name;
    private String jobDescription;
    private int salary;

    @ElementCollection
    @CollectionTable(name = "job_todos", joinColumns = @JoinColumn(name = "job_id"))
    @Column(name = "todo_list")
    private List<String> todoList = new ArrayList<>();
}