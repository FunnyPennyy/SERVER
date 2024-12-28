package com.penny.penny_backend.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Getter
@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class HomeworkStatus {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;  // 숙제상태 ID

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    @JsonIgnore // JSON 직렬화에서 제외
    private Student student; // 학생

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "homework_id", nullable = false)
    @JsonIgnore // JSON 직렬화에서 제외
    private Homework homework; // 숙제

    // Setter for completionStatus
    @Setter
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Status completionStatus = Status.PENDING; // 기본 완료 여부 안함(=대기중)으로

    // Enum 정의
    public enum Status {
        COMPLETION, // 완료됨
        PENDING     // 대기 중
    }

}