package com.openclassrooms.service;

import java.time.Instant;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.openclassrooms.dto.request.LoginRequest;
import com.openclassrooms.dto.request.RegisterRequest;
import com.openclassrooms.model.AppUser;
import com.openclassrooms.repository.AppUserRepository;

@Service
public class AppUserService {

  @Autowired
  private AppUserRepository appUserRepository;

  @Autowired
  private PasswordEncoder passwordEncoder;

  @Autowired
  private JwtService jwtService;

  /**
   * Get one user by id
   *
   * @param id the user id in the database
   * @return AppUser a user entity
   * @throws Exception if user not found
   */
  public AppUser getUser(final Integer id) throws Exception {
      AppUser user = appUserRepository.findById(id).orElseThrow(() -> new Exception("Utilisateur introuvable"));
      return user;
  }

  /**
   * Registers a user in the database and returns à JSON Web Token
   *
   * @param request provided user data
   * @return String the generated JSON Web Token
   * @throws Exception if the user email already exist
   */
  public String register(RegisterRequest request) throws Exception {
    if (appUserRepository.existsByEmail(request.getEmail())) {
      throw new Exception("Email déjà existant");
    }
    String hashedPassword = passwordEncoder.encode(request.getPassword());
    AppUser user = new AppUser(
        request.getName(),
        request.getEmail(),
        hashedPassword);
    user.setCreatedAt(Instant.now());
    user.setUpdatedAt(Instant.now());

    AppUser savedUser = appUserRepository.save(user);
    String token = jwtService.generateToken(savedUser.getEmail(), savedUser.getId());
    return token;
  }

  /**
   * Authenticates a user from credentials and returns a JSON Web Token
   *
   * @param request a request DTO with credentials
   * @return String a json web token
   * @throws Exception if the credentials are wrong.
   */
  public String login(LoginRequest request) throws Exception {
    String errorMessage = "Email ou mot de passe incorrect";
    AppUser user = appUserRepository.findByEmail(request.getEmail()).orElseThrow(() -> new Exception(errorMessage));
    if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
      throw new RuntimeException(errorMessage);
    }
    String token = jwtService.generateToken(user.getEmail(), user.getId());
    return token;
  }

}
