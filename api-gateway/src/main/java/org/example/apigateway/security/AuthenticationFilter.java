package org.example.apigateway.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
public class AuthenticationFilter implements GlobalFilter {

  @Value(value = "${jwt.secret}")
  private String SECRET_KEY;


  @Override
  public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
    ServerHttpRequest request = exchange.getRequest();

    if (request.getURI().getPath().contains("/api/auth/register") || request.getURI().getPath().contains("/api/auth") || request.getURI().getPath().contains("/v3/api-docs")) {
      return chain.filter(exchange);
    }

    if (!request.getHeaders().containsKey(HttpHeaders.AUTHORIZATION)) {
      throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Authorization header is missing");
    }

    String authorizationHeader = request.getHeaders().getFirst(HttpHeaders.AUTHORIZATION);

    if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
      throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Authorization header must start with Bearer");
    }

    String token = authorizationHeader.substring(7);

    if (token.isEmpty()) {
      throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Token is missing");
    }

    try {
      Claims claims = Jwts.parserBuilder()
              .setSigningKey(SECRET_KEY.getBytes())
              .build()
              .parseClaimsJws(token)
              .getBody();
      exchange.getRequest().mutate().header("role", String.valueOf(claims.get("role"))).build();
    } catch (Exception e) {
      throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid token");
    }

    return chain.filter(exchange);
  }
}
