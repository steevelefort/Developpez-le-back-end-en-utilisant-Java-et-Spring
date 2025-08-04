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
import com.openclassrooms.dto.response.BaseResponse;
import com.openclassrooms.dto.response.SimpleResponse;
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
  @ApiResponse(responseCode = "400", content = @Content(schema = @Schema(implementation = SimpleResponse.class)))
  public ResponseEntity<BaseResponse> register(@Valid @RequestBody RegisterRequest request) {
    try {
      String jwtToken = appUserService.register(request);
      return ResponseEntity.ok(new AuthResponse(jwtToken));
    } catch (Exception e) {
      return ResponseEntity.badRequest().body(new SimpleResponse(e.getMessage()));
    }
  }

  @PostMapping(value = "/login", produces = "application/json")
  @Operation(summary = "Authenticate a registered user")
  @SecurityRequirements({})
  @ApiResponse(responseCode = "200", content = @Content(schema = @Schema(implementation = AuthResponse.class)))
  @ApiResponse(responseCode = "400", content = @Content(schema = @Schema(implementation = SimpleResponse.class)))
  public ResponseEntity<BaseResponse> login(@Valid @RequestBody LoginRequest request) {
    try {
      String jwtToken = appUserService.login(request);
      return ResponseEntity.ok(new AuthResponse(jwtToken));
    } catch (Exception e) {
      return ResponseEntity.badRequest().body(new SimpleResponse(e.getMessage()));
    }
  }

  @GetMapping(value = "/me", produces = "application/json")
  @Operation(summary = "Get authenticated user information")
  @ApiResponse(responseCode = "200", content = @Content(schema = @Schema(implementation = AppUserResponse.class)))
  @ApiResponse(responseCode = "400", content = @Content(schema = @Schema(implementation = SimpleResponse.class)))
  public ResponseEntity<BaseResponse> me(@AuthenticationPrincipal Jwt jwt) {
    try {
      Integer userId = ((Number) jwt.getClaim("userId")).intValue();
      AppUserResponse appUserResponse = appUserService.getUser(userId);
      return ResponseEntity.ok(appUserResponse);
    } catch (Exception e) {
      return ResponseEntity.badRequest().body(new SimpleResponse(e.getMessage()));
    }
  }

}
