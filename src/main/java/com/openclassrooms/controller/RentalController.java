package com.openclassrooms.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.openclassrooms.dto.response.RentalListResponse;
import com.openclassrooms.dto.response.RentalResponse;
import com.openclassrooms.model.Rental;
import com.openclassrooms.service.RentalService;

@RestController
@RequestMapping("/api")
public class RentalController {

  @Autowired
  RentalService rentalService;

  public RentalController() {

  }

  @GetMapping(value = "/rentals", produces = "application/json")
  public ResponseEntity<?> rentals() {
    Iterable<Rental> rentals = rentalService.getRentals();
    RentalListResponse response = new RentalListResponse(rentals);
    return ResponseEntity.ok(response);
  }

  // public ResponseEntity<?> rentals(@AuthenticationPrincipal Jwt jwt) {
}
