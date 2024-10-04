package org.example.authservice.service;

import lombok.AllArgsConstructor;
import org.example.authservice.exception.BadRequestException;
import org.example.authservice.persistance.entity.User;
import org.example.authservice.persistance.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class UserService {

  private final UserRepository repository;
  private final PasswordEncoder passwordEncoder;

  public User getUserByEmail(String email) {
    return Optional.ofNullable(repository.findByEmail(email))
            .orElseThrow(() -> new BadRequestException("User with email is not found."));
  }

  public boolean checkPassword(String rawPassword, String encodedPassword) {
    return passwordEncoder.matches(rawPassword, encodedPassword);
  }

  public void registerUser(User user) {
    if (repository.findByEmail(user.getEmail()) != null) {
      throw new BadRequestException("User already exists with the same email.");
    }

    user.setPassword(passwordEncoder.encode(user.getPassword()));
    repository.save(user);
  }
}
