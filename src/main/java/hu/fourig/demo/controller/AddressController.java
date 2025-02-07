package hu.fourig.demo.controller;

import hu.fourig.demo.data.AddressDto;
import hu.fourig.demo.data.AddressPartnerDTO;
import hu.fourig.demo.service.AddressService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/addresses")
@RequiredArgsConstructor
public class AddressController {
    private final AddressService addressService;

    @GetMapping
    public ResponseEntity<List<AddressDto>> getAllAddresses() {
        return ResponseEntity.ok(addressService.getAllAddresses());
    }

    @PutMapping
    public ResponseEntity<AddressDto> updateAddress(@RequestBody final AddressDto addressDto) {
        return ResponseEntity.ok(addressService.updateAddress(addressDto));
    }

    @DeleteMapping
    public void deleteAddress(@RequestBody final Long id) {
        addressService.deleteAddress(id);
    }

    @GetMapping("/search")
    public ResponseEntity<List<AddressPartnerDTO>> searchAddresses(
            @RequestParam(required = false) final String street,
            @RequestParam(required = false) final String city,
            @RequestParam(required = false) final String zipCode,
            @RequestParam(required = false) final String number) {
        return ResponseEntity.ok(addressService.searchAddresses(street, city, zipCode, number));
    }
}
