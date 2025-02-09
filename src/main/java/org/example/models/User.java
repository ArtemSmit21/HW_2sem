package org.example.models;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class User {
  private Integer id;
  private String firstName;
  private String lastName;
  private String email;
}
