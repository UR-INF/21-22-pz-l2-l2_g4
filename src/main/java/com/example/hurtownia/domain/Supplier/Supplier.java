package com.example.hurtownia.domain.supplier;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Reprezentuje tabelę 'dostawca' w bazie danych.
 */
@Entity
@Table(name = "Dostawca")
public class Supplier implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "email")
    private String email;

    @Column(name = "kraj")
    private String country;

    @Column(name = "miejscowosc")
    private String place;

    @Column(name = "ulica")
    private String street;

    @Column(name = "nazwa")
    private String name;

    @Column(name = "nip")
    private String nip;

    public Supplier() {
    }

    public Supplier(String email, String country, String place, String street, String name, String nip) {
        this.email = email;
        this.country = country;
        this.place = place;
        this.street = street;
        this.name = name;
        this.nip = nip;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNip() {
        return nip;
    }

    public void setNip(String nip) {
        this.nip = nip;
    }

    @Override
    public String toString() {
        return "Dostawca{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", kraj='" + country + '\'' +
                ", miejscowosc='" + place + '\'' +
                ", ulica='" + street + '\'' +
                ", nazwa='" + name + '\'' +
                ", nip='" + nip + '\'' +
                '}';
    }
}
