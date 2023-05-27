package com.example.hurtownia.domain.order;

import com.example.hurtownia.domain.orderitem.OrderItem;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor
@SuperBuilder(toBuilder = true)
public class InvoiceData {

    private String name;
    private String surname;
    private String place;
    private String zipCode;
    private String street;
    private Integer apartmentNumber;
    private Integer buildingNumber;
    private  String phoneNumber;
    private String date;
    private String value;
    private String discount;
    private String valueAfterDiscount;
    private List<OrderItem> items;
}