package org.example.authservice.util.mapper;

import lombok.RequiredArgsConstructor;
import org.example.authservice.persistance.entity.User;
import org.example.authservice.web.request.RegistrationRequest;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserMapper {
  private final ModelMapper modelMapper;

  public User registrRequestToUser(RegistrationRequest registrationRequest) {
    return modelMapper.map(registrationRequest, User.class);
  }
}
