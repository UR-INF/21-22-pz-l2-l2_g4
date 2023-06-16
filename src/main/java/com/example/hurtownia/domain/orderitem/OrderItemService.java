package com.example.hurtownia.domain.orderitem;

import com.example.hurtownia.domain.order.Order;
import com.example.hurtownia.domain.order.OrderService;
import com.example.hurtownia.domain.orderitem.request.OrderItemCreateRequest;
import com.example.hurtownia.domain.orderitem.request.OrderItemUpdateRequest;
import com.example.hurtownia.domain.product.Product;
import com.example.hurtownia.domain.product.ProductService;
import com.example.hurtownia.domain.product.request.ProductUpdateRequest;
import org.apache.commons.beanutils.BeanUtils;
import org.hibernate.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

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
            OrderItem orderItem = findById(id);
            if (orderItem.getOrder().getState().equals("w przygotowaniu")) {
                Product product = productService.findById(orderItem.getProduct().getId());
                ProductUpdateRequest productUpdateRequest = new ProductUpdateRequest();
                BeanUtils.copyProperties(productUpdateRequest, product);
                productUpdateRequest.setSupplierId(product.getSupplier().getId());
                productUpdateRequest.setNumber(orderItem.getAmount() + product.getNumber());
                productService.update(productUpdateRequest);
            }
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
    public OrderItem create(OrderItemCreateRequest orderItemCreateRequest) throws InvocationTargetException, IllegalAccessException {
        Order order = orderService.findById(orderItemCreateRequest.getOrderId());
        Product product = productService.findById(orderItemCreateRequest.getProductId());
        ProductUpdateRequest productUpdateRequest = new ProductUpdateRequest();
        BeanUtils.copyProperties(productUpdateRequest, product);
        if (product.getNumber() > orderItemCreateRequest.getAmount()) {
            productUpdateRequest.setNumber(product.getNumber() - orderItemCreateRequest.getAmount());
        } else {
            orderItemCreateRequest.setAmount(product.getNumber());
            productUpdateRequest.setNumber(0);
        }
        productUpdateRequest.setSupplierId(product.getSupplier().getId());
        productService.update(productUpdateRequest);
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
    public OrderItem update(OrderItemUpdateRequest orderItemUpdateRequest) throws InvocationTargetException, IllegalAccessException {
        OrderItem oldOrderItem = findById(orderItemUpdateRequest.getId());
        Order order = orderService.findById(orderItemUpdateRequest.getOrderId());
        Product product = productService.findById(orderItemUpdateRequest.getProductId());
        ProductUpdateRequest productUpdateRequest = new ProductUpdateRequest();
        BeanUtils.copyProperties(productUpdateRequest, product);
        if (order.getState().equals("w przygotowaniu")) {
            if (product.getId().equals(oldOrderItem.getProduct().getId())) {
                int amount = Math.abs(oldOrderItem.getAmount() - orderItemUpdateRequest.getAmount());
                if (oldOrderItem.getAmount() - orderItemUpdateRequest.getAmount() >= 0) {
                    productUpdateRequest.setNumber(product.getNumber() + amount);
                } else {
                    productUpdateRequest.setNumber(product.getNumber() - amount);
                }
                productUpdateRequest.setSupplierId(product.getSupplier().getId());
                productService.update(productUpdateRequest);
            } else {
                Product oldProduct = oldOrderItem.getProduct();
                BeanUtils.copyProperties(productUpdateRequest, product);
                productUpdateRequest.setSupplierId(product.getSupplier().getId());
                productUpdateRequest.setNumber(product.getNumber() - orderItemUpdateRequest.getAmount());
                productService.update(productUpdateRequest);
                BeanUtils.copyProperties(productUpdateRequest, oldProduct);
                productUpdateRequest.setNumber(oldProduct.getNumber() + oldOrderItem.getAmount());
                productUpdateRequest.setSupplierId(oldProduct.getSupplier().getId());
                productService.update(productUpdateRequest);
            }
        }
        OrderItem orderItem = findById(orderItemUpdateRequest.getId());
        orderItem.setOrder(order);
        orderItem.setProduct(product);
        orderItem.setAmount(orderItemUpdateRequest.getAmount());
        orderItem.setPricePerUnit(product.getPrice());
        return orderItemRepository.save(orderItem);
    }
}
