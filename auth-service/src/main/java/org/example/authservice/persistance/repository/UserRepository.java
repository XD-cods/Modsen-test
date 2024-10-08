package org.example.authservice.persistance.repository;

import org.example.authservice.persistance.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

  Optional<User> findByEmail(String email);

  Boolean existsUserByEmail(String email);
}
