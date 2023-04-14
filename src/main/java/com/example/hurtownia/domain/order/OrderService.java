package com.example.hurtownia.domain.order;

import com.example.hurtownia.domain.customer.Customer;
import com.example.hurtownia.domain.customer.CustomerService;
import com.example.hurtownia.domain.order.request.OrderCreateRequest;
import com.example.hurtownia.domain.order.request.OrderUpdateRequest;
import com.example.hurtownia.domain.orderitem.OrderItem;
import com.example.hurtownia.domain.orderitem.OrderItemService;
import org.hibernate.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Zawiera metody dla tabeli 'zamowienie'.
 */
@Service
public class OrderService {

    @Autowired
    public OrderItemService orderItemService;
    @Autowired
    public CustomerService customerService;
    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private OrderRepository orderRepository;

    /**
     * Pobiera wszystkie zamówienia.
     *
     * @return lista wszystkich zamówień
     */
    public List<OrderDTO> findAll() {
        return orderRepository.findAll().stream()
                .map(order -> {
                    List<OrderItem> orderItems = orderItemService.findAllByOrderId(order.getId());
                    return orderMapper.mapToDto(order, calculateValue(orderItems));
                }).collect(Collectors.toList());
    }

    private double calculateValue(List<OrderItem> orderItems) {
        double value = 0.0;
        for (OrderItem orderItem : orderItems) value += orderItem.getPricePerUnit() * orderItem.getAmount();
        value = Math.round(value * 100.0) / 100.0;
        return value;
    }

    /**
     * Pobiera zamówienie o podanym id.
     *
     * @param id identyfikator zamówienia
     * @return zamówienie
     */
    public Order findById(Long id) {
        return orderRepository.findById(id).orElseThrow(() -> new ObjectNotFoundException(id, "Nie znaleziono zamówienia"));
    }

    /**
     * Usuwa zamówienie.
     *
     * @param id identyfikator usuwanego zamówienia
     * @return true - jeśli pomyślnie usunięto;
     * false - jeśli wystąpiły błędy
     */
    public boolean delete(Long id) {
        try {
            orderRepository.delete(findById(id));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Dodaje nowe zamowienie.
     *
     * @param orderCreateRequest nowe zamówienie
     */
    public Order create(OrderCreateRequest orderCreateRequest) {
        Customer customer = customerService.findById(orderCreateRequest.getCustomerId());
        Order order = Order.builder()
                .customer(customer)
                .date(orderCreateRequest.getDate())
                .state(orderCreateRequest.getState())
                .discount(orderCreateRequest.getDiscount())
                .build();
        return orderRepository.save(order);
    }

    /**
     * Aktualizuje zamówienie.
     *
     * @param orderUpdateRequest aktualizowane zamówienie
     */
    public Order update(OrderUpdateRequest orderUpdateRequest) {
        Order order = findById(orderUpdateRequest.getId());
        Customer customer = customerService.findById(orderUpdateRequest.getCustomerId());
        order.setCustomer(customer);
        order.setDate(orderUpdateRequest.getDate());
        order.setState(orderUpdateRequest.getState());
        order.setDiscount(orderUpdateRequest.getDiscount());
        return orderRepository.save(order);
    }

    /**
     * Aktualizuje zamówienie.
     *
     * @param id identyfikator zamówienia, z którego ma  być wygenerowana faktura
     */
    public InvoiceData getInvoiceData(Long id) {
        Order order = findById(id);
        List<OrderItem> orderItems = orderItemService.findAllByOrderId(id);
        InvoiceData invoiceData = InvoiceData.builder()
                .name(order.getCustomer().getName())
                .surname(order.getCustomer().getSurname())
                .date(order.getDate())
                .value(String.valueOf(calculateValue(orderItems)))
                .discount(String.valueOf(order.getDiscount()))
                .valueAfterDiscount(calculateValueAfterDiscount(order, orderItems))
                .build();
        return invoiceData;
    }

    private String calculateValueAfterDiscount(Order order, List<OrderItem> orderItems) {
        double value = calculateValue(orderItems);
        double valueAfterDiscount = value - (value * order.getDiscount());
        return String.valueOf(valueAfterDiscount);
    }
}
