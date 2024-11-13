package com.penny.penny_backend.domain;

import jakarta.persistence.*;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@NoArgsConstructor
@ToString
public class HomeworkStatus {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long statusId;

    private boolean status;
    private Long studentId;

    @ManyToOne
    @JoinColumn(name = "hw_id", nullable = false)
    private Homework homework;

    public HomeworkStatus(Long statusId, boolean status, Long studentId, Homework homework) {
        this.statusId = statusId;
        this.status = status;
        this.studentId = studentId;
        setHomework(homework);
    }

    public void setHomework(Homework homework) {
        this.homework = homework;
        if (homework != null && !homework.getHomeworkStatuses().contains(this)) {
            homework.getHomeworkStatuses().add(this);
        }
    }
}
