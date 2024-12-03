package com.penny.penny_backend.controller;

import com.penny.penny_backend.jwt.*;
import com.penny.penny_backend.service.MemberService;
import com.penny.penny_backend.dto.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/members")
public class MemberController {

    private final MemberService memberService;
    //private final JwtTokenProvider jwtTokenProvider;

    @PostMapping("/sign-up")
    public ResponseEntity<String> signUp(@RequestBody SignUpRequest signUpRequest){
        memberService.signUp(signUpRequest.getUsername(),
                signUpRequest.getPassword(),
                signUpRequest.getRole());
        return ResponseEntity.ok("User registered successfully.");
    }

    @PostMapping("/sign-in")
    public ResponseEntity<JwtToken> signIn(@RequestBody SignInRequest signInRequest){
        JwtToken token= memberService.signIn(signInRequest.getUsername(), signInRequest.getPassword());
        return ResponseEntity.ok(token);
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(@RequestHeader("Authorization") String accessToken) {
        memberService.logout(accessToken.substring(7));
        return ResponseEntity.ok("Logged out successfully.");
    }


/*    @PostMapping("/sign-in")
    public JwtToken signIn(@RequestBody SignInRequest signInRequest) {

        String username = signInRequest.getUsername();
        String password = signInRequest.getPassword();
        JwtToken jwtToken = memberService.signIn(username, password);
        log.info("request username = {}, password = {}", username, password);
        log.info("jwtToken accessToken = {}, refreshToken = {}", jwtToken.getAccessToken(), jwtToken.getRefreshToken());
        return jwtToken;
    }*/

    @PostMapping("/test")
    public String test() {
        return "success";
    }

    @GetMapping("/test2")
    public String test2() {
        return "success2";
    }

}