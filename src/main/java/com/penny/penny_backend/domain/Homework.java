package com.penny.penny_backend.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Setter
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
@Entity
public class Homework {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;  // 숙제 ID

    @CreationTimestamp
    private LocalDateTime createdAt; // 숙제 생성일자

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "classroom_id", nullable = false)
    @JsonIgnore // JSON 직렬화에서 제외
    private Classroom classroom; // 학급

    private String content;  // 숙제 내용

}