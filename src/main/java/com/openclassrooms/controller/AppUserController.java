package com.openclassrooms.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.openclassrooms.dto.response.AppUserResponse;
import com.openclassrooms.dto.response.BaseResponse;
import com.openclassrooms.dto.response.SimpleResponse;
import com.openclassrooms.service.AppUserService;

import jakarta.validation.Valid;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirements;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;

@RestController
@RequestMapping("/api/user")
public class AppUserController {

  @Autowired
  private AppUserService appUserService;

  @GetMapping(value = "/{id}", produces = "application/json")
  @Operation(summary = "Get a user information")
  @ApiResponse(responseCode = "200", content = @Content(schema = @Schema(implementation = AppUserResponse.class)))
  @ApiResponse(responseCode = "400", content = @Content(schema = @Schema(implementation = SimpleResponse.class)))
  public ResponseEntity<BaseResponse> user(@PathVariable Integer id) {
    try {
      AppUserResponse response = appUserService.getUser(id);
      return ResponseEntity.ok(response);
    } catch (Exception e) {
      return ResponseEntity.badRequest().body(new SimpleResponse(e.getMessage()));
    }
  }

}
