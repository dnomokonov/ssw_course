package da4a.authentication.dto;

import da4a.authentication.model.value.Address;
import lombok.Data;

@Data
public class CustomerDTO {
    private Long id;
    private String name;
    private Address address;
}