package hu.fourig.demo.service;

import hu.fourig.demo.data.AddressDto;
import hu.fourig.demo.data.CreateAddressDto;
import hu.fourig.demo.data.CreatePartnerDto;
import hu.fourig.demo.data.PartnerDto;
import hu.fourig.demo.mapper.PartnerMapper;
import hu.fourig.demo.repository.PartnerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class CsvExportService {

    private final PartnerMapper partnerMapper;
    private final PartnerRepository partnerRepository;

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

    @Transactional
    public void importCsv(MultipartFile file) {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream(), StandardCharsets.UTF_8));
             CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT.withFirstRecordAsHeader())) {

            for (CSVRecord record : csvParser) {
                String partnerName = record.get("Name");
                String email = record.get("Email");
                String phone = record.get("Phone");

                String street = record.get("Street");
                String city = record.get("City");
                String zipCode = record.get("Zip Code");
                String number = record.get("Number");

                if (partnerRepository.findByEmail(email).isPresent()) {
                    log.error("{} already exists", partnerName);
                } else {
                    CreatePartnerDto createPartnerDto = CreatePartnerDto.builder()
                            .name(partnerName)
                            .email(email)
                            .phone(phone)
                            .addresses(List.of(CreateAddressDto.builder()
                                    .street(street)
                                    .city(city)
                                    .zipCode(zipCode)
                                    .number(number).build())).build();
                    partnerRepository.save(partnerMapper.createDtoToEntity(createPartnerDto));

                }

            }
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new RuntimeException("CSV import error: " + e.getMessage());
        }
    }
}
