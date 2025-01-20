package org.iesalixar.daw2.dadogar.dwese_ticket_logger_api.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.cglib.core.internal.Function;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.List;

@Component
public class JwtUtil {

@Value("${jwt.secret}")
private String secretKeyFromProperties;

private SecretKey getSigningKey(){
    return Keys.hmacShaKeyFor(secretKeyFromProperties.getBytes());
}

public String extractUsername(String token){
    return extractClaim(token, Claims::getSubject);
}

public <T> T extractClaim(String token, Function<Claims, T> claimsResolver){
    final Claims claims = extractAllClaims(token);
    return claimsResolver.apply(claims);
}

public Claims extractAllClaims(String token){
    return Jwts.parser()
            .verifyWith(getSigningKey())
            .build()
            .parseSignedClaims(token)
            .getPayload();
}

public String generateToken(String username, List<String> roles){
    return Jwts.builder()
            .subject(username)
            .claim("roles",roles)
            .issuedAt(new Date())
            .expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60))
            .signWith(getSigningKey())
            .compact();
}

public boolean validateToken(String token, String username) {
    final String extractedUsername = extractUsername(token);
    return extractedUsername.equals(username) && !isTokenExpired(token);
}

private boolean isTokenExpired(String token) {
    return extractClaim(token, Claims::getExpiration).before(new Date());
}




}
