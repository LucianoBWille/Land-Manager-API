package com.lucianobwille.landmanagerapi.models;

import java.time.LocalDateTime;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import org.hibernate.annotations.Type;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@Entity 
@Table(name = "tb_user")
@NamedQueries({
    @NamedQuery(name = "User.findByName", query = "SELECT u FROM User u WHERE u.name LIKE ?1"),
    @NamedQuery(name = "User.findByEmail", query = "SELECT u FROM User u WHERE u.email LIKE ?1"),
    @NamedQuery(name = "User.findByNameAndPassword", query = "SELECT u FROM User u WHERE u.name LIKE ?1 AND u.password LIKE ?2")
})
public class User {
  @Id
  @Type(type = "org.hibernate.type.UUIDCharType")
  private UUID id;

  @Column(name = "name", nullable = false, length = 100)
  private String name;

  @Column(name = "email", nullable = false, length = 100)
  private String email;

  @Column(name = "password", nullable = false, length = 100)
  private String password;

  private LocalDateTime createdAt;
  private LocalDateTime updatedAt;

  public User() {
    this.id = UUID.randomUUID();
    this.createdAt = LocalDateTime.now();
    this.updatedAt = LocalDateTime.now();
  }
}
