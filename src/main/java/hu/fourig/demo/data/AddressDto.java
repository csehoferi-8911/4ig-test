package hu.fourig.demo.data;

import lombok.Builder;

@Builder
public record AddressDto(Long id, String street, String city, String zipCode, String number) {
}
