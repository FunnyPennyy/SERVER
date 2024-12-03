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
public class Student extends Member{
    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    private Long studentId;
    private String studentName;
    private Integer credit;

    @ManyToOne(fetch = FetchType.LAZY)
    private Classroom classroom;

    public Student(String username, String password, String studentName, Integer credit, Classroom classroom) {
        super.setUsername(username);
        super.setPassword(password);
        super.setRole(Role.USER); // Role 설정
        this.studentName = studentName;
        this.credit = credit;
        this.classroom = classroom;
    }


}
