package com.openclassrooms.dto.request;

import java.math.BigDecimal;

import org.springframework.web.multipart.MultipartFile;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * A DTO class to collect new Rental data
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Rental creation data")
public class RentalRequest {

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

}
