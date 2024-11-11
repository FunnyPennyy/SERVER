package com.penny.penny_backend.domain;

import jakarta.persistence.*;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Job {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int jobId;

    private String jobName;
    private String jobDescription;
    private int salary;

    @ElementCollection
    @CollectionTable(name = "job_todos", joinColumns = @JoinColumn(name = "job_id"))
    private List<String> todoList = new ArrayList<>();
}
