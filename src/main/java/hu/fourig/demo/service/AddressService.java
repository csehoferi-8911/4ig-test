package hu.fourig.demo.service;

import hu.fourig.demo.data.AddressDto;
import hu.fourig.demo.mapper.AddressMapper;
import hu.fourig.demo.model.Address;
import hu.fourig.demo.repository.AddressRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AddressService {
    private final AddressRepository addressRepository;
    private final AddressMapper addressMapper;

    public List<AddressDto> getAllAddresses() {
        return addressMapper.entitiesToDtos(addressRepository.findAll());
    }

    public List<AddressDto> searchByCity(String city) {
        return addressMapper.entitiesToDtos(addressRepository.findByCityContainingIgnoreCase(city));
    }

    public Address saveAddress(Address address) {
        return addressRepository.save(address);
    }

    public void deleteAddress(Long id) {
        addressRepository.deleteById(id);
    }
}
