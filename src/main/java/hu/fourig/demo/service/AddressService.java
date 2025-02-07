package hu.fourig.demo.service;

import hu.fourig.demo.data.AddressDto;
import hu.fourig.demo.data.AddressPartnerDTO;
import hu.fourig.demo.mapper.AddressMapper;
import hu.fourig.demo.repository.AddressRepository;
import hu.fourig.demo.repository.PartnerRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AddressService {
    private final AddressRepository addressRepository;
    private final AddressMapper addressMapper;
    private final PartnerRepository partnerRepository;

    public List<AddressDto> getAllAddresses() {
        return addressMapper.entitiesToDtos(addressRepository.findAll());
    }

    public List<AddressPartnerDTO> searchAddresses(String street, String city, String zipCode, String number) {
        return convertToAddressPartnerDTO(addressRepository.searchAddressesWithPartners(street, city, zipCode, number));
    }

    @Transactional
    public AddressDto updateAddress(final AddressDto addressDto) {
        addressRepository.findById(addressDto.id()).orElseThrow(() -> new EntityNotFoundException( "Address not found" ) );
        return addressMapper.entityToDto(addressRepository.save(addressMapper.dtoToEntity(addressDto)));
    }

    @Transactional
    public void deleteAddress(Long id) {
        addressRepository.deleteById(id);
    }

    private List<AddressPartnerDTO> convertToAddressPartnerDTO(List<Object[]> result) {
        List<AddressPartnerDTO> dtoList = new ArrayList<>();
        for (Object[] row : result) {
            AddressPartnerDTO dto = new AddressPartnerDTO(
                    (Long) row[0], // a.id
                    (String) row[1], // a.street
                    (String) row[2], // a.city
                    (String) row[3], // a.zipCode
                    (String) row[4], // a.number
                    (Long) row[5], // p.id
                    (String) row[6], // p.name
                    (String) row[7], // p.email
                    (String) row[8]  // p.phone
            );
            dtoList.add(dto);
        }
        return dtoList;
    }
}
