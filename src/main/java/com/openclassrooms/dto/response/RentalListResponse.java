package com.openclassrooms.dto.response;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * A DTO class returning a list of Rentals
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RentalListResponse {

  private List<RentalResponse> rentals;

}
