package com.penny.penny_backend.service;

import com.penny.penny_backend.config.JwtUtil;
import com.penny.penny_backend.domain.User;
import com.penny.penny_backend.dto.LoginRequest;
import com.penny.penny_backend.dto.LoginResponse;
import com.penny.penny_backend.dto.RegisterRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public User register(RegisterRequest request) {
        User user = User.builder()
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(request.getRole())
                .build();
        return userService.save(user);
    }

    public LoginResponse login(LoginRequest request) {
        User user = userService.findByUsername(request.getUsername());
        if (user != null && passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            String token = jwtUtil.generateToken(user.getUsername(), user.getRole());
            return new LoginResponse(token);
        }
        throw new IllegalArgumentException("Invalid credentials");
    }
}
