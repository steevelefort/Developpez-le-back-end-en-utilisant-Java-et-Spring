package com.openclassrooms.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.openclassrooms.model.AppUser;

@Repository
public interface AppUserRepository extends CrudRepository<AppUser, Integer>{

  boolean existsByEmail(String email);

}
