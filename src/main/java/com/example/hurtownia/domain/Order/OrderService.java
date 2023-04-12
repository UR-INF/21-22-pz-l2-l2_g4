package com.example.hurtownia.domain.order;

import com.example.hurtownia.domain.customer.Customer;
import com.example.hurtownia.domain.customer.CustomerService;
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
    private OrderMapper mapper;
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
                    return mapper.mapToDto(order, orderItems);
                }).collect(Collectors.toList());
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
    public Order save(OrderCreateRequest orderCreateRequest) {
        Customer customer = customerService.findById(orderCreateRequest.getCustomerId());
        return orderRepository.save(mapper.mapToEntity(orderCreateRequest, customer));
    }

    /**
     * Aktualizuje zamówienie.
     *
     * @param orderUpdateRequest aktualizowane zamówienie
     */
    public Order update(OrderUpdateRequest orderUpdateRequest) {
        Customer customer = customerService.findById(orderUpdateRequest.getCustomerId());
        return orderRepository.save(mapper.mapToEntity(orderUpdateRequest, customer));
    }

    /**
     * Aktualizuje zamówienie.
     *
     * @param id identyfikator zamówienia, z którego ma  być wygenerowana faktura
     */
    public InvoiceData getInvoiceData(Long id) {
        Order order = findById(id);
        List<OrderItem> orderItems = orderItemService.findAllByOrderId(id);
        return mapper.mapToInvoiceData(order, orderItems);
    }
}
