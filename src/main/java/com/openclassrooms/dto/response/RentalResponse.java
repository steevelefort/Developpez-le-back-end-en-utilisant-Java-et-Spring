package com.openclassrooms.dto.response;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * A DTO class to return a Rental
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RentalResponse {

  private Integer id;
  private String name;
  private BigDecimal surface;
  private BigDecimal price;
  private String picture;
  private String description;
  private Integer owner_id;
  private String created_at;
  private String updated_at;

}
