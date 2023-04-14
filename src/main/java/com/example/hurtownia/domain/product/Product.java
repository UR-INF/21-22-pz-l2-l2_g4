package com.example.hurtownia.domain.product;

import com.example.hurtownia.domain.supplier.Supplier;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Reprezentuje tabelę 'produkt' w bazie danych.
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@RequiredArgsConstructor
@SuperBuilder(toBuilder = true)
@Entity
@Table(name = "produkt")
public class Product implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NonNull
    @ManyToOne(targetEntity = Supplier.class)
    @JoinColumn(name = "idDostawca")
    private Supplier supplier;

    @NonNull
    @Column(name = "nazwa")
    private String name;

    @NonNull
    @Column(name = "jednostkaMiary")
    private String unitOfMeasurement;

    @NonNull
    @Column(name = "cena")
    private Double price;

    @NonNull
    @Column(name = "kraj")
    private String country;

    @NonNull
    @Column(name = "kod")
    private String code;

    @NonNull
    @Column(name = "kolor")
    private String color;

    @NonNull
    @Column(name = "ilosc")
    private Integer number;

    @NonNull
    @Column(name = "maxIlosc")
    private Integer maxNumber;
}
