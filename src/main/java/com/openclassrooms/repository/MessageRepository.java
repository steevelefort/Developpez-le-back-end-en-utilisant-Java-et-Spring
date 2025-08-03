package com.openclassrooms.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.openclassrooms.model.Message;

@Repository
public interface MessageRepository extends CrudRepository<Message, Integer> {

}
