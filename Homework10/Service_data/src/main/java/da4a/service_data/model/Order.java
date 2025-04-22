package da4a.service_data.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String userSub; // Хранит 'sub' из JWT (уникальный ID пользователя в Keycloak)
    private double totalAmount;
}