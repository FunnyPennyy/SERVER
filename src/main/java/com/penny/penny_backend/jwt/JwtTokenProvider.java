package com.penny.penny_backend.jwt;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Component
public class JwtTokenProvider {

    private final Key key;

    public JwtTokenProvider(@Value("${jwt.secret}")String secretKey){
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        this.key = Keys.hmacShaKeyFor(keyBytes);
    }

    public JwtToken generateToken(Authentication authentication){
        String authorities = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));

        long now = (new Date()).getTime();
        Date issuedAt = new Date();

        Date accessTokenExpiresln = new Date(now+86400000);

        //header 설정 추가
        Map<String, Object> headers = new HashMap<>();
        headers.put("alg", "HS256");
        headers.put("typ", "JWT");

        String accessToken = Jwts.builder()
                .setHeader(createHeaders())
                .setSubject(authentication.getName())
                .claim("iss", "off")
                .claim("aud", authentication.getName())
                .claim("auth", authorities)
                .setExpiration(accessTokenExpiresln)
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();

        String refreshToken =Jwts.builder()
                .setHeader(createHeaders())
                .setSubject("refreshToken")
                .claim("iss", "off")
                .claim("aud", authentication.getName())
                .claim("auth", authorities)
                .setExpiration(new Date(now+86400000))
                .setIssuedAt(issuedAt)
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();

        return JwtToken.builder()
                .grantType("Bearer")
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();

    }

    public Authentication getAuthentication(String accessToken){

        Claims claims = parseClaims(accessToken);

        if (claims.get("auth") == null){
            log.error("권한 정보가 없는 토큰입니다. ");
            throw  new RuntimeException("권한 정보가 없는 토큰입니다.");
        }

        Collection<? extends  GrantedAuthority> authorities =
                Arrays.stream(claims.get("auth").toString().split(","))
                        .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toList());

        //UserDetails principal = new User((String) claims.get("aud"), "", authorities);
//        return new UsernamePasswordAuthenticationToken(principal, "", authorities);
//
//        UserDetails principal = org.springframework.security.core.userdetails.User
//                .builder()
//                .username(claims.getSubject())
//                .password("") // 비밀번호는 빈 값으로 설정 (JWT에서 제공되지 않음)
//                .authorities(authorities)
//                .build();

        return new UsernamePasswordAuthenticationToken(claims.getSubject(), "", authorities);
    }

    // 토큰 정보를 검증하는 메서드
    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (SecurityException | MalformedJwtException e) {
            log.info("Invalid JWT Token", e);
        } catch (ExpiredJwtException e) {
            log.info("Expired JWT Token", e);
        } catch (UnsupportedJwtException e) {
            log.info("Unsupported JWT Token", e);
        } catch (IllegalArgumentException e) {
            log.info("JWT claims string is empty.", e);
        }
        return false;
    }



    private Claims parseClaims(String accessToken) {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(accessToken)
                    .getBody();
        } catch (ExpiredJwtException e) {
            return e.getClaims();
        }
    }

    private static Map<String, Object> createHeaders() {
        Map<String, Object> headers = new HashMap<>();
        headers.put("alg", "HS256"); // 알고리즘 정보 설정
        headers.put("typ", "JWT"); // 토큰 타입 정보 설정
        return headers; // 생성된 Header 정보 반환
    }




}