package com.openclassrooms.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * A DTO object to return a secure AppUser (without password)
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AppUserResponse {

  private Integer id;
  private String email;
  private String name;
  private String created_at;
  private String updated_at;

}
