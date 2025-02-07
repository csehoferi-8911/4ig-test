package hu.fourig.demo.data;

import java.util.List;


public record CreateOrSearchPartnerDto(String name, String email, String phone, List<CreateOrSearchAddressDto> addresses) {
}
