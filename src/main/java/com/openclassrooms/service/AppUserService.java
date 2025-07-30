package com.openclassrooms.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.openclassrooms.model.AppUser;
import com.openclassrooms.repository.AppUserRepository;

@Service
public class AppUserService {

  @Autowired
  private AppUserRepository appUserRepository;

  public Optional<AppUser> getUser(final Long id) {
    return appUserRepository.findById(id);
  }

  public Iterable<AppUser> getUsers() {
    return appUserRepository.findAll();
  }

  public void deleteUser(final Long id) {
    appUserRepository.deleteById(id);
  }

  public AppUser saveUser(final AppUser user) {
    return appUserRepository.save(user);
  }


}
