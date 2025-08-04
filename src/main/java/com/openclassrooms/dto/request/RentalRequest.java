package com.openclassrooms.dto.request;

import java.math.BigDecimal;

import org.springframework.web.multipart.MultipartFile;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

@Schema(description = "Rental creation data")
public class RentalRequest {

  public RentalRequest() {

  }

  @NotBlank(message = "Veuillez saisir un nom")
  @Size(max = 255, message = "Le nom doit contenir au maximum 255 caractères")
  private String name;

  @NotNull(message = "Veuillez saisir une surface")
  @Positive(message = "La surface doit être positive")
  private BigDecimal surface;

  @NotNull(message = "Veuillez saisir un prix")
  @Positive(message = "Le prix doit être positif")
  private BigDecimal price;

  @NotBlank(message = "Veuillez saisir une description")
  @Size(max = 2000, message = "La description doit contenir au maximum 2000 caractères")
  private String description;

  @Schema(description = "Rental picture", type = "string", format = "binary")
  @NotNull(message = "L’image est obligatoire")
  private MultipartFile picture;

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

  public MultipartFile getPicture() {
    return picture;
  }

  public void setPicture(MultipartFile picture) {
    this.picture = picture;
  }
}
