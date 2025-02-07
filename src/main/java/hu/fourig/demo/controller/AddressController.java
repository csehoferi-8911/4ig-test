package hu.fourig.demo.controller;

import hu.fourig.demo.data.AddressDto;
import hu.fourig.demo.model.Address;
import hu.fourig.demo.service.AddressService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/addresses")
@RequiredArgsConstructor
public class AddressController {
    private final AddressService addressService;

    @GetMapping
    public List<AddressDto> getAllAddresses() {
        return addressService.getAllAddresses();
    }

    @GetMapping("/search")
    public List<AddressDto> searchByCity(@RequestParam String city) {
        return addressService.searchByCity(city);
    }
}
