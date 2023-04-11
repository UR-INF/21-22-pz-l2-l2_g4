package com.example.hurtownia.domain.order;

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
    private OrderRepository orderRepository;

    @Autowired
    private OrderMapper mapper;

    /**
     * Pobiera wszystkie zamówienia.
     *
     * @return lista wszystkich zamówień
     */
    public List<OrderTableViewDTO> findAll() {
        return orderRepository.findAll().stream()
                .map(order -> mapper.toDTO(order))
                .collect(Collectors.toList());
    }

    /**
     * Pobiera zamówienie o podanym id.
     *
     * @param id identyfikator zamówienia
     * @return zamówienie
     */
    public Order findById(Long id) {
        Order order = orderRepository.findById(id).orElseThrow(() -> new ObjectNotFoundException(id, "Nie znaleziono zamówienia"));
        return order;
    }

    /**
     * Usuwa zamówienie.
     *
     * @param orderTableViewDTO usuwane zamówienie
     * @return true - jeśli pomyślnie usunięto;
     * false - jeśli wystąpiły błędy
     */
    public boolean delete(OrderTableViewDTO orderTableViewDTO) {
        try {
            orderRepository.delete(mapper.toEntity(orderTableViewDTO));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Dodaje nowe zamowienie.
     *
     * @param orderCreateDTO nowe zamówienie
     */
    public void save(OrderCreateDTO orderCreateDTO) {
        orderRepository.save(mapper.toEntity(orderCreateDTO));
    }

    /**
     * Aktualizuje zamówienie.
     *
     * @param orderTableViewDTO aktualizowane zamówienie
     */
    public void update(OrderTableViewDTO orderTableViewDTO) {
        orderRepository.save(mapper.toEntity(orderTableViewDTO));
    }

    /**
     * Zwraca obiekt OrderInvoiceDTO z danymi do wygenerowania faktury.
     *
     * @param orderTableViewDTO
     * @return obiekt OrderInvoiceDTO
     */
    public OrderInvoiceDTO getInvoiceData(OrderTableViewDTO orderTableViewDTO) {
        return mapper.toDTO(orderTableViewDTO);
    }
}
