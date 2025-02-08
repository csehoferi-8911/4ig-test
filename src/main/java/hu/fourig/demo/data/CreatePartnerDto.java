package hu.fourig.demo.data;

import lombok.Builder;

import java.util.List;

@Builder
public record CreatePartnerDto(String name, String email, String phone, List<CreateAddressDto> addresses) {
}
