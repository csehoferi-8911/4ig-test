package hu.fourig.demo.controller;

import hu.fourig.demo.data.CreatePartnerDto;
import hu.fourig.demo.data.PartnerDto;
import hu.fourig.demo.service.CsvExportService;
import hu.fourig.demo.service.PartnerService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import java.util.List;

@RestController
@RequestMapping("/api/partners")
@RequiredArgsConstructor
public class PartnerController {
    private final PartnerService partnerService;
    private final CsvExportService csvExportService;

    @Operation(summary = "Összes partner lekérdezés")
    @GetMapping
    public ResponseEntity<List<PartnerDto>> getAllPartners() {
        return ResponseEntity.ok(partnerService.getAllPartners());
    }

    @Operation(summary = "Partner létrehozás")
    @PostMapping
    public ResponseEntity<PartnerDto> createPartner(@RequestBody CreatePartnerDto partner) {
        return ResponseEntity.ok(partnerService.savePartner(partner));
    }

    @Operation(summary = "Partner adatok frissítés")
    @PutMapping
    public ResponseEntity<PartnerDto> updatePartner(@RequestBody PartnerDto partner) {
        return ResponseEntity.ok(partnerService.updatePartner(partner));
    }

    @Operation(summary = "Partner törlés id alapján")
    @DeleteMapping("/{id}")
    public void deletePartner(@PathVariable Long id) {
        partnerService.deletePartner(id);
    }

    @Operation(summary = "Partner keresés partner és cím adatok alapján")
    @GetMapping("/search")
    public ResponseEntity<List<PartnerDto>> searchPartners(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String email,
            @RequestParam(required = false) String phone,
            @RequestParam(required = false) String street,
            @RequestParam(required = false) String city,
            @RequestParam(required = false) String zipCode,
            @RequestParam(required = false) String number) {

        List<PartnerDto> partners = partnerService.searchPartners(name, email, phone, street, city, zipCode, number);
        return ResponseEntity.ok(partners);
    }

    @Operation(summary = "Partner partner és cím adatok export")
    @GetMapping("/export")
    public ResponseEntity<StreamingResponseBody> exportPartners() {
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"partners.csv\"")
                .contentType(org.springframework.http.MediaType.parseMediaType("text/csv"))
                .body(csvExportService.exportPartners());
    }

    @Operation(summary = "Partner partner és cím adatok import")
    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> uploadCsv(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            return ResponseEntity.badRequest().body("Empty file!");
        }

        csvExportService.importCsv(file);
        return ResponseEntity.ok("Partners and addresses import from csv is done!");
    }
}
