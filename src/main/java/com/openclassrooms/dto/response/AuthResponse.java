package com.openclassrooms.dto.response;

/**
 * A DTO simple objet returning the JWT
 */
public class AuthResponse extends BaseResponse {

  private String token;

  public AuthResponse(String token) {
    this.token = token;
  }

  public String getToken() {
    return token;
  }

  public void setToken(String token) {
    this.token = token;
  }

}
