package com.example.hurtownia.domain.order;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@ToString
@NoArgsConstructor
@SuperBuilder(toBuilder = true)
public class OrderInvoiceDTO {

    private String name;
    private String surname;
    private String date;
    private String price;
    private String discount;
    private String priceAfterDiscount;
}
