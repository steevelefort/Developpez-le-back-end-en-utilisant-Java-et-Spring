package com.openclassrooms.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * A DTO class for user login
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginRequest {

  @NotBlank(message = "L’email est obligatoire")
  private String email;

  @NotBlank(message = "Le mot de passe est obligatoire")
  @ToString.Exclude
  private String password;

}
