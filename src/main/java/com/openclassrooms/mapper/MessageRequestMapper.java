package com.openclassrooms.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.openclassrooms.dto.request.MessageRequest;
import com.openclassrooms.model.Message;

/**
 * Maps Messages requests
 */
@Mapper(componentModel = "spring")
public interface MessageRequestMapper {

  /**
   * Converts MessageRequest to Message entity
   *
   * @param messageRequest the message request
   * @return Message entity
   */
  @Mapping(target = "id", ignore = true)
  @Mapping(target = "createdAt", ignore = true)
  @Mapping(target = "updatedAt", ignore = true)
  @Mapping(target = "user", ignore = true)
  @Mapping(target = "rental", ignore = true)
  Message toEntity(MessageRequest messageRequest);

}
