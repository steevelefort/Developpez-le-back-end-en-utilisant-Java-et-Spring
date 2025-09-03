package com.openclassrooms.mapper;

import org.mapstruct.Mapper;

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
  Message toEntity(MessageRequest messageRequest);

}
