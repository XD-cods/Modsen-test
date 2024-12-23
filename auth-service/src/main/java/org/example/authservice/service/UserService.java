package org.example.authservice.service;

import lombok.AllArgsConstructor;
import org.example.authservice.exception.BadRequestException;
import org.example.authservice.exception.NotFoundException;
import org.example.authservice.persistance.entity.User;
import org.example.authservice.persistance.repository.UserRepository;
import org.example.authservice.util.JwtTokenUtil;
import org.example.authservice.util.mapper.UserMapper;
import org.example.authservice.web.request.AuthenticationRequest;
import org.example.authservice.web.request.RegistrationRequest;
import org.example.authservice.web.response.JwtResponse;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserService {
  private final JwtTokenUtil jwtTokenUtil;
  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;
  private final UserMapper userMapper;

  private static final String NOT_FOUND_USER_BY_EMAIL_MESSAGE = "User with email %s not found";
  private static final String USER_ALREADY_EXIST_MESSAGE = "User with email %s already exists";
  private static final String WRONG_PASSWORD_MESSAGE = "Wrong password";
  private static final String USER_REGISTERED_MESSAGE = "User registered successfully";

  public String registerUser(RegistrationRequest registrationRequest) {
    if (userRepository.existsUserByEmail(registrationRequest.getEmail())) {
      throw new BadRequestException(String.format(USER_ALREADY_EXIST_MESSAGE, registrationRequest.getEmail()));
    }
    User user = userMapper.registerRequestToUser(registrationRequest);
    user.setPassword(passwordEncoder.encode(user.getPassword()));
    userRepository.save(user);
    return String.format(USER_REGISTERED_MESSAGE);
  }

  public JwtResponse authenticateUser(AuthenticationRequest request) {
    User user = userRepository.findByEmail(request.getEmail())
            .orElseThrow(() -> new NotFoundException(String.format(NOT_FOUND_USER_BY_EMAIL_MESSAGE, request.getEmail())));

    if (passwordEncoder.matches(request.getPassword(), user.getPassword())) {
      String token = jwtTokenUtil.generateToken(user);
      return new JwtResponse(token);
    } else {
      throw new BadRequestException(String.format(WRONG_PASSWORD_MESSAGE));
    }
  }
}
