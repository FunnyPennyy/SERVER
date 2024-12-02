package com.penny.penny_backend.service;

import com.penny.penny_backend.domain.Member;
import com.penny.penny_backend.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    PasswordEncoder passwordEncoder;


    public UserDetails loadUserByUsername(String username) throws
            UsernameNotFoundException{
        return (UserDetails) memberRepository.findByUsername(username)
//                .map(this::createUserDetails)
                .orElseThrow(()->new UsernameNotFoundException("해당하는 회원을 찾을 수 없습니다." + username));
    }

//    public  UserDetails createUserDetails(Member member){
//        return Member.builder()
//                .username(member.getUsername())
//                .password((passwordEncoder.encode(member.getPassword())))
//                .build();
//    }
}
