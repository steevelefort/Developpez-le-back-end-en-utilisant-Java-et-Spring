package com.openclassrooms.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.openclassrooms.dto.request.RentalRequest;
import com.openclassrooms.model.Rental;

/**
 * Maps Rental requests
 */
@Mapper(componentModel = "spring")
public interface RentalRequestMapper {

  /**
   * Converts RentalRequest to Rental entity
   *
   * @param rentalRequest the rental request
   * @return Rental entity
   */
  @Mapping(target = "picture", ignore = true)
  @Mapping(target = "id", ignore = true)
  @Mapping(target = "ownerId", ignore = true)
  @Mapping(target = "createdAt", ignore = true)
  @Mapping(target = "updatedAt", ignore = true)
  @Mapping(target = "owner", ignore = true)
  Rental toEntity(RentalRequest rentalRequest);

}
