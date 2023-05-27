package com.example.hurtownia.domain.orderitem;

import com.example.hurtownia.domain.order.Order;
import com.example.hurtownia.domain.order.OrderService;
import com.example.hurtownia.domain.orderitem.request.OrderItemCreateRequest;
import com.example.hurtownia.domain.orderitem.request.OrderItemUpdateRequest;
import com.example.hurtownia.domain.product.Product;
import com.example.hurtownia.domain.product.ProductService;
import org.hibernate.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Zawiera metody dla tabeli 'element_zamowienia'.
 */
@Service
public class OrderItemService {

    @Autowired
    private OrderItemRepository orderItemRepository;
    @Autowired
    private OrderService orderService;
    @Autowired
    private ProductService productService;
    @Autowired
    private OrderItemMapper orderItemMapper;

    /**
     * Pobiera elementy zamówień.
     *
     * @return lista wszystkich elementów zamówień
     */
    public List<OrderItemDTO> findAll() {
        return orderItemMapper.mapListToDto(orderItemRepository.findAll());
    }

    /**
     * Pobiera wszystkie elementy konkretnego zamówienia. Używana przy obliczaniu wartości całego zamówienia.
     *
     * @param id identyfikator zamówienia
     * @return lista elementów zamówienia o podanym id
     */
    public List<OrderItem> findAllByOrderId(Long id) {
        return orderItemRepository.findAllByOrderId(id);
    }

    /**
     * Pobiera element zamówienia o konkretnym id.
     *
     * @param id identyfikator zamówienia
     * @return element zamówienia
     */
    public OrderItem findById(Long id) {
        return orderItemRepository.findById(id).orElseThrow(() -> new ObjectNotFoundException(id, "Nie znaleziono elementu zamówienia"));
    }

    /**
     * Usuwa element zamówienia.
     *
     * @param id identyfikator usuwanego zamówienia
     * @return true - jeśli pomyślnie usunięto;
     * false - jeśli wystąpiły błędy
     */
    public boolean delete(Long id) {
        try {
            orderItemRepository.delete(findById(id));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Dodaje nowy element zamówienia.
     *
     * @param orderItemCreateRequest nowy element zamówienia
     */
    public OrderItem create(OrderItemCreateRequest orderItemCreateRequest) {
        Order order = orderService.findById(orderItemCreateRequest.getOrderId());
        Product product = productService.findById(orderItemCreateRequest.getProductId());
        OrderItem orderItem = OrderItem.builder()
                .order(order)
                .product(product)
                .amount(orderItemCreateRequest.getAmount())
                .pricePerUnit(product.getPrice())
                .build();
        return orderItemRepository.save(orderItem);
    }

    /**
     * Aktualizuje element zamówienia.
     *
     * @param orderItemUpdateRequest aktualizowany element zamówienia
     */
    public OrderItem update(OrderItemUpdateRequest orderItemUpdateRequest) {
        Order order = orderService.findById(orderItemUpdateRequest.getOrderId());
        Product product = productService.findById(orderItemUpdateRequest.getProductId());
        OrderItem orderItem = findById(orderItemUpdateRequest.getId());
        orderItem.setOrder(order);
        orderItem.setProduct(product);
        orderItem.setAmount(orderItemUpdateRequest.getAmount());
        orderItem.setPricePerUnit(product.getPrice());
        return orderItemRepository.save(orderItem);
    }
}
