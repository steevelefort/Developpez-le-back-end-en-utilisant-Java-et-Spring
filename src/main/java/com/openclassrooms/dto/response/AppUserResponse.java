package com.openclassrooms.dto.response;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

import com.openclassrooms.model.AppUser;
import com.openclassrooms.util.DateUtils;

public class AppUserResponse extends BaseResponse {

  private Integer id;
  private String email;
  private String name;
  private String created_at;
  private String updated_at;

  public AppUserResponse() {
  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public AppUserResponse(AppUser user) {
    this.id = user.getId();
    this.email = user.getEmail();
    this.name = user.getName();
    this.created_at = DateUtils.formatDate(user.getCreatedAt());
    this.updated_at = DateUtils.formatDate(user.getUpdatedAt());
  }

  public String getCreated_at() {
    return created_at;
  }

  public void setCreated_at(String created_at) {
    this.created_at = created_at;
  }

  public String getUpdated_at() {
    return updated_at;
  }

  public void setUpdated_at(String updated_at) {
    this.updated_at = updated_at;
  }

}
