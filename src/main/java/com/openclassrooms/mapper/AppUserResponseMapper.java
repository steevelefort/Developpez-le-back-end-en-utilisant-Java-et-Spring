package com.openclassrooms.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.openclassrooms.dto.response.AppUserResponse;
import com.openclassrooms.model.AppUser;

/**
 * Maps User entities and responses
 */
@Mapper(componentModel = "spring")
public interface AppUserResponseMapper {

  /**
   * Converts a AppUser entity to AppUserResponse
   *
   * @Param appUser the user entity
   * @Return UserAppResponse dto
   */
  @Mapping(source = "createdAt", target = "created_at")
  @Mapping(source = "updatedAt", target = "updated_at")
  AppUserResponse toResponse(AppUser appUser);

}
