package com.densoft.whatsappclone.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;

@Service
public class TokenProvider {

    SecretKey secretKey = Keys.hmacShaKeyFor(JWTConstant.SECRET_KEY.getBytes());

    public String generateToken(Authentication authentication) {
        return Jwts.builder().setIssuer("whatsappclone")
                .setIssuedAt(new Date())
                .setExpiration(new Date(new Date().getTime() + 8640000))
                .claim("email", authentication.getName())
                .signWith(secretKey)
                .compact();
    }

    public String getEmailFromToken(String jwt) {
        jwt = jwt.substring(7);
        Claims claims = Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(jwt).getBody();

        return String.valueOf(claims.get("email"));
    }
}
