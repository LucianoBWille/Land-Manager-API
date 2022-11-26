package com.lucianobwille.landmanagerapi.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LandDTO {
  private String name;
  private String landPoligonString;
  private String ownerId;
}
