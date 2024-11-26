package com.penny.penny_backend.domain;


import jakarta.persistence.*;
import lombok.*;

@Table(name="users")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter
@Entity
public class User{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id", updatable = false)
    private Long Id;

    // 로그인 아이디
    @Column(name="username", nullable = false, unique = true)
    private String username;

    @Column(name="password", nullable = false)
    private String password;

    @Column(name = "role", nullable = false)
    private String role; //TEACHER (admin) or Student(User)


    @Builder
    public User(String username, String password, String auth){
        this.username=username;
        this.password=password;
    }
}
