package hu.fourig.demo.service;

import hu.fourig.demo.data.CreatePartnerDto;
import hu.fourig.demo.data.PartnerDto;
import hu.fourig.demo.mapper.PartnerMapper;
import hu.fourig.demo.model.Partner;
import hu.fourig.demo.repository.PartnerRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PartnerServiceTest {

    @Mock
    private PartnerRepository partnerRepository;

    @Mock
    private PartnerMapper partnerMapper;

    @InjectMocks
    private PartnerService partnerService;

    private Partner partner;
    private PartnerDto partnerDto;
    private CreatePartnerDto createPartnerDto;

    @BeforeEach
    void setUp() {
        partner = new Partner();
        partner.setId(1L);
        partner.setName("Test Partner");

        partnerDto = PartnerDto.builder()
                .id(1L)
                .name("Test Partner")
                .email("test@example.com")
                .phone("123456789")
                .addresses(List.of()).build();

        createPartnerDto = CreatePartnerDto.builder()
                .name("Test Partner")
                .email("test@example.com")
                .phone("123456789").build();
    }

    @Test
    void testGetAllPartners() {
        when(partnerRepository.findAll()).thenReturn(List.of(partner));
        when(partnerMapper.entitiesToDtos(any())).thenReturn(List.of(partnerDto));

        final var result = partnerService.getAllPartners();

        assertEquals(1, result.size());
        assertEquals("Test Partner", result.getFirst().name());
        verify(partnerRepository, times(1)).findAll();
    }

    @Test
    void testSavePartner() {
        when(partnerMapper.createDtoToEntity(any())).thenReturn(partner);
        when(partnerRepository.save(any())).thenReturn(partner);
        when(partnerMapper.entityToDto(any())).thenReturn(partnerDto);

        final var result = partnerService.savePartner(createPartnerDto);

        assertNotNull(result);
        assertEquals("Test Partner", result.name());
        verify(partnerRepository, times(1)).save(any());
    }

    @Test
    void testUpdatePartner_Success() {
        when(partnerRepository.findById(1L)).thenReturn(Optional.of(partner));
        when(partnerMapper.dtoToEntity(any())).thenReturn(partner);
        when(partnerRepository.save(any())).thenReturn(partner);
        when(partnerMapper.entityToDto(any())).thenReturn(partnerDto);

        final var result = partnerService.updatePartner(partnerDto);

        assertNotNull(result);
        assertEquals(1L, result.id());
        verify(partnerRepository, times(1)).findById(1L);
        verify(partnerRepository, times(1)).save(any());
    }

    @Test
    void testUpdatePartner_NotFound() {
        when(partnerRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> partnerService.updatePartner(partnerDto));

        verify(partnerRepository, times(1)).findById(1L);
        verify(partnerRepository, never()).save(any());
    }

    @Test
    void testDeletePartner() {
        doNothing().when(partnerRepository).deleteById(1L);

        partnerService.deletePartner(1L);

        verify(partnerRepository, times(1)).deleteById(1L);
    }

    @Test
    void testSearchPartners() {
        when(partnerRepository.findAll(any(Specification.class))).thenReturn(List.of(partner));
        when(partnerMapper.entitiesToDtos(any())).thenReturn(List.of(partnerDto));

        final var result = partnerService.searchPartners("Test", null, null, null, null, null, null);

        assertEquals(1, result.size());
        assertEquals("Test Partner", result.getFirst().name());
        verify(partnerRepository, times(1)).findAll(any(Specification.class));
    }
}
