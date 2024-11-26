package com.penny.penny_backend.domain;

import jakarta.persistence.*;
import lombok.Data;

import com.penny.penny_backend.domain.Classroom;
import lombok.*;


@Entity
@Table(name = "teacher")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Teacher {
    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    private Long teacherId;

    @Column(name = "teacher_name", nullable = false)
    private String teacherName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "classroom_id", foreignKey = @ForeignKey(name = "fk_classroom"))
    private Classroom classroom;

}
