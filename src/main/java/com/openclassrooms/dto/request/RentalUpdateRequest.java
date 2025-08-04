package com.openclassrooms.dto.request;

import java.math.BigDecimal;

import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

/**
 * A DTO class to collect Rental update data
 */
public class RentalUpdateRequest {

  public RentalUpdateRequest() {

  }

  @Size(max = 255, message = "Le nom doit contenir au maximum 255 caractères")
  private String name;

  @Positive(message = "La surface doit être positive")
  private BigDecimal surface;

  @Positive(message = "Le prix doit être positif")
  private BigDecimal price;

  @Size(max = 2000, message = "La description doit contenir au maximum 2000 caractères")
  private String description;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public BigDecimal getSurface() {
    return surface;
  }

  public void setSurface(BigDecimal surface) {
    this.surface = surface;
  }

  public BigDecimal getPrice() {
    return price;
  }

  public void setPrice(BigDecimal price) {
    this.price = price;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

}
