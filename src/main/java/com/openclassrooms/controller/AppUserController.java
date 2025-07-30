package com.openclassrooms.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.openclassrooms.service.AppUserService;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:4200")
public class AppUserController {

  @Autowired
  private AppUserService appUserService;

  @PostMapping(value = "/register", produces = "application/json")
  public String register() {
    return "{\"token\":\"xxxxxx\"}";
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




