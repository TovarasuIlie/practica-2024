package com.PracticaVara.springJwt.service.AccountServices;

import com.PracticaVara.springJwt.model.Account.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.function.Function;

@Service
public class JwtService {

    private String SECRET_KEY = "fc5b4c9af1659c3001b9be14c193eeb51eedb411920b242a7d512bb6cb4d0788";

    public String extractUsername(String token){
        return extractClaim(token, Claims::getSubject);
    }

    public boolean isValid(String token, User user){
        String username = extractUsername((token));
        return  (username.equals(user.getUsername()))&& !isTokenExpired(token);
    }
    public boolean isTokenExpired(String token){
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token){
        return extractClaim(token, Claims::getExpiration);
    }
    public <T> T extractClaim(String token, Function<Claims, T> resolver){
        Claims claims = extractAllClaims(token);
        return resolver.apply(claims);
    }
    private Claims extractAllClaims(String token){
        return Jwts
                .parser()
                .verifyWith(getSigninKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public String generateToken(User user){
        String token = Jwts
                .builder()
                .setHeaderParam("typ","JWT")
                .subject(user.getUsername())
                .claim("username", user.getUsername())
                .claim("email", user.getEmail())
                .claim("registerDate", user.getRegisteredDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH-mm-ss")))
                .claim("role", user.getRole())
                .claim("firstName", user.getFirstName())
                .claim("lastName", user.getLastName())
                .claim("address", user.getIpAddress())
                .claim("phoneNumber", user.getAddress())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis()+24*60*60*1000))
                .signWith(getSigninKey())
                .compact();
        return token;
    }

    private SecretKey getSigninKey(){
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
