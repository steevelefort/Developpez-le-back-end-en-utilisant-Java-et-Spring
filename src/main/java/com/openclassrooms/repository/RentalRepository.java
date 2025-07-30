package com.openclassrooms.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.openclassrooms.model.Rental;

@Repository
public interface RentalRepository extends CrudRepository<Rental, Long> {

}
