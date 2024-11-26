//package com.penny.penny_backend.config;
//
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Component;
//import io.jsonwebtoken.*;
//
//
//import java.util.Date;
//
//@RequiredArgsConstructor
//@Component
//public class TokenProvider {
//
//    private final JwtProperties jwtProperties;
//    private final String SECRET_KEY = "mySecretKey";
//    private final long TOKEN_VALIDITY = 1000*60*60;
//
//    public String generateToken(String username, String role) {
//        return Jwts.builder()
//                .setSubject(username)
//                .claim("role", role)
//                .setIssuedAt(new Date())
//                .setExpiration(new Date(System.currentTimeMillis() + TOKEN_VALIDITY))
//                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
//                .compact();
//    }
//
//    public boolean validateToken(String token) {
//        try {
//            Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token);
//            return true;
//        } catch (ExpiredJwtException e) {
//            System.out.println("Token expired");
//        } catch (UnsupportedJwtException e) {
//            System.out.println("Unsupported JWT");
//        } catch (MalformedJwtException e) {
//            System.out.println("Malformed JWT");
//        } catch (SignatureException e) {
//            System.out.println("Invalid signature");
//        } catch (IllegalArgumentException e) {
//            System.out.println("Token is null or empty");
//        }
//        return false;
//    }
//
//    // **JWT에서 사용자명 추출**
//    public String getUsernameFromToken(String token) {
//        Claims claims = Jwts.parser()
//                .setSigningKey(SECRET_KEY)
//                .parseClaimsJws(token)
//                .getBody();
//        return claims.getSubject();
//    }
//
//    // **JWT에서 역할 추출**
//    public String getRoleFromToken(String token) {
//        Claims claims = Jwts.parser()
//                .setSigningKey(SECRET_KEY)
//                .parseClaimsJws(token)
//                .getBody();
//        return claims.get("role", String.class);
//    }
//
//}
