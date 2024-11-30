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
public class Homework {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long hwId;
//
    private LocalDate date;
    private String title;

    @OneToMany(mappedBy = "homework", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<HomeworkStatus> homeworkStatuses = new ArrayList<>();

    public Homework(Long hwId, LocalDate date, String title) {
        this.hwId = hwId;
        this.date = LocalDate.now();
        this.title = title;
    }
}
