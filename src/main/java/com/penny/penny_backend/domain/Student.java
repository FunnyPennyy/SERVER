package com.penny.penny_backend.domain;

import jakarta.persistence.*;
import lombok.*;

import com.penny.penny_backend.domain.Classroom;




@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Student extends Member{

    private Integer credit;

    @ManyToOne(fetch = FetchType.LAZY)
    private Classroom classroom;

    public Student(String username, String password, Integer credit, Classroom classroom) {
        super.setUsername(username);
        super.setPassword(password);
        super.setRole(Role.USER); // Role 설정
        this.credit = credit;
        this.classroom = classroom;
    }


}
