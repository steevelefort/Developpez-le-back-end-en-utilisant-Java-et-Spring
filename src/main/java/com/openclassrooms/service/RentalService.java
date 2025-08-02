package com.openclassrooms.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.openclassrooms.dto.response.RentalResponse;
import com.openclassrooms.model.Rental;
import com.openclassrooms.repository.RentalRepository;

@Service
public class RentalService {

  @Autowired
  private RentalRepository rentalRepository;

  public RentalResponse getRental(final Integer id) {
    Rental rental = rentalRepository.findById(id).orElseThrow(() -> new RuntimeException("Location introuvable"));
    return new RentalResponse(rental); 
  }

  public Iterable<Rental> getRentals() {
    return rentalRepository.findAll();
  }

  public void deleteRental(final Integer id) {
    rentalRepository.deleteById(id);
  }

  public Rental saveRental(final Rental rental) {
    return rentalRepository.save(rental);
  }


}
