package org.example.authservice.util;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.example.authservice.persistance.entity.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtTokenUtil {

  @Value("${jwt.secret}")
  private String SECRET_KEY;

  public String generateToken(User user) {
    Map<String, Object> claims = new HashMap<>();
    claims.put("role", user.getRole());
    return createToken(claims, user.getEmail());
  }

  private String createToken(Map<String, Object> claims, String subject) {
    return Jwts.builder()
            .setClaims(claims)
            .setSubject(subject)
            .setIssuedAt(new Date(System.currentTimeMillis()))
            .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10))
            .signWith(getSigningKey(), SignatureAlgorithm.HS256)
            .compact();
  }

  private Key getSigningKey() {
    byte[] keyBytes = SECRET_KEY.getBytes();
    return new SecretKeySpec(keyBytes, SignatureAlgorithm.HS256.getJcaName());
  }
}
