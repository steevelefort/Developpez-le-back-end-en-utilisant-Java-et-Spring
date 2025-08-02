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
import com.openclassrooms.dto.response.ErrorResponse;
import com.openclassrooms.service.AppUserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/user")
public class AppUserController {

  @Autowired
  private AppUserService appUserService;

  @GetMapping(value = "/{id}", produces = "application/json")
  public ResponseEntity<?> user(@PathVariable Integer id) {
    try {
      AppUserResponse response = appUserService.getUser(id);
      return ResponseEntity.ok(response);
    } catch (Exception e) {
      return ResponseEntity.badRequest().body(new ErrorResponse(e.getMessage()));
    }
  }

}
