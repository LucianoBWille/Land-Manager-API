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
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lucianobwille.landmanagerapi.dto.UserDTO;
import com.lucianobwille.landmanagerapi.dto.LoginDTO;
import com.lucianobwille.landmanagerapi.models.User;
import com.lucianobwille.landmanagerapi.repositories.UserRepository;

@RestController
@RequestMapping("users")
@CrossOrigin(origins = "http://localhost:4200")
public class UserController {
  @Autowired
  private UserRepository userRepository;

  @GetMapping(produces = "application/json")
  public List<User> getAll(@RequestParam(required = false) String name) {
    if (Strings.isNotEmpty(name)) {
      return userRepository.findByName('%'+name+'%');
      // return userRepository.findByName(name);
    } else {
      return userRepository.findAll();
    }
  }

  @PostMapping("/login")
  public ResponseEntity<Object>  getLogin(@RequestBody LoginDTO loginDTO)  {
    String name = loginDTO.getName();
    String password = loginDTO.getPassword();

    if (Strings.isEmpty(name) || Strings.isEmpty(password)) {
      return ResponseEntity.badRequest().body("Need to inform name and password");
    } else {
      List<User> res = userRepository.findByNameAndPassword(name, password);

      if (res.size() == 1) {
        User user = res.get(0);
        String token = "{\"id\":" + user.getId() +
                      ",\"name\":"+user.getName() +
                      ",\"email\":"+user.getEmail() +
                      ",\"password\":"+user.getPassword()+
                      ",\"createdAt\":"+user.getCreatedAt().toString()+
                      ",\"updatedAt\":"+user.getUpdatedAt().toString()+"}";
        return ResponseEntity.ok(res.get(0));
      } else {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        //if more then one user log error
        // if (res.size() > 0) {
        //   System.out.println("More then one user with same name and password");
        // } else {
        //   System.out.println("User not found");
        // }
      }
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

    Optional<User> res = userRepository.findById(uuid);

    if (res.isPresent()) {
      return ResponseEntity.ok(res.get());
    } else {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
    }
  }

  @PostMapping
  public ResponseEntity<Object> save(@RequestBody UserDTO userDTO) {
    User user = new User();
    BeanUtils.copyProperties(userDTO, user);

    LocalDateTime now = LocalDateTime.now(ZoneId.of("UTC"));

    user.setCreatedAt(now);
    user.setUpdatedAt(now);

    //find user by name
    List<User> res = userRepository.findByName(user.getName());
    //if user exist return error
    if (res.size() > 0) {
      return ResponseEntity.badRequest().body("User already exist");
    }

    return ResponseEntity.ok(userRepository.save(user)) ;
  }

  @PutMapping("{id}")
  public ResponseEntity<Object> update(@PathVariable String id, @RequestBody UserDTO userDTO) {
    // validate UUID
    UUID uuid;
    try {
      uuid = UUID.fromString(id);
    } catch (Exception e) {
      return ResponseEntity.badRequest().body("Invalid ID format");
    }

    Optional<User> res = userRepository.findById(uuid);

    User user = res.get();
    BeanUtils.copyProperties(userDTO, user);
    user.setUpdatedAt(LocalDateTime.now(ZoneId.of("UTC")));

    return ResponseEntity.ok(userRepository.save(user));
  }

  @DeleteMapping(
    value = "{id}", 
    produces="text/plain"
    )
  public ResponseEntity<Object> delete(@PathVariable String id) {
    // validate UUID
    UUID uuid;
    try {
      uuid = UUID.fromString(id);
    } catch (Exception e) {
      return ResponseEntity.badRequest().body("Invalid ID format");
    }

    Optional<User> res = userRepository.findById(uuid);

    if (res.isPresent()) {
      userRepository.delete(res.get());
      return ResponseEntity.ok("User deleted");
    } else {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
    }
  }

}
