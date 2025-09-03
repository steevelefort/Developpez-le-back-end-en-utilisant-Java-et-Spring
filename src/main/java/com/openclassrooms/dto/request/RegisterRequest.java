package com.openclassrooms.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * A DTO class for user registration
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {
  @NotBlank(message = "Veuillez saisir votre email")
  @Email(message = "Veuillez saisir une adresse mail valide")
  @Size(max = 255, message = "L’email doit faire moins de 255 caractères")
  private String email;

  @NotBlank(message = "Veuillez saisir votre nom")
  @Size(min = 3, max = 255, message = "Le nom doit contenir entre 3 et 255 caractères")
  private String name;

  // Angular front-end validates a minimal 3 characters length. 6 or 8 would be better.
  @NotBlank(message = "Veuillez saisir un mot de passe")
  @Size(min = 3, max = 255, message = "Le mot de passe doit contenir entre 3 et 255 caractères")
  @ToString.Exclude
  private String password;

}
