package com.lucianobwille.landmanagerapi.repositories;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lucianobwille.landmanagerapi.models.Land;

public interface LandRepository extends JpaRepository<Land, UUID> {
  public List<Land> findByName(String name);

  public List<Land> findByOwnerId(UUID ownerId);
}
