package com.example.hurtownia.domain.order;

import com.example.hurtownia.domain.customer.Customer;
import com.example.hurtownia.domain.customer.CustomerService;
import com.example.hurtownia.domain.orderitem.OrderItem;
import com.example.hurtownia.domain.orderitem.OrderItemService;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;

/**
 * Mapper pomiędzy Order i jego DTO.
 */
@Component
public class OrderMapper {

    public CustomerService customerService;
    public OrderItemService orderItemService;

    /**
     * Konwerter z Order na OrderTableViewDTO.
     */
    Converter<Order, OrderTableViewDTO> orderToOrderTableViewDTOConverter = context -> {
        OrderTableViewDTO orderTableViewDTO = new OrderTableViewDTO();
        List<OrderItem> orderItems = orderItemService.findAllByOrderId(context.getSource().getId());
        double price = 0.0;
        for (OrderItem orderItem : orderItems) price += orderItem.getItemPrice();
        price = Math.round(price * 100.0) / 100.0;
        orderTableViewDTO.setValue(price);
        orderTableViewDTO.setId(context.getSource().getId());
        orderTableViewDTO.setCustomerId(context.getSource().getCustomer().getId());
        orderTableViewDTO.setDate(context.getSource().getDate());
        orderTableViewDTO.setDiscount(context.getSource().getDiscount());
        orderTableViewDTO.setState(context.getSource().getState());
        return orderTableViewDTO;
    };

    /**
     * Konwerter z OrderTableViewDTO na Order.
     */
    Converter<OrderTableViewDTO, Order> orderTableViewDTOToOrderConverter = context -> {
        Order order = new Order();
        order.setId(context.getSource().getId());
        order.setCustomer(customerService.findById(context.getSource().getCustomerId()));
        order.setDate(context.getSource().getDate());
        order.setState(context.getSource().getState());
        order.setDiscount(context.getSource().getDiscount());
        return order;
    };

    /**
     * Konwerter z OrderCreateDTO na Order.
     */
    Converter<OrderCreateDTO, Order> orderCreateDTOToOrderConverter = context -> {
        Order order = new Order();
        order.setCustomer(customerService.findById(context.getSource().getCustomerId()));
        order.setDate(context.getSource().getDate());
        order.setState(context.getSource().getState());
        order.setDiscount(context.getSource().getDiscount());
        return order;
    };

    /**
     * Konwerter z OrderCreateDTO na OrderInvoiceDTO.
     */
    Converter<OrderTableViewDTO, OrderInvoiceDTO> orderTableViewDTOToOrderInvoiceDTOConverter = context -> {
        OrderInvoiceDTO orderInvoiceDTO = new OrderInvoiceDTO();
        Customer customer = customerService.findById(context.getSource().getCustomerId());
        List<OrderItem> orderItems = orderItemService.findAllByOrderId(context.getSource().getId());
        double price = 0.0;
        for (OrderItem orderItem : orderItems) price += orderItem.getItemPrice();
        price = Math.round(price * 100.0) / 100.0;
        double priceAfterDiscount = price - (price * context.getSource().getDiscount());
        orderInvoiceDTO.setName(customer.getName());
        orderInvoiceDTO.setSurname(customer.getSurname());
        orderInvoiceDTO.setDate(context.getSource().getDate());
        orderInvoiceDTO.setPrice(String.valueOf(price));
        orderInvoiceDTO.setDiscount(DiscountConverter.fromNumericToPercentage(context.getSource().getDiscount()));
        orderInvoiceDTO.setPriceAfterDiscount(String.valueOf(priceAfterDiscount));
        return orderInvoiceDTO;
    };
    private ModelMapper modelMapper;

    @Autowired
    public OrderMapper(ModelMapper modelMapper, CustomerService customerService, OrderItemService orderItemService) {
        this.modelMapper = modelMapper;
        this.customerService = customerService;
        this.orderItemService = orderItemService;

        modelMapper.createTypeMap(Order.class, OrderTableViewDTO.class).setConverter(orderToOrderTableViewDTOConverter);
        modelMapper.createTypeMap(OrderTableViewDTO.class, OrderInvoiceDTO.class).setConverter(orderTableViewDTOToOrderInvoiceDTOConverter);
        modelMapper.createTypeMap(OrderTableViewDTO.class, Order.class).setConverter(orderTableViewDTOToOrderConverter);
        modelMapper.createTypeMap(OrderCreateDTO.class, Order.class).setConverter(orderCreateDTOToOrderConverter);
    }

    /**
     * Mapuje OrderTableViewDTO na Order.
     *
     * @param order
     * @return obiekt OrderTableViewDTO
     */
    public OrderTableViewDTO toDTO(Order order) {
        return Objects.isNull(order) ? null : modelMapper.map(order, OrderTableViewDTO.class);
    }

    /**
     * Mapuje OrderTableViewDTO na OrderInvoiceDTO.
     *
     * @param orderTableViewDTO
     * @return obiekt OrderInvoiceDTO
     */
    public OrderInvoiceDTO toDTO(OrderTableViewDTO orderTableViewDTO) {
        return Objects.isNull(orderTableViewDTO) ? null : modelMapper.map(orderTableViewDTO, OrderInvoiceDTO.class);
    }

    /**
     * Mapuje OrderCreateDTO na Order.
     *
     * @param orderCreateDTO
     * @return obiekt Order
     */
    public Order toEntity(OrderCreateDTO orderCreateDTO) {
        return Objects.isNull(orderCreateDTO) ? null : modelMapper.map(orderCreateDTO, Order.class);
    }

    /**
     * Mapuje OrderTableViewDTO na Order.
     *
     * @param orderTableViewDTO
     * @return obiekt Order
     */
    public Order toEntity(OrderTableViewDTO orderTableViewDTO) {
        return Objects.isNull(orderTableViewDTO) ? null : modelMapper.map(orderTableViewDTO, Order.class);
    }
}
