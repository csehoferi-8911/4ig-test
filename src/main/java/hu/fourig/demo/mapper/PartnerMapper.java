package hu.fourig.demo.mapper;

import hu.fourig.demo.data.CreatePartnerDto;
import hu.fourig.demo.data.PartnerDto;
import hu.fourig.demo.model.Partner;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PartnerMapper {

    Partner createDtoToEntity(final CreatePartnerDto createPartnerDto);
    PartnerDto entityToDto(final Partner partner);
    Partner dtoToEntity(final PartnerDto partnerDto);
    List<PartnerDto> entitiesToDtos(final List<Partner> partners);
}
