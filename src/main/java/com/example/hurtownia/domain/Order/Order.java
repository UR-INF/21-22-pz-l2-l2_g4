package com.example.hurtownia.domain.order;

import com.example.hurtownia.domain.customer.Customer;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Reprezentuje tabelę 'zamowienie' w bazie danych.
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@RequiredArgsConstructor
@SuperBuilder(toBuilder = true)
@Entity
@Table(name = "zamowienie")
public class Order implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NonNull
    @ManyToOne(targetEntity = Customer.class)
    @JoinColumn(name = "idKlient")
    private Customer customer;

    @NonNull
    @Column(name = "data")
    private String date;

    @NonNull
    @Column(name = "stanZamowienia")
    private String state;

    @NonNull
    @Column(name = "rabat")
    private Double discount;
}
