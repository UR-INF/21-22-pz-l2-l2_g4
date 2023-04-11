package com.example.hurtownia.domain.orderitem;

import com.example.hurtownia.domain.order.Order;
import com.example.hurtownia.domain.product.Product;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Reprezentuje tabelę 'ElementZamowienia' w bazie danych.
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@RequiredArgsConstructor
@SuperBuilder(toBuilder = true)
@AllArgsConstructor(onConstructor = @__(@Builder))
@Entity
@Table(name = "element_zamowienia")
public class OrderItem implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @NonNull
    @ManyToOne(targetEntity = Order.class)
    @JoinColumn(name = "idZamowienie")
    private Order Order;

    @NonNull
    @ManyToOne(targetEntity = Product.class)
    @JoinColumn(name = "idProdukt")
    private Product product;

    @NonNull
    @Column(name = "ilosc")
    private int number;

    @NonNull
    @Column(name = "cenaElementu")
    private Double itemPrice;

    @NonNull
    @Column(name = "cenaZaJednostke")
    private Double pricePerUnit;
}