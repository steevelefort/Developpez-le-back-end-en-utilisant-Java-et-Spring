package com.openclassrooms.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.openclassrooms.model.Rental;
import com.openclassrooms.repository.RentalRepository;

@Service
public class RentalService {

  @Autowired
  private RentalRepository rentalRepository;

  public Optional<Rental> getRental(final Integer id) {
    return rentalRepository.findById(id);
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
