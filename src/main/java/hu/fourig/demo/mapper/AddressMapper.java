package hu.fourig.demo.mapper;

import hu.fourig.demo.data.AddressDto;
import hu.fourig.demo.model.Address;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AddressMapper {

    Address dtoToEntity( final AddressDto addressDto );
    AddressDto entityToDto(final Address partner);
    List<AddressDto> entitiesToDtos(final List<Address> partners);
}
