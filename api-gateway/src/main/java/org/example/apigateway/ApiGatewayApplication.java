package org.example.apigateway;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import jakarta.ws.rs.HttpMethod;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableDiscoveryClient
@OpenAPIDefinition(info = @Info(title = "API Gateway", version = "1.0", description = "Documentation API Gateway v1.0"))
public class ApiGatewayApplication {

  public static void main(String[] args) {
    SpringApplication.run(ApiGatewayApplication.class, args);
  }

  @Bean
  public RouteLocator routeLocator(RouteLocatorBuilder builder) {
    return builder
            .routes()
            .route(r -> r.path("/api/book/v3/api-docs").and().method(HttpMethod.GET).uri("lb://book-service"))
            .route(r -> r.path("/api/record/v3/api-docs").and().method(HttpMethod.GET).uri("lb://library-service"))
            .route(r -> r.path("/api/auth/v3/api-docs").and().method(HttpMethod.GET).uri("lb://auth-service"))
            .build();
  }

}
