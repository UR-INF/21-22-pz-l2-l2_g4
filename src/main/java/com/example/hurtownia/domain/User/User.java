package com.example.hurtownia.domain.user;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Reprezentuje tabelę 'Uzytkownik' w bazie danych.
 */
@Getter
@Setter
@NoArgsConstructor
@RequiredArgsConstructor
@Entity
@Table(name = "Uzytkownik")
public class User implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

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
    private int isAdmin;

    @NonNull
    @Column(name = "generowanieRaportow")
    private int generatingReports;

    @NonNull
    @Column(name = "udzielanieRabatow")
    private int grantingDiscounts;
}
