package da4a.authentication.model.paymentType;

import da4a.authentication.model.Payment;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Column;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name = "credit_payments")
public class Credit extends Payment {
    private String number;

    @Column(name = "type")
    private String cardType;

    @Column(name = "exp_date")
    private LocalDateTime expDate;
}
