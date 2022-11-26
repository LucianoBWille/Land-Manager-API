package com.lucianobwille.landmanagerapi.models;

import java.time.LocalDateTime;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import org.hibernate.annotations.ManyToAny;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.annotations.Type;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@Entity
@Table(name = "tb_land")
@NamedQueries({
    @NamedQuery(name = "Land.findByName", query = "SELECT l FROM Land l WHERE l.name LIKE :name"),
    @NamedQuery(name = "Land.findByOwner", query = "SELECT l FROM Land l WHERE l.owner.id = :ownerId")
})
public class Land {
  @Id
  @Type(type = "org.hibernate.type.UUIDCharType")
  private UUID id;

  @Column(name = "name", nullable = false, length = 100)
  private String name;

  @Column(name = "landPoligonString", nullable = false, length = 1000)
  private String landPoligonString;

  @ManyToOne
  @JoinColumn(name = "owner_id", nullable = false)
  private User owner;

  private LocalDateTime createdAt;
  private LocalDateTime updatedAt;

  public Land() {
    this.id = UUID.randomUUID();
    this.createdAt = LocalDateTime.now();
    this.updatedAt = LocalDateTime.now();
  }
}
