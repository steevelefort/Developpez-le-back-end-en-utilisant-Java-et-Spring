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
   * Converts UserRegisterRequest to User entity
   *
   * @param userRegisterRequest the registration request
   * @return user entity
   */
  @Mapping(target = "id", ignore = true)
  @Mapping(target = "createdAt", ignore = true)
  @Mapping(target = "updatedAt", ignore = true)
  @Mapping(target = "password", ignore = true)
  AppUser toEntity(RegisterRequest registerRequest);
}
