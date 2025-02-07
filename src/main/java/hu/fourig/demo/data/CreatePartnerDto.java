package hu.fourig.demo.data;

import java.util.List;


public record CreatePartnerDto(String name, String email, String phone, List<CreateAddressDto> addresses) {
}
