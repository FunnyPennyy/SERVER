package com.penny.penny_backend.domain;

import jakarta.persistence.*;
import lombok.*;

import com.penny.penny_backend.domain.Classroom;


@Entity
@Table(name = "student")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Student {
    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    private Long studentId;
    private String studentName;
    private Integer credit;

    @ManyToOne(fetch = FetchType.LAZY)
    private Classroom classroom;


}
