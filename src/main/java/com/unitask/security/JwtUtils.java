package com.unitask.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.*;
import java.util.stream.Collectors;

@Component
@Slf4j
public class JwtUtils {
    private static final Logger logger = LoggerFactory.getLogger(JwtUtils.class);

    @Value("${jwt.secret}")
    private String jwtSecret;

    @Value("${jwt.Expiration}")
    private int jwtExpiration;

    public static final String ROLE_CLAIM = "roles";
    public static final String USER_CLAIM = "user";


    public String generateJwtToken(Authentication authentication) {
        UserDetails userPrincipal = (UserDetails) authentication.getPrincipal();
        Optional<? extends GrantedAuthority> firstAuthority = userPrincipal.getAuthorities().stream().findFirst();
        String authority = firstAuthority.map(GrantedAuthority::getAuthority).orElse("NUH_UH");;
        List<String> roles = userPrincipal.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList());
        return Jwts.builder()
                .subject(userPrincipal.getUsername())
                .issuedAt(new Date())
                .expiration(new Date(new Date().getTime() + jwtExpiration))
                .signWith(key())
                .claim(ROLE_CLAIM, roles)
                .compact();
    }

    public SecretKey key() {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret));
    }

    public boolean validateJwtToken(String authtoken) {
        try {
            Jwts.parser().verifyWith(key()).build().parse(authtoken);
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
        return Jwts.parser().verifyWith(key()).build().parseClaimsJws(authtoken).getBody().getSubject();
    }

    public Map<String, String> getClaims(String authtoken) {
        Map<String, String> map = new HashMap<>();
        Jws<Claims> claims = Jwts.parser().verifyWith(key()).build().parseSignedClaims(authtoken);
        Claims payload = claims.getPayload();
        map.put(ROLE_CLAIM, (String) payload.get("roles"));
        map.put(USER_CLAIM, payload.getSubject());
        return map;
    }
}
