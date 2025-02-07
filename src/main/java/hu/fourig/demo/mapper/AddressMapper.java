package hu.fourig.demo.mapper;

import hu.fourig.demo.data.AddressDto;
import hu.fourig.demo.data.CreateAddressDto;
import hu.fourig.demo.model.Address;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AddressMapper {

    Address createDtoToEntity(CreateAddressDto createPartnerDto);
    AddressDto entityToDto(Address partner);
    List<AddressDto> entitiesToDtos(List<Address> partners);
}
