package com.penny.penny_backend.domain;


import jakarta.persistence.*;
import lombok.*;
import java.util.*;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;


@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
@Setter
@Entity
@Builder
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id", updatable = false)
    private Long Id;
    // 로그인 아이디
    @Column(name="username", nullable = false, unique = true)
    private String username;
    @Column(name="password", nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;

    public enum Role{
        ADMIN, USER
    }


    public List<GrantedAuthority> getAuthorities(){
        return Collections.singletonList(new SimpleGrantedAuthority("ROLE_"+this.role.name()));
    }



}


