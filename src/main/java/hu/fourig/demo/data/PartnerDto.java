package hu.fourig.demo.data;

import lombok.Builder;

import java.util.List;

@Builder
public record PartnerDto(Long id, String name, String email, String phone, List<AddressDto> addresses) {
}
