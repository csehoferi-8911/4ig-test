package hu.fourig.demo.service;

import hu.fourig.demo.data.AddressDto;
import hu.fourig.demo.data.CreatePartnerDto;
import hu.fourig.demo.data.PartnerDto;
import hu.fourig.demo.mapper.PartnerMapper;
import hu.fourig.demo.model.Partner;
import hu.fourig.demo.repository.PartnerRepository;
import hu.fourig.demo.service.search.PartnerSpecification;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class PartnerService {
    private final PartnerRepository partnerRepository;
    private final PartnerMapper partnerMapper;
    private final CsvExportService csvExportService;

    public List<PartnerDto> getAllPartners() {
        return partnerMapper.entitiesToDtos(partnerRepository.findAll());
    }

    @Transactional
    public PartnerDto savePartner(CreatePartnerDto createPartnerDto) {
        final var partner = partnerMapper.createDtoToEntity(createPartnerDto);
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

    public StreamingResponseBody exportPartners() {
        StreamingResponseBody stream = out -> {
            try (CSVPrinter printer = new CSVPrinter(new OutputStreamWriter(out), CSVFormat.DEFAULT.withHeader(
                    "Partner ID", "Name", "Email", "Phone", "Address ID", "Street", "City", "Zip Code", "Number"))) {
                final List<PartnerDto> partners = partnerMapper.entitiesToDtos(partnerRepository.findAll());
                for (PartnerDto partner : partners) {
                    for (AddressDto address : partner.addresses()) {
                        printer.printRecord(
                                partner.id(),
                                partner.name(),
                                partner.email(),
                                partner.phone(),
                                address.id(),
                                address.street(),
                                address.city(),
                                address.zipCode(),
                                address.number()
                        );
                    }
                }
                printer.flush();

            } catch (IOException e) {
                log.error(e.getMessage());
            }
        };

        return stream;
    }

    public List<PartnerDto> searchPartners(String name, String email, String phone, String street, String
            city, String zipCode, String number) {
        Specification<Partner> spec = Specification
                .where(PartnerSpecification.hasNameContaining(name))
                .and(PartnerSpecification.hasEmailContaining(email))
                .and(PartnerSpecification.hasPhoneContaining(phone))
                .and(PartnerSpecification.hasAddressContaining(street, city, zipCode, number));

        return partnerMapper.entitiesToDtos(partnerRepository.findAll(spec));
    }
}