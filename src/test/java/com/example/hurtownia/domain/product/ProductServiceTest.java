package com.example.hurtownia.domain.product;

import com.example.hurtownia.domain.product.request.ProductCreateRequest;
import com.example.hurtownia.domain.product.request.ProductUpdateRequest;
import com.example.hurtownia.domain.supplier.Supplier;
import com.example.hurtownia.domain.supplier.SupplierService;
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
class ProductServiceTest {

    static Product product;
    static ProductDTO productDTO;
    static List<Product> listOfProduct;
    static List<ProductDTO> listOfProductDTO;
    static ProductCreateRequest productCreateRequest;
    static ProductUpdateRequest productUpdateRequest;
    static Supplier supplier;
    @Mock
    SupplierService supplierService;
    @Mock
    ProductMapper productMapper;
    @Mock
    ProductRepository productRepository;
    @InjectMocks
    @Spy
    ProductService productService;

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

        product = Product.builder()
                .id(1L)
                .supplier(supplier)
                .name("Panel podłogowy")
                .country("Polska")
                .code("9dn2nd2d")
                .color("Grey")
                .maxNumber(10000)
                .price(82.6)
                .unitOfMeasurement("m^2")
                .number(100)
                .build();

        productDTO = ProductDTO.builder()
                .id(1L)
                .supplierId(1L)
                .name("Panel podłogowy")
                .country("Polska")
                .code("9dn2nd2d")
                .color("Grey")
                .maxNumber(10000)
                .price(82.6)
                .unitOfMeasurement("m^2")
                .number(100)
                .build();

        productCreateRequest = ProductCreateRequest.builder()
                .supplierId(1L)
                .name("Panel podłogowy")
                .country("Polska")
                .code("9dn2nd2d")
                .color("Grey")
                .maxNumber(10000)
                .price(82.6)
                .unitOfMeasurement("m^2")
                .number(100)
                .build();

        productUpdateRequest = ProductUpdateRequest.builder()
                .id(2L)
                .supplierId(2L)
                .name("Płytki")
                .country("Niemcy")
                .code("1dn2nd2d")
                .color("White")
                .maxNumber(1000)
                .price(20.0)
                .unitOfMeasurement("sztuka")
                .number(200)
                .build();

        listOfProduct = List.of(product);
        listOfProductDTO = List.of(productDTO);
    }

    @Test
    void findAll() {
        when(productRepository.findAll()).thenReturn(listOfProduct);
        when(productMapper.mapListToDto(listOfProduct)).thenReturn(listOfProductDTO);
        assertThat(productService.findAll()).isEqualTo(listOfProductDTO);
    }

    @Test
    void findById() {
        when(productRepository.findById(any())).thenReturn(Optional.empty());
        assertThatThrownBy(() -> productService.findById(any())).isInstanceOf(ObjectNotFoundException.class);

        when(productRepository.findById(any())).thenReturn(Optional.of(product));
        assertThat(productService.findById(any())).isEqualTo(product);
    }

    @Test
    void create() {
        when(supplierService.findById(any())).thenReturn(supplier);
        when(productRepository.save(any())).thenAnswer(i -> i.getArgument(0));
        assertThat(productService.create(productCreateRequest))
                .satisfies(newProduct -> {
                    assertThat(newProduct.getSupplier().getId()).isEqualTo(productCreateRequest.getSupplierId());
                    assertThat(newProduct.getName()).isEqualTo(productCreateRequest.getName());
                    assertThat(newProduct.getUnitOfMeasurement()).isEqualTo(productCreateRequest.getUnitOfMeasurement());
                    assertThat(newProduct.getPrice()).isEqualTo(productCreateRequest.getPrice());
                    assertThat(newProduct.getCountry()).isEqualTo(productCreateRequest.getCountry());
                    assertThat(newProduct.getCode()).isEqualTo(productCreateRequest.getCode());
                    assertThat(newProduct.getColor()).isEqualTo(productCreateRequest.getColor());
                    assertThat(newProduct.getNumber()).isEqualTo(productCreateRequest.getNumber());
                    assertThat(newProduct.getMaxNumber()).isEqualTo(productCreateRequest.getMaxNumber());
                });
    }

    @Test
    void update() {
        when(productRepository.findById(any())).thenReturn(Optional.ofNullable(product));
        when(supplierService.findById(any())).thenReturn(supplier);
        when(productRepository.save(any())).thenAnswer(i -> i.getArgument(0));
        assertThat(productService.update(productUpdateRequest))
                .satisfies(newProduct -> {
                    assertThat(newProduct.getSupplier().getId()).isEqualTo(productCreateRequest.getSupplierId());
                    assertThat(newProduct.getName()).isEqualTo(productUpdateRequest.getName());
                    assertThat(newProduct.getUnitOfMeasurement()).isEqualTo(productUpdateRequest.getUnitOfMeasurement());
                    assertThat(newProduct.getPrice()).isEqualTo(productUpdateRequest.getPrice());
                    assertThat(newProduct.getCountry()).isEqualTo(productUpdateRequest.getCountry());
                    assertThat(newProduct.getCode()).isEqualTo(productUpdateRequest.getCode());
                    assertThat(newProduct.getColor()).isEqualTo(productUpdateRequest.getColor());
                    assertThat(newProduct.getNumber()).isEqualTo(productUpdateRequest.getNumber());
                    assertThat(newProduct.getMaxNumber()).isEqualTo(productUpdateRequest.getMaxNumber());
                });
    }

    @Test
    void getSupplyData() {
        when(productRepository.findById(any())).thenReturn(Optional.ofNullable(product));
        when(supplierService.findById(any())).thenReturn(supplier);
        var amount = product.getMaxNumber() - product.getNumber();
        assertThat(productService.getSupplyData(List.of(1L)))
                .satisfies(list -> {
                    assertThat(list.get(0).getSupplierName()).isEqualTo(supplier.getName());
                    assertThat(list.get(0).getProductCode()).isEqualTo(product.getCode());
                    assertThat(list.get(0).getProductUnitOfMeasurement()).isEqualTo(product.getUnitOfMeasurement());
                    assertThat(list.get(0).getAmount()).isEqualTo(String.valueOf(amount));
                });
    }
}