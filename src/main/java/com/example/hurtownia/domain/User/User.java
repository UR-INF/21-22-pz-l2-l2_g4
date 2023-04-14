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
@AllArgsConstructor(onConstructor = @__(@Builder))
@Entity
@Table(name = "Uzytkownik")
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

    @Column(name = "isAdmin")
    private boolean isAdmin;

    @Column(name = "generowanieRaportow")
    private boolean generatingReports;

    @Column(name = "udzielanieRabatow")
    private boolean grantingDiscounts;
}
