package com.openclassrooms.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.openclassrooms.dto.response.AppUserResponse;
import com.openclassrooms.service.AppUserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;

@RestController
@RequestMapping("/api/user")
public class AppUserController {

  @Autowired
  private AppUserService appUserService;

  @GetMapping(value = "/{id}", produces = "application/json")
  @Operation(summary = "Get a user information")
  @ApiResponse(responseCode = "401", description = "Unauthorized", content=@Content())
  @ApiResponse(responseCode = "200", content = @Content(schema =
  @Schema(implementation = AppUserResponse.class)))
  @ApiResponse(responseCode = "400", description = "Bad request", content=@Content())
  public AppUserResponse user(@PathVariable Integer id) {
    AppUserResponse userResponse = appUserService.getUser(id);
    return userResponse;
  }

}
