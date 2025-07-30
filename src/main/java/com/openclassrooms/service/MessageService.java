package com.openclassrooms.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.openclassrooms.model.Message;
import com.openclassrooms.repository.MessageRepository;

@Service
public class MessageService {

  @Autowired
  private MessageRepository messageRepository;

  public Optional<Message> getMessage(final Integer id) {
    return messageRepository.findById(id);
  }

  public Iterable<Message> getMessages() {
    return messageRepository.findAll();
  }

  public void deleteMessage(final Integer id) {
    messageRepository.deleteById(id);
  }

  public Message saveMessage(final Message message) {
    return messageRepository.save(message);
  }


}
