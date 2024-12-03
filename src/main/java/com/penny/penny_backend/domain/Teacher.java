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
public class Teacher extends Member{
    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    private Long teacherId;

    @Column(name = "teacher_name", nullable = false)
    private String teacherName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "classroom_id", foreignKey = @ForeignKey(name = "fk_classroom"))
    private Classroom classroom;

    public Teacher(String username, String password, String teacherName, Classroom classroom) {
        super.setUsername(username);
        super.setPassword(password);
        super.setRole(Role.ADMIN); // Role 설정
        this.teacherName = teacherName;
        this.classroom = classroom;
    }

}
