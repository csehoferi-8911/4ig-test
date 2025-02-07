package hu.fourig.demo.data;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AddressPartnerDTO {
    private Long addressId;
    private String street;
    private String city;
    private String zipCode;
    private String number;
    private Long partnerId;
    private String name;
    private String email;
    private String phone;
}
