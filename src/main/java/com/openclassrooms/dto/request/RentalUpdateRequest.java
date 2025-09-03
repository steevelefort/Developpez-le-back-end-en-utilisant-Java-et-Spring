package com.openclassrooms.dto.request;

import java.math.BigDecimal;

import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * A DTO class to collect Rental update data
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RentalUpdateRequest {

  @Size(max = 255, message = "Le nom doit contenir au maximum 255 caractères")
  private String name;

  @Positive(message = "La surface doit être positive")
  private BigDecimal surface;

  @Positive(message = "Le prix doit être positif")
  private BigDecimal price;

  @Size(max = 2000, message = "La description doit contenir au maximum 2000 caractères")
  private String description;

}
