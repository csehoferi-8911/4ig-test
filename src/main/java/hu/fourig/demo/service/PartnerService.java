package hu.fourig.demo.service;

import hu.fourig.demo.data.CreateOrSearchPartnerDto;
import hu.fourig.demo.data.PartnerDto;
import hu.fourig.demo.mapper.PartnerMapper;
import hu.fourig.demo.model.Partner;
import hu.fourig.demo.repository.PartnerRepository;
import hu.fourig.demo.service.search.PartnerSpecification;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PartnerService {
    private final PartnerRepository partnerRepository;
    private final PartnerMapper partnerMapper;

    public List<PartnerDto> getAllPartners() {
        return partnerMapper.entitiesToDtos(partnerRepository.findAll());
    }

    @Transactional
    public PartnerDto savePartner(CreateOrSearchPartnerDto createOrSearchPartnerDto) {
        final var partner = partnerMapper.createDtoToEntity(createOrSearchPartnerDto);
        return partnerMapper.entityToDto(partnerRepository.save(partner));
    }

    @Transactional
    public PartnerDto updatePartner(PartnerDto partnerDto) {
        partnerRepository.findById(partnerDto.id()).orElseThrow(() -> new EntityNotFoundException("Partner not found"));
        final var partner = partnerMapper.dtoToEntity(partnerDto);
        return partnerMapper.entityToDto(partnerRepository.save(partner));
    }

    @Transactional
    public void deletePartner(Long id) {
        partnerRepository.deleteById(id);
    }

    public List<PartnerDto> searchPartners(String name, String email, String phone, String street, String city, String zipCode, String number) {
        Specification<Partner> spec = Specification
                .where(PartnerSpecification.hasNameContaining(name))
                .and(PartnerSpecification.hasEmailContaining(email))
                .and(PartnerSpecification.hasPhoneContaining(phone))
                .and(PartnerSpecification.hasAddressContaining(street, city, zipCode, number));

        return partnerMapper.entitiesToDtos(partnerRepository.findAll(spec));
    }
}