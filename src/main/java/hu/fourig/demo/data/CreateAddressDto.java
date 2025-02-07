package hu.fourig.demo.data;

import lombok.Builder;

@Builder
public record CreateAddressDto(String street, String city, String zipCode) {
}
