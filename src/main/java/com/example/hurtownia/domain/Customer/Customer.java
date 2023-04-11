package com.example.hurtownia.domain.customer;

import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Reprezentuje tabelę 'klient' w bazie danych.
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@RequiredArgsConstructor
@SuperBuilder(toBuilder = true)
@Entity
@Table(name = "klient")
public class Customer implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NonNull
    @Column(name = "imie")
    private String name;

    @NonNull
    @Column(name = "nazwisko")
    private String surname;

    @NonNull
    @Column(name = "pesel")
    private String pesel;

    @NonNull
    @Column(name = "numerTelefonu")
    private String phoneNumber;

    @NonNull
    @Column(name = "email")
    private String email;

    @NonNull
    @Column(name = "miejscowosc")
    private String place;

    @NonNull
    @Column(name = "ulica")
    private String street;

    @NonNull
    @Column(name = "numerMieszkania")
    private Integer apartmentNumber;

    @NonNull
    @Column(name = "numerBudynku")
    private Integer buildingNumber;
}
