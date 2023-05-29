package com.example.hurtownia.domain.supplier;

import com.example.hurtownia.domain.supplier.request.SupplierCreateRequest;
import com.example.hurtownia.domain.supplier.request.SupplierUpdateRequest;
import org.hibernate.ObjectNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SupplierServiceTest {

    static Supplier supplier;
    static SupplierDTO supplierDTO;
    static List<Supplier> listOfSupplier;
    static List<SupplierDTO> listOfSupplierDTO;
    static SupplierCreateRequest supplierCreateRequest;
    static SupplierUpdateRequest supplierUpdateRequest;
    @Mock
    SupplierMapper supplierMapper;
    @Mock
    SupplierRepository supplierRepository;
    @InjectMocks
    @Spy
    SupplierService supplierService;

    @BeforeEach
    void setUp() {
        supplier = Supplier.builder()
                .id(1L)
                .email("email@email.com")
                .country("Polska")
                .place("Rzeszów")
                .street("Rejtana")
                .name("Dostawca X")
                .nip("83728932")
                .build();

        supplierDTO = SupplierDTO.builder()
                .id(1L)
                .email("email@email.com")
                .country("Polska")
                .place("Rzeszów")
                .street("Rejtana")
                .name("Dostawca X")
                .nip("83728932")
                .build();

        supplierCreateRequest = SupplierCreateRequest.builder()
                .email("email@email.com")
                .country("Polska")
                .place("Rzeszów")
                .street("Rejtana")
                .name("Dostawca X")
                .nip("83728932")
                .build();

        supplierUpdateRequest = SupplierUpdateRequest.builder()
                .id(2L)
                .email("email2@email.com")
                .country("Chiny")
                .place("Pekin")
                .street("Street")
                .name("Dostawca Y")
                .nip("93728932")
                .build();

        listOfSupplier = List.of(supplier);
        listOfSupplierDTO = List.of(supplierDTO);
    }

    @Test
    void findAll() {
        when(supplierRepository.findAll()).thenReturn(listOfSupplier);
        when(supplierMapper.mapListToDto(listOfSupplier)).thenReturn(listOfSupplierDTO);
        assertThat(supplierService.findAll()).isEqualTo(listOfSupplierDTO);
    }

    @Test
    void findById() {
        when(supplierRepository.findById(any())).thenReturn(Optional.empty());
        assertThatThrownBy(() -> supplierService.findById(any())).isInstanceOf(ObjectNotFoundException.class);

        when(supplierRepository.findById(any())).thenReturn(Optional.of(supplier));
        assertThat(supplierService.findById(any())).isEqualTo(supplier);
    }

    @Test
    void create() {
        when(supplierRepository.save(any())).thenAnswer(i -> i.getArgument(0));
        assertThat(supplierService.create(supplierCreateRequest))
                .satisfies(newSupplier -> {
                    assertThat(newSupplier.getEmail()).isEqualTo(supplierCreateRequest.getEmail());
                    assertThat(newSupplier.getCountry()).isEqualTo(supplierCreateRequest.getCountry());
                    assertThat(newSupplier.getPlace()).isEqualTo(supplierCreateRequest.getPlace());
                    assertThat(newSupplier.getStreet()).isEqualTo(supplierCreateRequest.getStreet());
                    assertThat(newSupplier.getName()).isEqualTo(supplierCreateRequest.getName());
                    assertThat(newSupplier.getNip()).isEqualTo(supplierCreateRequest.getNip());
                });
    }

    @Test
    void update() {
        when(supplierRepository.findById(any())).thenReturn(Optional.ofNullable(supplier));
        when(supplierRepository.save(any())).thenAnswer(i -> i.getArgument(0));
        assertThat(supplierService.update(supplierUpdateRequest))
                .satisfies(newSupplier -> {
                    assertThat(newSupplier.getEmail()).isEqualTo(supplierUpdateRequest.getEmail());
                    assertThat(newSupplier.getCountry()).isEqualTo(supplierUpdateRequest.getCountry());
                    assertThat(newSupplier.getPlace()).isEqualTo(supplierUpdateRequest.getPlace());
                    assertThat(newSupplier.getStreet()).isEqualTo(supplierUpdateRequest.getStreet());
                    assertThat(newSupplier.getName()).isEqualTo(supplierUpdateRequest.getName());
                    assertThat(newSupplier.getNip()).isEqualTo(supplierUpdateRequest.getNip());
                });
    }
}