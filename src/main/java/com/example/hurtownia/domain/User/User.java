package com.example.hurtownia.domain.user;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Reprezentuje tabelę 'Uzytkownik' w bazie danych.
 */
@Entity
@Table(name = "Uzytkownik")
public class User implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "imie")
    private String name;

    @Column(name = "nazwisko")
    private String surname;

    @Column(name = "email")
    private String email;

    @Column(name = "haslo")
    private String password;

    @Column(name = "numerTelefonu")
    private String phoneNumber;

    @Column(name = "isAdmin")
    private int isAdmin;

    @Column(name = "generowanieRaportow")
    private int generatingReports;

    @Column(name = "udzielanieRabatow")
    private int grantingDiscounts;

    public User() {
    }

    public User(String name, String surname, String email, String password, String phoneNumber, int isAdmin, int generatingReports, int grantingDiscounts) {
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.isAdmin = isAdmin;
        this.generatingReports = generatingReports;
        this.grantingDiscounts = grantingDiscounts;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public int getIsAdmin() {
        return isAdmin;
    }

    public void setIsAdmin(int isAdmin) {
        this.isAdmin = isAdmin;
    }

    public int getGeneratingReports() {
        return generatingReports;
    }

    public void setGeneratingReports(int generatingReports) {
        this.generatingReports = generatingReports;
    }

    public int getGrantingDiscounts() {
        return grantingDiscounts;
    }

    public void setGrantingDiscounts(int grantingDiscounts) {
        this.grantingDiscounts = grantingDiscounts;
    }

    @Override
    public String toString() {
        return "Uzytkownik{" + "id=" + id + ", imie='" + name + '\'' + ", nazwisko='" + surname + '\'' + ", email='" + email + '\'' + ", haslo='" + password + '\'' + ", numerTelefonu='" + phoneNumber + '\'' + ", isAdmin=" + isAdmin + ", generowanieRaportow=" + generatingReports + ", udzielanieRabatow=" + grantingDiscounts + '}';
    }
}
