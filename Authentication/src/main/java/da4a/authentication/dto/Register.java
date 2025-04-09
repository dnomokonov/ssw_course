package da4a.authentication.dto;

import da4a.authentication.model.value.Address;
import lombok.Data;

@Data
public class Register {
    private String name;
    private Address address;
    private String username;
    private String password;
}