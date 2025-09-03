package com.openclassrooms.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.openclassrooms.dto.response.RentalResponse;
import com.openclassrooms.model.Rental;

/**
 * Maps Rental entities and responses
 */
@Mapper(componentModel = "spring")
public interface RentalReponseMapper {
    
  /**
   * Converts rental entity to rentalResponse
   *
   * @param rental the rental entity
   * @return rental response
   */
  @Mapping(source = "createdAt", target = "created_at")
  @Mapping(source = "updatedAt", target = "updated_at")
  @Mapping(source = "ownerId", target = "owner_id")
  RentalResponse toResponse(Rental rental);

  /**
   * Converts list of Rental entities to list of RentalResponse
   *
   * @param rentals the list of rental entities
   * @return list of rental responses
   */
  @Mapping(source = "createdAt", target = "created_at")
  @Mapping(source = "updatedAt", target = "updated_at")
  @Mapping(source = "ownerId", target = "owner_id")
  List<RentalResponse> toResponseList(Iterable<Rental> rentals);
}
