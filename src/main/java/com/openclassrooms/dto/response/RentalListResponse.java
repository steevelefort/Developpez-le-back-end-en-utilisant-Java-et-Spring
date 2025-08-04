package com.openclassrooms.dto.response;

import java.util.ArrayList;
import java.util.List;

import com.openclassrooms.model.Rental;

/**
 * A DTO class returning a list of Rentals
 */
public class RentalListResponse extends BaseResponse {

  private List<RentalResponse> rentals;

  public RentalListResponse(Iterable<Rental> rentals) {
    this.rentals = new ArrayList<>();
    for (Rental rental: rentals) {
      this.rentals.add(new RentalResponse(rental));
    }
  }

  public List<RentalResponse> getRentals() {
    return rentals;
  }

  public void setRentals(List<RentalResponse> rentals) {
    this.rentals = rentals;
  }

}
