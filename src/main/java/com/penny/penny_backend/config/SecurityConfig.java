package com.penny.penny_backend.config;

import com.penny.penny_backend.jwt.JwtAuthenticationFilter;
import com.penny.penny_backend.jwt.JwtTokenProvider;
import com.penny.penny_backend.service.CustomUserDetailsService;
import io.jsonwebtoken.Jwt;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;



import java.security.cert.CertPathBuilder;
import java.util.Collections;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final JwtTokenProvider jwtTokenProvider;

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http.cors(corsCustomizer -> corsCustomizer.configurationSource(request -> {
            CorsConfiguration config = new CorsConfiguration();
            config.setAllowedOrigins(Collections.singletonList("http://localhost:3000"));
            config.setAllowedMethods(Collections.singletonList("*"));
            config.setAllowedHeaders(Collections.singletonList("*"));
            config.setAllowCredentials(true);
            config.setMaxAge(3600L); // 1시간
            return config;
        }));

        http.csrf(csrf -> csrf.disable())
                .authorizeHttpRequests((requests) -> requests
                        // members/로 시작하는 url 모두 허용
                        .requestMatchers("/members/**","/schools/**","/students/sign-up", "/teachers/sign-up").permitAll()
                        .requestMatchers("/members/test").hasRole("USER")
                        .requestMatchers("/members/test2").hasRole("ADMIN")
                        .anyRequest().authenticated() //그 외는 권한 필요

                )
                .formLogin((form)-> form.disable())
                .logout((logout)-> logout.permitAll());

        http.addFilterBefore(new JwtAuthenticationFilter(jwtTokenProvider),UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

}


