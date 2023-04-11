package com.example.hurtownia.domain.Order;

import com.example.hurtownia.domain.customer.Customer;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderDTO {

    private Long id;
    private Customer customerDTO;
    private String date;
    private String state;
    private Double discount;
}
