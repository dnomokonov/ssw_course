package da4a.ordersystem.model.value.measurements;

import da4a.ordersystem.model.value.Measurement;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Embeddable
@Getter
@Setter
public class Weight implements Measurement {
    private BigDecimal value;
    private String name;
    private String symbol;
}
