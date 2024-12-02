package com.penny.penny_backend.jwt;

import com.penny.penny_backend.service.CustomUserDetailsService;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
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

    //public JwtToken generateToken(Authentication authentication){
      public String generateToken(String username, String role ){
/*        String authorities = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));

        String role = authentication.getAuthorities().stream()
                .findFirst()
                .map(GrantedAuthority::getAuthority)
                .orElse("USER");*/

        Claims claims = Jwts.claims().setSubject(username);
        claims.put("role", role);

        long now = (new Date()).getTime();
        Date issuedAt = new Date();
        Date accessTokenExpiresln = new Date(issuedAt.getTime()+86400000);

        //header 설정 추가
/*      Map<String, Object> headers = new HashMap<>();
        headers.put("alg", "HS256");
        headers.put("typ", "JWT");

        String accessToken = Jwts.builder()
                .setHeader(createHeaders())
                .setSubject(authentication.getName())
                .claim("role", role)
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
                .build();*/
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(issuedAt)
                .setExpiration(accessTokenExpiresln)
                .signWith(key, SignatureAlgorithm.ES256)
                .compact();

    }

/*    public Authentication getAuthentication(String accessToken){

        Claims claims = parseClaims(accessToken);

        if (claims.get("username") == null){
            log.error("권한 정보가 없는 토큰입니다. ");
            throw  new RuntimeException("권한 정보가 없는 토큰입니다.");
        }

        Collection<? extends  GrantedAuthority> authorities =
                Arrays.stream(claims.get("username").toString().split(","))
                        .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toList());

        return new UsernamePasswordAuthenticationToken(claims.getSubject(), "", authorities);


    }*/

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


    //getUsername - token에서 username 추출
/*    public Claim parseClaims(String accessToken) {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(accessToken)
                    .getBody() ;
        } catch (ExpiredJwtException e) {
            return e.getClaims();
        }
    }*/

    public String getUsername(String accessToken) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(accessToken)
                .getBody()
                .getSubject();

    }




}

