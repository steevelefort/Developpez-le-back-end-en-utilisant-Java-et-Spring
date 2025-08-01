package com.openclassrooms.dto.response;

/**
 * This is a DTO class for error response
 */
public class ErrorResponse {

    private String message;

    public ErrorResponse(String message) {
      this.message = message;
    }

    public String getMessage() {
      return message;
    }

    public void setMessage(String message) {
      this.message = message;
    }

}
