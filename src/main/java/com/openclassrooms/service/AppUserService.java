package com.openclassrooms.service;

import java.time.Instant;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.openclassrooms.dto.request.LoginRequest;
import com.openclassrooms.dto.request.RegisterRequest;
import com.openclassrooms.dto.response.AppUserResponse;
import com.openclassrooms.dto.response.AuthResponse;
import com.openclassrooms.mapper.AppUserResponseMapper;
import com.openclassrooms.mapper.UserRegisterMapper;
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

  @Autowired
  private UserRegisterMapper userRegisterMapper;

  @Autowired
  private AppUserResponseMapper appUserResponseMapper;

  /**
   * Get one user by id
   *
   * @param id the user id in the database
   * @return AppUser a user entity
   * @throws Exception if user not found
   */
  public AppUserResponse getUser(final Integer id) {
      AppUser user = appUserRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "error"));
      AppUserResponse appUserResponse = appUserResponseMapper.toResponse(user);
      return appUserResponse;
  }

  /**
   * Registers a user in the database and returns à JSON Web Token
   *
   * @param request provided user data
   * @return String the generated JSON Web Token
   * @throws Exception if the user email already exist
   */
  public AuthResponse register(RegisterRequest request) {
    if (appUserRepository.findByEmail(request.getEmail()).isPresent()) {
      throw new IllegalArgumentException("Un utilisateur avec cet email existe déjà");
    }

    // Validation password length - aligned with test data (6 chars) rather than frontend validation (3 chars)
    String password = request.getPassword();
    if (password.length()<6) {
      throw new IllegalArgumentException("Le mot de passe doit faire au moins 6 caractères");
    }
    
    AppUser user = userRegisterMapper.toEntity(request);
    String hashedPassword = passwordEncoder.encode(request.getPassword());
    user.setPassword(hashedPassword);
    AppUser savedUser = appUserRepository.save(user);
    String token = jwtService.generateToken(savedUser.getEmail(), savedUser.getId());
    return new AuthResponse(token);
  }

  /**
   * Authenticates a user from credentials and returns a JSON Web Token
   *
   * @param request a request DTO with credentials
   * @return String a json web token
   * @throws Exception if the credentials are wrong.
   */
  public AuthResponse login(LoginRequest request) {
    String errorMessage = "Email ou mot de passe incorrect";
    AppUser user = appUserRepository.findByEmail(request.getEmail()).orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "error"));
    if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
        throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "error");
    }
    String token = jwtService.generateToken(user.getEmail(), user.getId());
    return new AuthResponse(token);
  }

}
