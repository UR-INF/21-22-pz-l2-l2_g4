package com.example.hurtownia.domain.orderitem;

import com.example.hurtownia.domain.order.OrderService;
import com.example.hurtownia.domain.product.Product;
import com.example.hurtownia.domain.product.ProductService;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * Mapper pomiędzy OrderItem i jego DTO.
 */
@Component
public class OrderItemMapper {

    public ProductService productService;
    public OrderService orderService;

    /**
     * Konwerter z OrderItem na OrderItemTableViewDTO.
     */
    Converter<OrderItem, OrderItemTableViewDTO> orderItemToOrderItemTableViewDTOConverter = context -> {
        OrderItemTableViewDTO orderItemTableViewDTO = new OrderItemTableViewDTO();
        orderItemTableViewDTO.setId(context.getSource().getId());
        orderItemTableViewDTO.setOrderid(context.getSource().getOrder().getId());
        orderItemTableViewDTO.setProductId(context.getSource().getProduct().getId());
        orderItemTableViewDTO.setAmount(context.getSource().getAmount());
        orderItemTableViewDTO.setPricePerUnit(context.getSource().getPricePerUnit());
        orderItemTableViewDTO.setItemPrice(orderItemTableViewDTO.getAmount() * orderItemTableViewDTO.getPricePerUnit());
        return orderItemTableViewDTO;
    };

    /**
     * Konwerter z OrderItemTableViewDTO na OrderItem.
     */
    Converter<OrderItemTableViewDTO, OrderItem> orderItemTableViewDTOToOrderItemConverter = context -> {
        OrderItem orderItem = new OrderItem();
        orderItem.setId(context.getSource().getId());
        orderItem.setOrder(orderService.findById(context.getSource().getOrderid()));
        orderItem.setProduct(productService.findById(context.getSource().getProductId()));
        orderItem.setAmount(context.getSource().getAmount());
        orderItem.setItemPrice(context.getSource().getItemPrice());
        orderItem.setPricePerUnit(context.getSource().getPricePerUnit());
        return orderItem;
    };

    /**
     * Konwerter z OrderItemCreateDTO na OrderItem.
     */
    Converter<OrderItemCreateDTO, OrderItem> orderItemCreateDTOToOrderItemConverter = context -> {
        OrderItem orderItem = new OrderItem();
        Product product = productService.findById(context.getSource().getProductId());
        orderItem.setOrder(orderService.findById(context.getSource().getOrderid()));
        orderItem.setProduct(product);
        orderItem.setAmount(context.getSource().getAmount());
        orderItem.setPricePerUnit(product.getPrice());
        orderItem.setItemPrice(orderItem.getPricePerUnit() * orderItem.getAmount());
        return orderItem;
    };
    private ModelMapper modelMapper;

    @Autowired
    public OrderItemMapper(ModelMapper modelMapper, ProductService productService, OrderService orderService) {
        this.modelMapper = modelMapper;
        this.productService = productService;
        this.orderService = orderService;

        modelMapper.createTypeMap(OrderItem.class, OrderItemTableViewDTO.class).setConverter(orderItemToOrderItemTableViewDTOConverter);
        modelMapper.createTypeMap(OrderItemTableViewDTO.class, OrderItem.class).setConverter(orderItemTableViewDTOToOrderItemConverter);
        modelMapper.createTypeMap(OrderItemCreateDTO.class, OrderItem.class).setConverter(orderItemCreateDTOToOrderItemConverter);
    }

    /**
     * Mapuje OrderItemTableViewDTO na OrderItem.
     *
     * @param orderItem
     * @return obiekt OrderItemTableViewDTO
     */
    public OrderItemTableViewDTO toDTO(OrderItem orderItem) {
        return Objects.isNull(orderItem) ? null : modelMapper.map(orderItem, OrderItemTableViewDTO.class);
    }

    /**
     * Mapuje OrderItemCreateDTO na OrderItem.
     *
     * @param orderItemCreateDTO
     * @return obiekt OrderItem
     */
    public OrderItem toEntity(OrderItemCreateDTO orderItemCreateDTO) {
        return Objects.isNull(orderItemCreateDTO) ? null : modelMapper.map(orderItemCreateDTO, OrderItem.class);
    }

    /**
     * Mapuje OrderItemTableViewDTO na OrderItem.
     *
     * @param orderItemTableViewDTO
     * @return obiekt OrderItem
     */
    public OrderItem toEntity(OrderItemTableViewDTO orderItemTableViewDTO) {
        return Objects.isNull(orderItemTableViewDTO) ? null : modelMapper.map(orderItemTableViewDTO, OrderItem.class);
    }
}
