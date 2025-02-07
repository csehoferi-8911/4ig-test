package hu.fourig.demo.controller;

import hu.fourig.demo.data.CreatePartnerDto;
import hu.fourig.demo.data.PartnerDto;
import hu.fourig.demo.service.PartnerService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import java.util.List;

@RestController
@RequestMapping("/api/partners")
@RequiredArgsConstructor
public class PartnerController {
    private final PartnerService partnerService;

    @Operation(description = "Összes partner lekérdezés")
    @GetMapping
    public ResponseEntity<List<PartnerDto>> getAllPartners() {
        return ResponseEntity.ok(partnerService.getAllPartners());
    }

    @Operation(description = "Partner létrehozás")
    @PostMapping
    public ResponseEntity<PartnerDto> createPartner(@RequestBody CreatePartnerDto partner) {
        return ResponseEntity.ok(partnerService.savePartner(partner));
    }

    @Operation(description = "Partner adatok frissítés")
    @PutMapping
    public ResponseEntity<PartnerDto> updatePartner(@RequestBody PartnerDto partner) {
        return ResponseEntity.ok(partnerService.updatePartner(partner));
    }

    @Operation(description = "Partner törlés id alapján")
    @DeleteMapping("/{id}")
    public void deletePartner(@PathVariable Long id) {
        partnerService.deletePartner(id);
    }

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

    @GetMapping("/export")
    public ResponseEntity<StreamingResponseBody> exportPartners() {
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"partners.csv\"")
                .contentType(org.springframework.http.MediaType.parseMediaType("text/csv"))
                .body(partnerService.exportPartners());
    }
}
