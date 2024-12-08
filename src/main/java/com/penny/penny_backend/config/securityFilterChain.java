package com.penny.penny_backend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

public class securityFilterChain {
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable()) // CSRF 비활성화
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/**").permitAll() // 인증 없이 접근 가능
                        .anyRequest().authenticated()             // 나머지 요청은 인증 필요
                )
                .httpBasic(withDefaults()); // 기본 HTTP Basic 인증 사용 (필요 시)

        return http.build();
    }
}

