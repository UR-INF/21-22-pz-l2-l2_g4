package com.example.hurtownia.domain.order;

import com.example.hurtownia.domain.customer.Customer;
import com.example.hurtownia.domain.customer.CustomerService;
import com.example.hurtownia.domain.order.request.OrderCreateRequest;
import com.example.hurtownia.domain.order.request.OrderUpdateRequest;
import com.example.hurtownia.domain.orderitem.OrderItem;
import com.example.hurtownia.domain.orderitem.OrderItemService;
import com.example.hurtownia.domain.product.Product;
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
class OrderServiceTest {

    static Order order;
    static OrderDTO orderDTO;
    static List<Order> listOfOrder;
    static List<OrderDTO> listOfOrderDTO;
    static OrderItem orderItem;
    static List<OrderItem> listOfOrderItem;
    static Customer customer;
    static OrderCreateRequest orderCreateRequest;
    static OrderUpdateRequest orderUpdateRequest;
    static Product product;
    static Supplier supplier;
    @Mock
    OrderItemService orderItemService;
    @Mock
    CustomerService customerService;
    @Mock
    OrderMapper orderMapper;
    @Mock
    OrderRepository orderRepository;
    @InjectMocks
    @Spy
    OrderService orderService;

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


        orderDTO = OrderDTO.builder()
                .id(1L)
                .customerId(1L)
                .build();

        orderCreateRequest = OrderCreateRequest.builder()
                .customerId(1L)
                .date("21.01.2023")
                .state("State")
                .discount(12.5)
                .build();

        orderUpdateRequest = OrderUpdateRequest.builder()
                .customerId(2L)
                .date("23.01.2023")
                .state("States")
                .discount(22.5)
                .build();

        listOfOrder = List.of(order);
        listOfOrderDTO = List.of(orderDTO);

        orderItem = OrderItem.builder()
                .order(order)
                .amount(100)
                .product(product)
                .pricePerUnit(1.00)
                .build();

        listOfOrderItem = List.of(orderItem);
    }

    @Test
    void findAll() {
        when(orderRepository.findAll()).thenReturn(listOfOrder);
        when(orderItemService.findAllByOrderId(any())).thenReturn(listOfOrderItem);
        when(orderMapper.mapToDto(order, listOfOrderItem.get(0).getPricePerUnit() * listOfOrderItem.get(0).getAmount())).thenReturn(orderDTO);
        assertThat(orderService.findAll()).isEqualTo(listOfOrderDTO);
    }

    @Test
    void findById() {
        when(orderRepository.findById(any())).thenReturn(Optional.empty());
        assertThatThrownBy(() -> orderService.findById(any())).isInstanceOf(ObjectNotFoundException.class);

        when(orderRepository.findById(any())).thenReturn(Optional.of(order));
        assertThat(orderService.findById(any())).isEqualTo(order);
    }

    @Test
    void delete() {
    }

    @Test
    void create() {
        when(customerService.findById(any())).thenReturn(customer);
        when(orderRepository.save(any())).thenAnswer(i -> i.getArgument(0));
        assertThat(orderService.create(orderCreateRequest))
                .satisfies(newOrder -> {
                    assertThat(newOrder.getDate()).isEqualTo(orderCreateRequest.getDate());
                    assertThat(newOrder.getDiscount()).isEqualTo(orderCreateRequest.getDiscount());
                    assertThat(newOrder.getState()).isEqualTo(orderCreateRequest.getState());
                    assertThat(newOrder.getCustomer().getId()).isEqualTo(orderCreateRequest.getCustomerId());
                });
    }

    @Test
    void update() {
        when(orderRepository.findById(any())).thenReturn(Optional.ofNullable(order));
        when(customerService.findById(any())).thenReturn(customer);
        when(orderRepository.save(any())).thenAnswer(i -> i.getArgument(0));
        assertThat(orderService.update(orderUpdateRequest))
                .satisfies(newOrder -> {
                    assertThat(newOrder.getDate()).isEqualTo(orderUpdateRequest.getDate());
                    assertThat(newOrder.getDiscount()).isEqualTo(orderUpdateRequest.getDiscount());
                    assertThat(newOrder.getState()).isEqualTo(orderUpdateRequest.getState());
                    assertThat(newOrder.getCustomer().getId()).isEqualTo(orderCreateRequest.getCustomerId());
                });
    }

    @Test
    void getInvoiceData() {
        when(orderRepository.findById(any())).thenReturn(Optional.ofNullable(order));
        when(orderItemService.findAllByOrderId(any())).thenReturn(listOfOrderItem);
        var calculateValue = listOfOrderItem.get(0).getPricePerUnit() * listOfOrderItem.get(0).getAmount();
        var calculateValueAfterDiscount = calculateValue - (calculateValue * order.getDiscount());
        assertThat(orderService.getInvoiceData(1L))
                .satisfies(invoiceData -> {
                    assertThat(invoiceData.getName()).isEqualTo(order.getCustomer().getName());
                    assertThat(invoiceData.getSurname()).isEqualTo(order.getCustomer().getSurname());
                    assertThat(invoiceData.getPlace()).isEqualTo(order.getCustomer().getPlace());
                    assertThat(invoiceData.getStreet()).isEqualTo(order.getCustomer().getStreet());
                    assertThat(invoiceData.getBuildingNumber()).isEqualTo(order.getCustomer().getBuildingNumber());
                    assertThat(invoiceData.getApartmentNumber()).isEqualTo(order.getCustomer().getApartmentNumber());
                    assertThat(invoiceData.getPhoneNumber()).isEqualTo(order.getCustomer().getPhoneNumber());
                    assertThat(invoiceData.getDate()).isEqualTo(order.getDate());
                    assertThat(invoiceData.getValue()).isEqualTo(String.valueOf(calculateValue));
                    assertThat(invoiceData.getDiscount()).isEqualTo(DiscountConverter.fromNumericToPercentage(order.getDiscount()));
                    assertThat(invoiceData.getValueAfterDiscount()).isEqualTo(String.valueOf(calculateValueAfterDiscount));
                });
    }
}