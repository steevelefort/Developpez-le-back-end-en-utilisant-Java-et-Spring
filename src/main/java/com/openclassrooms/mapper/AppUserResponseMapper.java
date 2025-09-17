package com.openclassrooms.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.openclassrooms.dto.response.AppUserResponse;
import com.openclassrooms.model.AppUser;

/**
 * Maps AppUser entities and responses
 */
@Mapper(componentModel = "spring")
public interface AppUserResponseMapper {

  /**
   * Converts an AppUser entity to AppUserResponse
   *
   * @param appUser the user entity
   * @return AppUserResponse dto
   */
  @Mapping(source = "createdAt", target = "created_at")
  @Mapping(source = "updatedAt", target = "updated_at")
  AppUserResponse toResponse(AppUser appUser);

}
