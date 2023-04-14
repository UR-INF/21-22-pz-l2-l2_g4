package com.example.hurtownia.domain.supplier;

import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Reprezentuje tabelę 'dostawca' w bazie danych.
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@RequiredArgsConstructor
@SuperBuilder(toBuilder = true)
@Entity
@Table(name = "dostawca")
public class Supplier implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NonNull
    @Column(name = "email")
    private String email;

    @NonNull
    @Column(name = "kraj")
    private String country;

    @NonNull
    @Column(name = "miejscowosc")
    private String place;

    @NonNull
    @Column(name = "ulica")
    private String street;

    @NonNull
    @Column(name = "nazwa")
    private String name;

    @NonNull
    @Column(name = "nip")
    private String nip;
}
