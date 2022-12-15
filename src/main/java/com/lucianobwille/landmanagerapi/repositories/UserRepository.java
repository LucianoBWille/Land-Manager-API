package com.lucianobwille.landmanagerapi.repositories;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lucianobwille.landmanagerapi.models.User;

public interface UserRepository extends JpaRepository<User, UUID> {
  public List<User> findByName(String name);

  public List<User> findByEmail(String email);

  public List<User> findByNameAndPassword(String name, String password);
}
