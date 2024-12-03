package com.penny.penny_backend.domain;

import jakarta.persistence.*;
import lombok.Data;

import com.penny.penny_backend.domain.Classroom;
import lombok.*;



@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Teacher extends Member{

    @ManyToOne(fetch = FetchType.LAZY)
    private Classroom classroom;

    public Teacher(String username, String password, Classroom classroom) {
        super.setUsername(username);
        super.setPassword(password);
        super.setRole(Role.ADMIN); // Role 설정
        this.classroom = classroom;
    }

}
