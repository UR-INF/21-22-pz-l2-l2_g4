package com.example.hurtownia.domain.user;

import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Reprezentuje tabelę 'Uzytkownik' w bazie danych.
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@RequiredArgsConstructor
@SuperBuilder(toBuilder = true)
@Entity
@Table(name = "uzytkownik")
public class User implements Serializable {

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
    @Column(name = "email")
    private String email;

    @NonNull
    @Column(name = "haslo")
    private String password;

    @NonNull
    @Column(name = "numerTelefonu")
    private String phoneNumber;

    @NonNull
    @Column(name = "isAdmin")
    private Boolean isAdmin;

    @NonNull
    @Column(name = "generowanieRaportow")
    private Boolean generatingReports;

    @NonNull
    @Column(name = "udzielanieRabatow")
    private Boolean grantingDiscounts;
}
