package com.example.hurtownia.domain.customer;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Reprezentuje tabelę 'klient' w bazie danych.
 */
@Entity
@Table(name = "klient")
public class Customer implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "imie")
    private String name;

    @Column(name = "nazwisko")
    private String surname;

    @Column(name = "pesel")
    private String pesel;

    @Column(name = "numerTelefonu")
    private String phoneNumber;

    @Column(name = "email")
    private String email;

    @Column(name = "miejscowosc")
    private String place;

    @Column(name = "ulica")
    private String street;

    @Column(name = "numerMieszkania")
    private int apartmentNumber;

    @Column(name = "numerBudynku")
    private int buildingNumber;

    public Customer() {
    }

    public Customer(String name, String surname, String pesel, String phoneNumber, String email, String place, String street, int apartmentNumber, int buildingNumber) {
        this.name = name;
        this.surname = surname;
        this.pesel = pesel;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.place = place;
        this.street = street;
        this.apartmentNumber = apartmentNumber;
        this.buildingNumber = buildingNumber;
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

    public String getPesel() {
        return pesel;
    }

    public void setPesel(String pesel) {
        this.pesel = pesel;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public int getApartmentNumber() {
        return apartmentNumber;
    }

    public void setApartmentNumber(int apartmentNumber) {
        this.apartmentNumber = apartmentNumber;
    }

    public int getBuildingNumber() {
        return buildingNumber;
    }

    public void setBuildingNumber(int numerBudynku) {
        this.buildingNumber = numerBudynku;
    }

    @Override
    public String toString() {
        return "Klient{" + "id=" + id + ", imie='" + name + '\'' + ", nazwisko='" + surname + '\'' + ", pesel='" + pesel + '\'' + ", numerTelefonu='" + phoneNumber + '\'' + ", email='" + email + '\'' + ", miejscowosc='" + place + '\'' + ", ulica='" + street + '\'' + ", numerMieszkania='" + apartmentNumber + '\'' + ", numerBudynku='" + buildingNumber + '\'' + '}';
    }
}
