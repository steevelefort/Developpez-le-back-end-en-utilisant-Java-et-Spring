package com.openclassrooms.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.openclassrooms.dto.request.LoginRequest;
import com.openclassrooms.dto.request.RegisterRequest;
import com.openclassrooms.dto.response.AppUserResponse;
import com.openclassrooms.dto.response.AuthResponse;
import com.openclassrooms.dto.response.ErrorResponse;
import com.openclassrooms.service.AppUserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:4200")
public class AppUserController {

  @Autowired
  private AppUserService appUserService;

  @PostMapping(value = "/register", produces = "application/json")
  public ResponseEntity<?> register(@Valid @RequestBody RegisterRequest request) {
    try {
      String jwtToken = appUserService.register(request);
      return ResponseEntity.ok(new AuthResponse(jwtToken));
    } catch (Exception e) {
      return ResponseEntity.badRequest().body(new ErrorResponse(e.getMessage()));
    }
  }

  @PostMapping(value = "/login", produces = "application/json")
  public ResponseEntity<?> login(@Valid @RequestBody LoginRequest request) {
    try {
      String jwtToken = appUserService.login(request);
      System.out.println("Login OK");
      return ResponseEntity.ok(new AuthResponse(jwtToken));
    } catch (Exception e) {
      return ResponseEntity.badRequest().body(new ErrorResponse(e.getMessage()));
    }
  }

  @GetMapping(value = "/me", produces = "application/json")
  public ResponseEntity<?> me(@AuthenticationPrincipal Jwt jwt) {
    // System.out.println("Me called");
    try {
      // System.out.println("Step 1");
      Integer userId = ((Number) jwt.getClaim("userId")).intValue();
      // System.out.println("Step 2");
      AppUserResponse appUserResponse = appUserService.getUser(userId);
      // System.out.println("Step 3");
      // System.out.println("Me succeed for user "+userId.toString());
      return ResponseEntity.ok(appUserResponse);
    } catch (Exception e) {
      System.out.println("plantage " + e.getMessage());
      return ResponseEntity.badRequest().body(new ErrorResponse(e.getMessage()));
    }
  }

}
