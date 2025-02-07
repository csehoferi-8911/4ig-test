package hu.fourig.demo.data;

import lombok.Builder;

@Builder
public record CreateOrSearchAddressDto(String street, String city, String zipCode, String number) {
}
