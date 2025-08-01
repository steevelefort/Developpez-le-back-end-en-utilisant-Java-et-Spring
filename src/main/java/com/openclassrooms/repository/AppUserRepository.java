package com.openclassrooms.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.openclassrooms.model.AppUser;

@Repository
public interface AppUserRepository extends CrudRepository<AppUser, Integer> {

  boolean existsByEmail(String email);

  Optional<AppUser> findByEmail(String email);

}
