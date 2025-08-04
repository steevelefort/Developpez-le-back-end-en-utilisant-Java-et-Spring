package com.openclassrooms.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

/**
 * A DTO class for received message
 */
public class MessageRequest {
    
    public MessageRequest() {
        
    }

    @NotBlank(message = "Veuillez saisir un message")
    @Size(max = 2000, message = "Le message doit contenir au maximum 2000 caractères")
    String message;

    @NotNull(message = "Veuillez saisir un id d'utilisateur")
    @JsonProperty("user_id")
    Integer userId;

    @NotNull(message = "Veuillez saisir un id de location")
    @JsonProperty("rental_id")
    Integer rentalId;

    public String getMessage() {
      return message;
    }

    public void setMessage(String message) {
      this.message = message;
    }

    public Integer getUserId() {
      return userId;
    }

    public void setUserId(Integer userId) {
      this.userId = userId;
    }

    public Integer getRentalId() {
      return rentalId;
    }

    public void setRentalId(Integer rentalId) {
      this.rentalId = rentalId;
    }

    
}
