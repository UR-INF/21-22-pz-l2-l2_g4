package com.example.hurtownia.domain.orderitem;

import com.example.hurtownia.domain.customer.Customer;
import com.example.hurtownia.domain.order.Order;
import com.example.hurtownia.domain.order.OrderService;
import com.example.hurtownia.domain.orderitem.request.OrderItemCreateRequest;
import com.example.hurtownia.domain.orderitem.request.OrderItemUpdateRequest;
import com.example.hurtownia.domain.product.Product;
import com.example.hurtownia.domain.product.ProductService;
import com.example.hurtownia.domain.supplier.Supplier;
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
class OrderItemServiceTest {

    static OrderItem orderItem;
    static OrderItemDTO orderItemDTO;
    static List<OrderItem> listOfOrderItem;
    static List<OrderItemDTO> listOfOrderItemDTO;
    static OrderItemCreateRequest orderItemCreateRequest;
    static OrderItemUpdateRequest orderItemUpdateRequest;
    static Customer customer;
    static Order order;
    static Product product;
    static Supplier supplier;
    @Mock
    ProductService productService;
    @Mock
    OrderService orderService;
    @Mock
    OrderItemMapper orderItemMapper;
    @Mock
    OrderItemRepository orderItemRepository;
    @InjectMocks
    @Spy
    OrderItemService orderItemService;

    @BeforeEach
    void setUp() {
        supplier = Supplier.builder()
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
                .number(12321)
                .build();

        customer = Customer.builder()
                .id(1L)
                .name("Customer")
                .surname("Surname")
                .pesel("21973812L")
                .email("email@com.pl")
                .place("Place")
                .zipCode("30-300")
                .phoneNumber("32873782")
                .apartmentNumber(1)
                .buildingNumber(1)
                .street("Street")
                .build();

        order = Order.builder()
                .id(1L)
                .customer(customer)
                .date("23.12.3233")
                .state("state")
                .discount(10.0)
                .build();

        orderItem = OrderItem.builder()
                .id(1L)
                .order(order)
                .amount(100)
                .product(product)
                .pricePerUnit(1.00)
                .build();

        orderItemDTO = OrderItemDTO.builder()
                .id(1L)
                .orderId(1L)
                .productId(1L)
                .amount(100)
                .pricePerUnit(1.00)
                .itemPrice(1.00 * 100)
                .build();

        orderItemCreateRequest = OrderItemCreateRequest.builder()
                .orderId(1L)
                .productId(1L)
                .amount(10)
                .build();

        orderItemUpdateRequest = OrderItemUpdateRequest.builder()
                .id(2L)
                .orderId(2L)
                .productId(2L)
                .amount(20)
                .itemPrice(100.00)
                .pricePerUnit(1.00)
                .build();

        listOfOrderItem = List.of(orderItem);
        listOfOrderItemDTO = List.of(orderItemDTO);
    }

    @Test
    void findAll() {
        when(orderItemRepository.findAll()).thenReturn(listOfOrderItem);
        when(orderItemMapper.mapListToDto(listOfOrderItem)).thenReturn(listOfOrderItemDTO);
        assertThat(orderItemService.findAll()).isEqualTo(listOfOrderItemDTO);
    }

    @Test
    void findAllByOrderId() {
        when(orderItemRepository.findAllByOrderId(any())).thenReturn(listOfOrderItem);
        assertThat(orderItemService.findAllByOrderId(any())).isEqualTo(listOfOrderItem);
    }

    @Test
    void findById() {
        when(orderItemRepository.findById(any())).thenReturn(Optional.empty());
        assertThatThrownBy(() -> orderItemService.findById(any())).isInstanceOf(ObjectNotFoundException.class);

        when(orderItemRepository.findById(any())).thenReturn(Optional.of(orderItem));
        assertThat(orderItemService.findById(any())).isEqualTo(orderItem);
    }

    @Test
    void delete() {
    }

    @Test
    void create() {
        when(orderService.findById(any())).thenReturn(order);
        when(productService.findById(any())).thenReturn(product);
        when(orderItemRepository.save(any())).thenAnswer(i -> i.getArgument(0));
        assertThat(orderItemService.create(orderItemCreateRequest))
                .satisfies(newOrderItem -> {
                    assertThat(newOrderItem.getOrder().getId()).isEqualTo(orderItemCreateRequest.getOrderId());
                    assertThat(newOrderItem.getProduct().getId()).isEqualTo(orderItemCreateRequest.getProductId());
                    assertThat(newOrderItem.getAmount()).isEqualTo(orderItemCreateRequest.getAmount());
                    assertThat(newOrderItem.getPricePerUnit()).isEqualTo(product.getPrice());
                });
    }

    @Test
    void update() {
        when(orderItemRepository.findById(any())).thenReturn(Optional.ofNullable(orderItem));
        when(orderService.findById(any())).thenReturn(order);
        when(productService.findById(any())).thenReturn(product);
        when(orderItemRepository.save(any())).thenAnswer(i -> i.getArgument(0));
        assertThat(orderItemService.update(orderItemUpdateRequest))
                .satisfies(newOrderItem -> {
                    assertThat(newOrderItem.getOrder().getId()).isEqualTo(orderItemCreateRequest.getOrderId());
                    assertThat(newOrderItem.getProduct().getId()).isEqualTo(orderItemCreateRequest.getProductId());
                    assertThat(newOrderItem.getAmount()).isEqualTo(orderItemUpdateRequest.getAmount());
                    assertThat(newOrderItem.getPricePerUnit()).isEqualTo(product.getPrice());
                });
    }
}