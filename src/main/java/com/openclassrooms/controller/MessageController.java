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
import com.openclassrooms.dto.response.BaseResponse;
import com.openclassrooms.dto.response.SimpleResponse;
import com.openclassrooms.service.MessageService;

import jakarta.validation.Valid;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirements;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;

@RestController
@RequestMapping("/api")
public class MessageController {

  @Autowired
  private MessageService messageService;

  public MessageController() {

  }

  @PostMapping(value = "/messages", produces = "application/json")
  @Operation(summary = "Send a message to a owner")
  @ApiResponse(responseCode = "200", content = @Content(schema = @Schema(implementation = SimpleResponse.class)))
  @ApiResponse(responseCode = "400", content = @Content(schema = @Schema(implementation = SimpleResponse.class)))
  public ResponseEntity<BaseResponse> postMessage(
      @Valid @RequestBody MessageRequest request,
      @AuthenticationPrincipal Jwt jwt
      ) {

    Integer userId = ((Number) jwt.getClaim("userId")).intValue();

    try {
      messageService.saveMessage(request, userId); 
      return ResponseEntity.ok(new SimpleResponse("Message send with success"));
    } catch (Exception e) {
      return ResponseEntity.badRequest().body(new SimpleResponse("Impossible d’envoyer ce message"));
    }
  }


}
