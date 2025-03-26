package io.swagger.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
public class Comments {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @NotNull
  private String text;

  // Конструктор по умолчанию (нужен для JPA)
  public Comments() {
  }

  public Comments(Long id, String text) {
    this.id = id;
    this.text = text;
  }

  @Override
  public String toString() {
    return "Comments{" +
            "id=" + id +
            ", text='" + text + '\'' +
            '}';
  }
}