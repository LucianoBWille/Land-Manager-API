package com.lucianobwille.landmanagerapi.controllers;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lucianobwille.landmanagerapi.dto.LandDTO;
import com.lucianobwille.landmanagerapi.models.Land;
import com.lucianobwille.landmanagerapi.models.User;
import com.lucianobwille.landmanagerapi.repositories.LandRepository;
import com.lucianobwille.landmanagerapi.repositories.UserRepository;

@RestController
@RequestMapping("lands")
public class LandController {
  @Autowired
  private LandRepository landRepository;
  @Autowired
  private UserRepository userRepository;

  @GetMapping(produces = "application/json")
  public List<Land> getAll(@RequestParam(required = false) String name) {
    if (Strings.isNotEmpty(name)) {
      return landRepository.findByName(name);
    } else {
      return landRepository.findAll();
    }
  }

  @GetMapping("{id}")
  public ResponseEntity<Object> getById(@PathVariable String id) {

    // validate UUID
    UUID uuid;
    try {
      uuid = UUID.fromString(id);
    } catch (Exception e) {
      return ResponseEntity.badRequest().body("Invalid ID format");
    }

    Optional<Land> res = landRepository.findById(uuid);

    if (res.isPresent()) {
      return ResponseEntity.ok(res.get());
    } else {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Land not found");
    }
  }

  @PostMapping
  public Land save(@RequestBody LandDTO landDTO) {
    Land land = new Land();

    System.out.println("LandDTO => " + landDTO);

    UUID ownerId = UUID.fromString(landDTO.getOwnerId());

    Optional<User> owner = userRepository.findById(ownerId);
    BeanUtils.copyProperties(landDTO, land);
    land.setOwner(owner.get());

    LocalDateTime now = LocalDateTime.now(ZoneId.of("UTC"));

    land.setCreatedAt(now);
    land.setUpdatedAt(now);

    return landRepository.save(land);
  }

  @PutMapping("{id}")
  public ResponseEntity<Object> update(@PathVariable String id, @RequestBody LandDTO landDTO) {
    // validate UUID
    UUID uuid;
    try {
      uuid = UUID.fromString(id);
    } catch (Exception e) {
      return ResponseEntity.badRequest().body("Invalid ID format");
    }

    Optional<Land> res = landRepository.findById(uuid);

    Land land = res.get();
    BeanUtils.copyProperties(landDTO, land);
    land.setUpdatedAt(LocalDateTime.now(ZoneId.of("UTC")));

    return ResponseEntity.ok(landRepository.save(land));
  }

  @DeleteMapping("{id}")
  public ResponseEntity<Object> delete(@PathVariable String id) {
    // validate UUID
    UUID uuid;
    try {
      uuid = UUID.fromString(id);
    } catch (Exception e) {
      return ResponseEntity.badRequest().body("Invalid ID format");
    }

    Optional<Land> res = landRepository.findById(uuid);

    if (res.isPresent()) {
      landRepository.delete(res.get());
      return ResponseEntity.ok("Land deleted");
    } else {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Land not found");
    }
  }

}
