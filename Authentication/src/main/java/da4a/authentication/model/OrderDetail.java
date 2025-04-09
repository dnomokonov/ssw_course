package da4a.authentication.model;

import da4a.authentication.model.value.measurements.Quantity;

import jakarta.persistence.Embeddable;
import jakarta.persistence.Embedded;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Embeddable
@Getter
@Setter
public class OrderDetail {
    @Embedded
    private Quantity quantity;

    private String taxStatus;

    @ManyToOne
    @JoinColumn(name = "item_id")
    private Item item;
}
