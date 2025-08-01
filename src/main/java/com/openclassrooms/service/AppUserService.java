package com.openclassrooms.service;

import java.util.Optional;

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

  public Optional<AppUser> getUser(final Integer id) {
    return appUserRepository.findById(id);
  }

  public Iterable<AppUser> getUsers() {
    return appUserRepository.findAll();
  }

  public void deleteUser(final Integer id) {
    appUserRepository.deleteById(id);
  }

  public AppUser saveUser(final AppUser user) {
    return appUserRepository.save(user);
  }

  /**
   * Register a user in the database and return à JSON Web Token
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
        hashedPassword
        );
    AppUser savedUser = saveUser(user);
    String token = jwtService.generateToken(savedUser.getEmail(), savedUser.getId());
    return token;
  }


  public String login(LoginRequest request) throws Exception {
    String errorMessage = "Email ou mot de passe incorrect";
    AppUser user = appUserRepository.findByEmail(request.getEmail()).orElseThrow(() -> new Exception(errorMessage));
    if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
      System.out.println("mot de passe incorrect");
      throw new Exception(errorMessage);
    }
    String token = jwtService.generateToken(user.getEmail(), user.getId());
    return token;
  }

}
