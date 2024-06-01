package com.chuan.taskmanagement.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JwtUtills {
    private static final Logger logger = LoggerFactory.getLogger(JwtUtills.class);

    @Value("${jwt.secret}")
    private String jwtSecret;

    @Value("${jwt.Expiration}")
    private int jwtExpiration;

    public String generateJwtToken(Authentication authentication) {
        UserDetails userPricipal = (UserDetails) authentication.getPrincipal();
        return Jwts.builder()
                .setSubject(userPricipal.getUsername())
                .setIssuedAt(new Date())
                .setExpiration(new Date(new Date().getTime() + jwtExpiration))
                .signWith(key(), SignatureAlgorithm.HS256)
                .compact();
    }

    public Key key() {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret));
    }

    public boolean validateJwtToken(String authtoken) {
        try {
            Jwts.parser().setSigningKey(key()).build().parse(authtoken);
            return true;

        } catch (MalformedJwtException e) {
            logger.error("Invalid JWT Token : {}", e.getMessage());
        } catch (ExpiredJwtException e) {
            logger.error("JWT Token is Expired : {}", e.getMessage());
        } catch (UnsupportedJwtException e) {
            logger.error("Unsupported JWT :{}", e.getMessage());
        } catch (IllegalArgumentException e) {
            logger.error("JWT Payload is Empty: {}", e.getMessage());
        }
        return false;


    }

    public String getUsernameFromJwtToken(String authtoken) {
        return Jwts.parser().setSigningKey(key()).build().parseClaimsJws(authtoken).getBody().getSubject();
    }
}
