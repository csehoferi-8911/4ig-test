package hu.fourig.demo.service;

import hu.fourig.demo.data.CreatePartnerDto;
import hu.fourig.demo.data.PartnerDto;
import hu.fourig.demo.mapper.AddressMapper;
import hu.fourig.demo.mapper.PartnerMapper;
import hu.fourig.demo.repository.PartnerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PartnerService {
    private final PartnerRepository partnerRepository;
    private final PartnerMapper partnerMapper;
    private final AddressMapper addressMapper;

    public List<PartnerDto> getAllPartners() {
        return partnerMapper.entitiesToDtos(partnerRepository.findAll());
    }

    @Transactional
    public PartnerDto savePartner(CreatePartnerDto createPartnerDto) {
        final var partner = partnerMapper.createDtoToEntity(createPartnerDto);
        return partnerMapper.entityToDto(partnerRepository.save(partner));
    }

    @Transactional
    public void deletePartner(Long id) {
        partnerRepository.deleteById(id);
    }
}