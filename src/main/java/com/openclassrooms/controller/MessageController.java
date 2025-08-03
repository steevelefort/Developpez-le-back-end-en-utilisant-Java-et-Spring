package com.openclassrooms.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.oauth2.jwt.Jwt;

import com.openclassrooms.dto.request.MessageRequest;
import com.openclassrooms.dto.response.SimpleResponse;
import com.openclassrooms.service.MessageService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api")
public class MessageController {

  @Autowired
  private MessageService messageService;

  public MessageController() {

  }

  @PostMapping(value = "/messages", produces = "application/json")
  public ResponseEntity<?> postMessage(
      @Valid @RequestBody MessageRequest request,
      @AuthenticationPrincipal Jwt jwt
      ) {

    System.out.println("Enter the /api/messages route");

    Integer userId = ((Number) jwt.getClaim("userId")).intValue();

    try {
      messageService.saveMessage(request, userId); 
      return ResponseEntity.ok(new SimpleResponse("Message send with success"));
    } catch (Exception e) {
      System.out.println(e.getMessage());
      return ResponseEntity.badRequest().body(new SimpleResponse("Impossible d’envoyer ce message"));
    }
  }


}
