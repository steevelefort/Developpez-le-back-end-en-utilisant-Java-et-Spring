package com.openclassrooms.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.openclassrooms.dto.request.LoginRequest;
import com.openclassrooms.dto.request.RegisterRequest;
import com.openclassrooms.dto.response.AppUserResponse;
import com.openclassrooms.dto.response.AuthResponse;
import com.openclassrooms.service.AppUserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirements;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

  @Autowired
  private AppUserService appUserService;

  @PostMapping(value = "/register", produces = "application/json")
  @Operation(summary = "Register a new user")
  @SecurityRequirements({})
  @ApiResponse(responseCode = "200", content = @Content(schema = @Schema(implementation = AuthResponse.class)))
  @ApiResponse(responseCode = "400", description = "Bad request", content = @Content())
  public AuthResponse register(@Valid @RequestBody RegisterRequest request) {
      AuthResponse response = appUserService.register(request);
      return response; 
  }

  @PostMapping(value = "/login", produces = "application/json")
  @Operation(summary = "Authenticate a registered user")
  @SecurityRequirements({})
  @ApiResponse(responseCode = "200", content = @Content(schema = @Schema(implementation = AuthResponse.class)))
  @ApiResponse(responseCode = "400", description = "Bad request", content = @Content())
  @ApiResponse(responseCode = "401", description = "Not authorized", content = @Content())
  public AuthResponse login(@Valid @RequestBody LoginRequest request) {
      AuthResponse response = appUserService.login(request);
      return response;
  }

  @GetMapping(value = "/me", produces = "application/json")
  @Operation(summary = "Get authenticated user information")
  @ApiResponse(responseCode = "200", content = @Content(schema = @Schema(implementation = AppUserResponse.class)))
  @ApiResponse(responseCode = "401", description = "Not authorized", content = @Content())
  public AppUserResponse me(@AuthenticationPrincipal Jwt jwt) {
      Integer userId = ((Number) jwt.getClaim("userId")).intValue();
      AppUserResponse response = appUserService.getUser(userId);
      return response;
  }

}
