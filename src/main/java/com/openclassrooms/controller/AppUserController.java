package com.openclassrooms.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.openclassrooms.dto.request.RegisterRequest;
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
      System.out.println("token " + jwtToken);
      return ResponseEntity.ok(new AuthResponse(jwtToken));
    } catch (Exception e) {
      System.out.println("Error response");
      System.out.println(e.getMessage());
      return ResponseEntity.badRequest().body(new ErrorResponse(e.getMessage()));
    }
  }

  @PostMapping(value = "/login", produces = "application/json")
  public String login() {
    return "{\"token\":\"xxxxxx\"}";
  }

  @GetMapping(value = "/me", produces = "application/json")
  public String me() {
    return "{\"name\":\"xxxxxx\"}";
  }

}
