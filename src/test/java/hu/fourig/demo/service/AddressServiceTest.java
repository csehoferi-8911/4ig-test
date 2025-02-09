package hu.fourig.demo.service;

import hu.fourig.demo.data.AddressDto;
import hu.fourig.demo.mapper.AddressMapper;
import hu.fourig.demo.model.Address;
import hu.fourig.demo.repository.AddressRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AddressServiceTest {

    @Mock
    private AddressRepository addressRepository;

    @Mock
    private AddressMapper addressMapper;

    @InjectMocks
    private AddressService addressService;

    private AddressDto addressDto;

    @BeforeEach
    void setUp() {
        addressDto = AddressDto.builder()
                .id(1L)
                .street("street")
                .city("city")
                .zipCode("12345")
                .number("10")
                .build();
    }

    @Test
    void testGetAllAddresses() {
        when(addressRepository.findAll()).thenReturn(List.of());
        when(addressMapper.entitiesToDtos(any())).thenReturn(List.of(addressDto));

        final var result = addressService.getAllAddresses();

        assertEquals(1, result.size());
        assertEquals("street", result.getFirst().street());
        verify(addressRepository, times(1)).findAll();
    }

    @Test
    void testSearchAddresses() {
        Object[] row = {1L, "street", "city", "12345", "10", 1L, "John Doe", "john@example.com", "123456789"};
        when(addressRepository.searchAddressesWithPartners(any(), any(), any(), any())).thenReturn(Collections.singletonList(row));

        final var result = addressService.searchAddresses("street", "city", "12345", "10");

        assertEquals(1, result.size());
        assertEquals("street", result.getFirst().getStreet());
        assertEquals("John Doe", result.getFirst().getName());
        verify(addressRepository, times(1)).searchAddressesWithPartners(any(), any(), any(), any());
    }

    @Test
    void testUpdateAddress_Success() {
        when(addressRepository.findById(1L)).thenReturn(Optional.of(new Address()));
        when(addressMapper.dtoToEntity(any())).thenReturn(new Address());
        when(addressRepository.save(any())).thenReturn(new Address());
        when(addressMapper.entityToDto(any())).thenReturn(addressDto);

        final var result = addressService.updateAddress(addressDto);

        assertNotNull(result);
        assertEquals("street", result.street());
        verify(addressRepository, times(1)).findById(1L);
        verify(addressRepository, times(1)).save(any());
    }

    @Test
    void testUpdateAddress_NotFound() {
        when(addressRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> addressService.updateAddress(addressDto));
        verify(addressRepository, times(1)).findById(1L);
        verify(addressRepository, never()).save(any());
    }

    @Test
    void testDeleteAddress() {
        doNothing().when(addressRepository).deleteById(1L);

        addressService.deleteAddress(1L);

        verify(addressRepository, times(1)).deleteById(1L);
    }
}
