package hu.fourig.demo.mapper;

import hu.fourig.demo.data.CreatePartnerDto;
import hu.fourig.demo.data.PartnerDto;
import hu.fourig.demo.model.Partner;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PartnerMapper {

    Partner createDtoToEntity(CreatePartnerDto createPartnerDto);
    PartnerDto entityToDto(Partner partner);
    List<PartnerDto> entitiesToDtos(List<Partner> partners);
}
