package hu.fourig.demo.controller;

import hu.fourig.demo.data.CreatePartnerDto;
import hu.fourig.demo.data.PartnerDto;
import hu.fourig.demo.service.PartnerService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/partners")
@RequiredArgsConstructor
public class PartnerController {
    private final PartnerService partnerService;

    @GetMapping
    public List<PartnerDto> getAllPartners() {
        return partnerService.getAllPartners();
    }

    @PostMapping
    public PartnerDto createPartner(@RequestBody CreatePartnerDto partner) {
        return partnerService.savePartner(partner);
    }

    @DeleteMapping("/{id}")
    public void deletePartner(@PathVariable Long id) {
        partnerService.deletePartner(id);
    }
}
