package org.iesalixar.daw2.dadogar.dwese_ticket_logger_api.utils;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.KeyPair;
import io.jsonwebtoken.security.Keys;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cglib.core.internal.Function;
import org.springframework.stereotype.Component;


import javax.crypto.SecretKey;
import java.util.Date;
import java.util.List;


@Component
public class JwtUtil {

    @Autowired
    private KeyPair jwtKeyPair;

    private static final long JWT_EXPIRATION = 3600000;


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


    public Claims extractAllClaims(String token) {
        return Jwts.parser() // JwtParserBuilder
                .verifyWith(jwtKeyPair.getPublic()) // Configura la clave para verificar la firma
                .build() // JwtParser
                .parseSignedClaims(token) // Verifica el token y lo parsea
                .getPayload(); // Devuelve el cuerpo del JWT (claims)
    }


    public String generateToken(String username, List<String> roles) {
        return Jwts.builder()
                .subject(username) // Configura el claim "sub" (nombre de usuario)
                .claim("roles", roles) // Incluye los roles como claim adicional
                .issuedAt(new Date()) // Fecha de emisión del token
                .expiration(new Date(System.currentTimeMillis() + JWT_EXPIRATION)) // Expira en 1 hora
                .signWith(jwtKeyPair.getPrivate(), Jwts.SIG.RS256) // Firma el token con la clave secreta
                .compact(); // Genera el token en formato JWT
    }


    public boolean validateToken(String token, String username) {
        Claims claims = Jwts.parser() // JwtParserBuilder
                .verifyWith(jwtKeyPair.getPublic()) // Configura la clave para verificar la firma
                .build() // JwtParser
                .parseSignedClaims(token) // Jws<Claims>
                .getPayload(); // JwsClaims
        return username.equals(claims.getSubject()) && !isTokenExpired(claims);
    }


    private boolean isTokenExpired(Claims claims) {
        return claims.getExpiration().before(new Date());
    }








}
