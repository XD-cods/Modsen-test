package org.example.authservice.web.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class AuthenticationRequest {

  @NotBlank
  private String email;

  @NotBlank
  private String password;
}
