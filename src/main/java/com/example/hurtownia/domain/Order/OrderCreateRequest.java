package com.example.hurtownia.domain.order;

import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder(toBuilder = true)
public class OrderCreateRequest {

    private Long customerId;
    private String date;
    private Double discount;
    private String state;
}
