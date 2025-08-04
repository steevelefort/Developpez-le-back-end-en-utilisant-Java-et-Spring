package com.openclassrooms.dto.response;

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
