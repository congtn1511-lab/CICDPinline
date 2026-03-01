package org.example.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

@Component
public class JwtProvider {
    @Value("${jwt.secret-key}")
    private String SECRET;
    @Value("${jwt.access-exp}")
    private int EXPACCESS;
    @Value("${jwt.refresh-exp}")
    private int EXPREFRESH;

    private SecretKey getSigningKey() {
        return Keys.hmacShaKeyFor(SECRET.getBytes(StandardCharsets.UTF_8));
    }

    public String generateAccessToken(Claims claims, LocalDateTime time) {
        ZoneId zone = ZoneId.systemDefault(); Date at = Date.from(time.atZone(zone).toInstant());
        return Jwts.builder().setClaims(claims).setExpiration(Date.from(time.plusMinutes(EXPACCESS).atZone(ZoneId.systemDefault()).toInstant())).signWith(getSigningKey(), SignatureAlgorithm.HS256).compact();
    }

    public String generateRefreshToken(Claims claims, LocalDateTime time) {
        ZoneId zone = ZoneId.systemDefault(); Date at = Date.from(time.atZone(zone).toInstant());
        return Jwts.builder().setClaims(claims).setIssuedAt(at).setExpiration(Date.from(time.plusMinutes(EXPREFRESH).atZone(ZoneId.systemDefault()).toInstant())).signWith(getSigningKey(), SignatureAlgorithm.HS256).compact();
    }

    public Claim decode(String token){
        Jws<Claims> jws = Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token);
        return mapToClaim(jws.getBody());
    }

    private Claim mapToClaim(Claims claims){
        Claim claim = new Claim();
        claim.setUserName(getValue(claims, "user"));
        claim.setUserName(getValue(claims, "exp"));
        return claim;
    }

    private String getValue(Claims claims, String key) {
        Object value = claims.get(key);
        return value != null ? String.valueOf(value) : null;
    }
}
