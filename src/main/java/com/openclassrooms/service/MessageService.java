package com.openclassrooms.service;

import java.time.Instant;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.openclassrooms.dto.request.MessageRequest;
import com.openclassrooms.mapper.MessageRequestMapper;
import com.openclassrooms.model.Message;
import com.openclassrooms.repository.MessageRepository;
import com.openclassrooms.repository.RentalRepository;

@Service
public class MessageService {

  @Autowired
  private MessageRepository messageRepository;

  @Autowired
  private RentalRepository rentalRepository;

  @Autowired
  private MessageRequestMapper messageRequestMapper;

  /**
   * Create a new message in database
   *
   * @param request a DTO object with the message data
   * @param userId  the current authenticated user id
   * @return void
   */
  public void saveMessage(final MessageRequest request, Integer userId) {

    // Check if userId is the one who send the message !
    if (!rentalRepository.existsById(request.getRentalId())) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Rental not found");
    }

    // The userId in the request is the sender id.
    // We need to check if this id is the same than the authenticated user.
    if (!request.getUserId().equals(userId)) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Not allowed to send a message for another user");
    }

    Message message = messageRequestMapper.toEntity(request);

    messageRepository.save(message);
  }

}
