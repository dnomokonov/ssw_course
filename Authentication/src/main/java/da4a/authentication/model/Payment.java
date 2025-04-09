package da4a.authentication.model;

import da4a.authentication.model.status.PaymentStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "payments")
public abstract class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private float amount;

    @Column(name = "payment_status")
    @Enumerated(EnumType.STRING)
    private PaymentStatus status;

    @OneToOne
    @JoinColumn(name = "order_id", unique = true)
    private Order order;

    @Setter
    @Column(name = "payment_type")
    private String type;

    public String getType() {
        return this.getClass().getSimpleName().toUpperCase();
    }
}
