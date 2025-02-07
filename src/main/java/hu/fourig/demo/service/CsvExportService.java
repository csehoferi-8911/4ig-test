package hu.fourig.demo.service;

import hu.fourig.demo.data.AddressDto;
import hu.fourig.demo.data.PartnerDto;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.springframework.stereotype.Service;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

@Service
public class CsvExportService {

    public void exportPartnersToCsv(List<PartnerDto> partners, String filePath) throws IOException {
        try (CSVPrinter printer = new CSVPrinter(new FileWriter(filePath), CSVFormat.DEFAULT.withHeader(
                "Partner ID", "Name", "Email", "Phone", "Address ID", "Street", "City", "Zip Code", "Number"))) {

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
        }
    }
}
