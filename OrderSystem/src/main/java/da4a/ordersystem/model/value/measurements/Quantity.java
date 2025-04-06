package da4a.ordersystem.model.value.measurements;

import da4a.ordersystem.model.value.Measurement;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Embeddable
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Quantity implements Measurement {
    private Integer value;
    private String name;
    private String symbol;
}
