package org.example.authservice.web.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.example.authservice.persistance.entity.User;
import org.example.authservice.service.UserService;
import org.example.authservice.util.JwtTokenUtil;
import org.example.authservice.util.mapper.UserMapper;
import org.example.authservice.web.request.AuthenticationRequest;
import org.example.authservice.web.request.RegistrationRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/api/auth")
public class UserController {

  private final UserService userService;
  private final JwtTokenUtil jwtTokenUtil;
  private final UserMapper userMapper;

  @Operation(summary = "аунтефицироваться и получить jwt ключ", description = "Возвращает jwt ключ")
  @ApiResponses(value = {
          @ApiResponse(responseCode = "200", description = "JWT ключ получен"),
          @ApiResponse(responseCode = "404", description = "почта не найдены"),
          @ApiResponse(responseCode = "400", description = "Ошибка в запросе")
  })
  @PostMapping(value = "/login")
  public ResponseEntity<String> authenticateUser(
          @Valid @RequestBody AuthenticationRequest request
  ) {
    User user = userService.getUserByEmail(request.getEmail());

    if (userService.checkPassword(request.getPassword(), user.getPassword())) {
      String token = jwtTokenUtil.generateToken(user);
      return ResponseEntity.ok(token);
    }

    return ResponseEntity.badRequest().body("Invalid email or password");
  }

  @Operation(summary = "зарегистрироваться и добавить данные в БД", description = "сообщение об успешной регистрации")
  @ApiResponses(value = {
          @ApiResponse(responseCode = "200", description = "регистрация прошла успешно"),
          @ApiResponse(responseCode = "400", description = "Ошибка в запросе")
  })
  @PostMapping(value = "/register")
  public ResponseEntity<String> registerUser(
          @Valid @RequestBody RegistrationRequest request
  ) {
    User user = userMapper.registrRequestToUser(request);

    userService.registerUser(user);
    return ResponseEntity.ok("User registered successfully");
  }
}
