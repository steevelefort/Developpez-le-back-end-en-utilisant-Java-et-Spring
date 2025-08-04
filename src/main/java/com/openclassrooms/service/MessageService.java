package com.openclassrooms.service;

import java.time.Instant;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.openclassrooms.dto.request.MessageRequest;
import com.openclassrooms.model.Message;
import com.openclassrooms.repository.MessageRepository;
import com.openclassrooms.repository.RentalRepository;

@Service
public class MessageService {

  @Autowired
  private MessageRepository messageRepository;

  @Autowired
  private RentalRepository rentalRepository;

  public boolean saveMessage(final MessageRequest request, Integer userId) throws Exception {

    // Check if userId is the one who send the message !
    if (!rentalRepository.existsById(request.getRentalId())) {
      throw new RuntimeException("Rental not found");
    }

    // The userId in the request is the sender id.
    // We need to check if this id is the same than the authenticated user.
    if (!request.getUserId().equals(userId)) {
      throw new RuntimeException("Not allowed to send a message for another user");
    }

    Message message = new Message();
    message.setUserId(userId);
    message.setRentalId(request.getRentalId());
    message.setMessage(request.getMessage());
    message.setCreatedAt(Instant.now());
    message.setUpdatedAt(Instant.now());

    messageRepository.save(message);

    return true;
  }


}
