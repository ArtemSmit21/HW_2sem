package org.example.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@Schema(name = "User", description = "Сущность пользователя")
public class User {
  @Schema(description = "Уникальный идетификатор", example = "111")
  private Integer id;
  private String firstName;
  private String lastName;
  private String email;
}
