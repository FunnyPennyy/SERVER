package com.penny.penny_backend.controller;

import com.penny.penny_backend.dto.AuthResponse;
import com.penny.penny_backend.dto.SignupRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

//@RestController
//@RequestMapping("/auth")
//@RequiredArgsConstructor
//public class AuthController {
//    private final UserService
//    @PostMapping("/signup")
//    public ResponseEntity<AuthResponse> signup(@RequestBody SignupRequest dto) {
//        return ResponseEntity.ok(userService.signup(dto));
//    }
//
//    @PostMapping("/login")
//    public ResponseEntity<AuthResponse> login(@RequestParam String username, @RequestParam String password) {
//        return ResponseEntity.ok(userService.login(username, password));
//    }
//}
