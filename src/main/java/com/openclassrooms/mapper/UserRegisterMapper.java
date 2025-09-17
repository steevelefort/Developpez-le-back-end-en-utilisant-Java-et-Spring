package com.openclassrooms.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.openclassrooms.dto.request.RegisterRequest;
import com.openclassrooms.model.AppUser;

/**
 * Maps User registration requests
 */
@Mapper(componentModel = "spring")
public interface UserRegisterMapper {
  /**
   * Converts RegisterRequest to AppUser entity
   *
   * @param registerRequest the registration request
   * @return AppUser entity
   */
  @Mapping(target = "id", ignore = true)
  @Mapping(target = "createdAt", ignore = true)
  @Mapping(target = "updatedAt", ignore = true)
  @Mapping(target = "password", ignore = true)
  AppUser toEntity(RegisterRequest registerRequest);
}
