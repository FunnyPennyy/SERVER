package com.penny.penny_backend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableMethodSecurity(prePostEnabled = true) // @PreAuthorize 등을 사용하기 위해 활성화
public class SecurityConfig {

    /*@Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeRequests(authorize -> authorize
                        .antMatchers("/public/**").permitAll() // 특정 경로를 인증 없이 허용
                        .anyRequest().authenticated()          // 나머지 요청은 인증 필요
                )
                .formLogin().permitAll()                    // 기본 로그인 폼 허용
                .and()
                .logout().permitAll();                      // 로그아웃 허용

        return http.build();
    }*/
}
