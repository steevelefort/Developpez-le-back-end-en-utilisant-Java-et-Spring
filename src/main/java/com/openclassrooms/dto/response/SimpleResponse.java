package com.openclassrooms.dto.response;

/**
 * This is a DTO class for error response
 */
public class SimpleResponse extends BaseResponse {

    private String message;

    public SimpleResponse(String message) {
      this.message = message;
    }

    public String getMessage() {
      return message;
    }

    public void setMessage(String message) {
      this.message = message;
    }

}
