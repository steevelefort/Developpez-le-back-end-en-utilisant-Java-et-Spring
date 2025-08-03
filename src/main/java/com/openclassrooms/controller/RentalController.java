package com.openclassrooms.controller;

import java.io.IOException;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.openclassrooms.dto.request.RentalRequest;
import com.openclassrooms.dto.request.RentalUpdateRequest;
import com.openclassrooms.dto.response.SimpleResponse;
import com.openclassrooms.dto.response.RentalListResponse;
import com.openclassrooms.dto.response.RentalResponse;
import com.openclassrooms.model.Rental;
import com.openclassrooms.service.RentalService;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api")
public class RentalController {

  @Autowired
  RentalService rentalService;

  public RentalController() {

  }

  @GetMapping(value = "/rentals", produces = "application/json")
  public ResponseEntity<?> getRentals() {
    Iterable<Rental> rentals = rentalService.getRentals();
    RentalListResponse response = new RentalListResponse(rentals);
    return ResponseEntity.ok(response);
  }

  @GetMapping(value = "/rentals/{id}", produces = "application/json")
  public ResponseEntity<?> getRentalById(@PathVariable Integer id) {
    try {
      RentalResponse response = rentalService.getRental(id);
      return ResponseEntity.ok(response);
    } catch (Exception e) {
      return ResponseEntity.badRequest().body(new SimpleResponse(e.getMessage()));
    }
  }

  // Beware of ModelAttribute : the Angular app send a FormData object !
  @PostMapping(value = "/rentals", produces = "application/json")
  public ResponseEntity<?> postRental(
      @Valid @ModelAttribute RentalRequest request,
      @RequestParam("picture") MultipartFile picture,
      @AuthenticationPrincipal Jwt jwt) {

    // Check if a file is uploaded
    if (picture.isEmpty()) {
      return ResponseEntity.badRequest().body(new SimpleResponse("L’image est obligatoire"));
    }

    // Fix a limit of 2MB for pictures
    if (picture.getSize() > 2 * 1024 * 1024) {
      return ResponseEntity.badRequest().body(new SimpleResponse("L'image ne doit pas dépasser 2Mo"));
    }

    // Check if the provided file type is allowed
    if (!isAllowedImage(picture)) {
      return ResponseEntity.badRequest()
          .body(new SimpleResponse("Veuillez fournir une image valide (.jpg, .jpeg, .png)"));
    }

    // Get the current authenticated user (owner)
    Integer userId = ((Number) jwt.getClaim("userId")).intValue();

    try {
      rentalService.createRental(request, picture, userId);
      return ResponseEntity.ok(new SimpleResponse("Rental created !"));
    } catch (IOException e) {
      return ResponseEntity.status(500).body(new SimpleResponse("Erreur lors de la sauvegarde de l'image"));
    } catch (Exception e) {
      return ResponseEntity.status(500).body(new SimpleResponse("Erreur lors de l’enregistrement"));
    }

  }

  @PutMapping(value = "/rentals/{id}", produces = "application/json")
  public ResponseEntity<?> putRental(
      @Valid @ModelAttribute RentalUpdateRequest request,
      // @RequestParam("picture") MultipartFile picture,
      @AuthenticationPrincipal Jwt jwt,
      @PathVariable Integer id) {

    System.out.println("/rentals/id");

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
