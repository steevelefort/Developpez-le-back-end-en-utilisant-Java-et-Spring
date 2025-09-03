package com.openclassrooms.controller;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import com.openclassrooms.dto.request.RentalRequest;
import com.openclassrooms.dto.request.RentalUpdateRequest;
import com.openclassrooms.dto.response.SimpleResponse;
import com.openclassrooms.mapper.RentalReponseMapper;
import com.openclassrooms.dto.response.BaseResponse;
import com.openclassrooms.dto.response.RentalListResponse;
import com.openclassrooms.dto.response.RentalResponse;
import com.openclassrooms.model.Rental;
import com.openclassrooms.service.RentalService;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;

@RestController
@RequestMapping("/api")
public class RentalController {

  @Autowired
  RentalService rentalService;

  public RentalController() {

  }

  @GetMapping(value = "/rentals", produces = "application/json")
  @Operation(summary = "Get a list of all rentals")
  // @ApiResponse(responseCode = "200", content = @Content(schema =
  // @Schema(implementation = RentalListResponse.class)))
  public RentalListResponse getRentals() {
    RentalListResponse response = rentalService.getRentals();
    return response;
  }

  @GetMapping(value = "/rentals/{id}", produces = "application/json")
  @Operation(summary = "Get a rental by id")
  // @ApiResponse(responseCode = "200", content = @Content(schema =
  // @Schema(implementation = RentalResponse.class)))
  // @ApiResponse(responseCode = "400", content = @Content(schema =
  // @Schema(implementation = SimpleResponse.class)))
  public RentalResponse getRentalById(@PathVariable Integer id) {
    RentalResponse rentalResponse = rentalService.getRental(id);
    return rentalResponse;
  }

  // Beware of ModelAttribute : the Angular app send a FormData object !
  @PostMapping(value = "/rentals", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = "application/json")
  @Operation(summary = "Create a new rental", requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(required = true, content = @Content(mediaType = MediaType.MULTIPART_FORM_DATA_VALUE, schema = @Schema(implementation = RentalRequest.class))))
  // @ApiResponse(responseCode = "200", content = @Content(schema =
  // @Schema(implementation = SimpleResponse.class)))
  // @ApiResponse(responseCode = "400", content = @Content(schema =
  // @Schema(implementation = SimpleResponse.class)))
  // @ApiResponse(responseCode = "500", content = @Content(schema =
  // @Schema(implementation = SimpleResponse.class)))
  public SimpleResponse postRental(
      @Valid @ModelAttribute RentalRequest request,
      @RequestParam("picture") MultipartFile picture,
      @AuthenticationPrincipal Jwt jwt) {

    // Check if a file is uploaded
    if (picture.isEmpty()) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "L’image est obligatoire");
    }

    // Fix a limit of 2MB for pictures
    if (picture.getSize() > 2 * 1024 * 1024) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "L’image est trop volumineuse (2Mo maximum)");
    }

    // Check if the provided file type is allowed
    if (!isAllowedImage(picture)) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
          "Veuillez fournir une image valide (.jpg, .jpeg, .png)");
    }

    // Get the current authenticated user (owner)
    Integer userId = ((Number) jwt.getClaim("userId")).intValue();

    rentalService.createRental(request, picture, userId);
    return new SimpleResponse("Rental created !");
  }

  @PutMapping(value = "/rentals/{id}", produces = "application/json")
  @Operation(summary = "Update a rental")
  // @ApiResponse(responseCode = "200", content = @Content(schema =
  // @Schema(implementation = SimpleResponse.class)))
  // @ApiResponse(responseCode = "403", content = @Content(schema =
  // @Schema(implementation = SimpleResponse.class)))
  // @ApiResponse(responseCode = "404", content = @Content(schema =
  // @Schema(implementation = SimpleResponse.class)))
  // @ApiResponse(responseCode = "500", content = @Content(schema =
  // @Schema(implementation = SimpleResponse.class)))
  public ResponseEntity<BaseResponse> putRental(
      @Valid @ModelAttribute RentalUpdateRequest request,
      @AuthenticationPrincipal Jwt jwt,
      @PathVariable Integer id) {

    Integer userId = ((Number) jwt.getClaim("userId")).intValue();

    try {
      rentalService.updateRental(request, userId, id);
      return ResponseEntity.ok(new SimpleResponse("Rental updated !"));
    } catch (SecurityException e) {
      return ResponseEntity.status(403).build();
    } catch (EntityNotFoundException e) {
      return ResponseEntity.status(404).build();
    } catch (Exception e) {
      return ResponseEntity.status(500).body(new SimpleResponse("Erreur lors de l’enregistrement"));
    }
  }

  /**
   * Check if the file format is allowed
   *
   * @param picture a multipart uploaded objet
   * @return boolean true is the file format is allowed
   */
  private boolean isAllowedImage(MultipartFile picture) {
    String pictureContentType = picture.getContentType();

    // There is an arbitrary choice here. We can maybe add some other image formats
    if (!Arrays.asList("image/jpeg", "image/png", "image/jpg").contains(pictureContentType)) {
      return false;
    }
    return true;
  }

}
