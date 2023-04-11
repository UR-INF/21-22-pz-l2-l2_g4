package com.example.hurtownia.domain.orderitem;

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
    private OrderItemMapper mapper;

    /**
     * Pobiera elementy zamówień.
     *
     * @return lista wszystkich elementów zamówień
     */
    public List<OrderItemTableViewDTO> findAll() {
        return orderItemRepository.findAll().stream()
                .map(orderItem -> mapper.toDTO(orderItem))
                .collect(Collectors.toList());
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
     * Usuwa element zamówienia.
     *
     * @param orderItemTableViewDTO usuwany element zamówienia
     * @return true - jeśli pomyślnie usunięto;
     * false - jeśli wystąpiły błędy
     */
    public boolean delete(OrderItemTableViewDTO orderItemTableViewDTO) {
        try {
            orderItemRepository.delete(mapper.toEntity(orderItemTableViewDTO));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Dodaje nowy element zamówienia.
     *
     * @param orderItemCreateDTO nowy element zamówienia
     */
    public void save(OrderItemCreateDTO orderItemCreateDTO) {
        orderItemRepository.save(mapper.toEntity(orderItemCreateDTO));
    }

    /**
     * Aktualizuje element zamówienia.
     *
     * @param orderItemTableViewDTO aktualizowany element zamówienia
     */
    public void update(OrderItemTableViewDTO orderItemTableViewDTO) {
        orderItemRepository.save(mapper.toEntity(orderItemTableViewDTO));
    }
}
