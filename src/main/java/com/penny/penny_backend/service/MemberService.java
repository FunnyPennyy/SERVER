package com.penny.penny_backend.service;



import com.penny.penny_backend.dto.*;
import com.penny.penny_backend.jwt.*;
import com.penny.penny_backend.repository.*;
import com.penny.penny_backend.domain.*;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly=false)
@Slf4j
public class MemberService{

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final JwtTokenProvider jwtTokenProvider;

    @Transactional
    public Member signUp(String username, String password, Member.Role role){
        if (memberRepository.findByUsername(username).isPresent()){
            throw new IllegalStateException("Username already exists.");
        }

        Member member = Member.builder()
                .username(username)
                .password(passwordEncoder.encode(password))
                .role(role)
                .build();
        return memberRepository.save(member);
    }

    public JwtToken signIn(String username, String password){
        try{
            UsernamePasswordAuthenticationToken authenticationToken
                    = new UsernamePasswordAuthenticationToken(username, password);

            Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

            JwtToken jwtToken = jwtTokenProvider.generateToken(authentication);

            return jwtToken;

        }catch (Exception e){
            System.err.println(e);
        }
        return null;
    }

    public void logout(String accessToken){
        log.info("Token invalidated for: {}", accessToken);
    }



}
