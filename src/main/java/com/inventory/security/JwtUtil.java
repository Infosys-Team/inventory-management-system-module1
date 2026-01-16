// package com.inventory.security;

// import java.util.Date;

// import org.springframework.stereotype.Component;

// import io.jsonwebtoken.Claims;
// import io.jsonwebtoken.Jwts;
// import io.jsonwebtoken.SignatureAlgorithm;

// @Component
// public class JwtUtil {

//     private final String SECRET_KEY = "inventory_secret_key";

//     public String generateToken(String username) {

//         return Jwts.builder()
//                 .setSubject(username)
//                 .setIssuedAt(new Date())
//                 .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60)) // 1 hour
//                 .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
//                 .compact();
//     }

//     public String extractUsername(String token) {

//         return extractClaims(token).getSubject();
//     }

//     public boolean validateToken(String token) {

//         return !extractClaims(token)
//                 .getExpiration()
//                 .before(new Date());
//     }

//     private Claims extractClaims(String token) {

//         return Jwts.parser()
//                 .setSigningKey(SECRET_KEY)
//                 .parseClaimsJws(token)
//                 .getBody();
//     }
// }
