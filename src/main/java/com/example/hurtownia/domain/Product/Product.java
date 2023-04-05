package com.example.hurtownia.domain.product;

import com.example.hurtownia.domain.supplier.Supplier;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Reprezentuje tabelę 'Produkt' w bazie danych.
 */
@Entity
@Table(name = "Produkt")
public class Product implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @ManyToOne(targetEntity = Supplier.class)
    @JoinColumn(name = "idDostawca")
    private Supplier idSupplier;

    @Column(name = "nazwa")
    private String name;

    @Column(name = "jednostkaMiary")
    private String unitOfMeasurement;

    @Column(name = "cena")
    private Double price;

    @Column(name = "kraj")
    private String country;

    @Column(name = "kod")
    private String code;

    @Column(name = "kolor")
    private String color;

    @Column(name = "ilosc")
    private int number;

    @Column(name = "maxIlosc")
    private int maxNumber;

    @Transient
    private boolean supply = false;

    public Product() {
    }

    public Product(Supplier idSupplier, String name, String unitOfMeasurement, Double price, String country, String code, String color, int number, int maxNumber) {
        this.idSupplier = idSupplier;
        this.name = name;
        this.unitOfMeasurement = unitOfMeasurement;
        this.price = price;
        this.country = country;
        this.code = code;
        this.color = color;
        this.number = number;
        this.maxNumber = maxNumber;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Supplier getSupplier() {
        return idSupplier;
    }

    public void setSupplier(Supplier idSupplier) {
        this.idSupplier = idSupplier;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUnitOfMeasurement() {
        return unitOfMeasurement;
    }

    public void setUnitOfMeasurement(String unitOfMeasurement) {
        this.unitOfMeasurement = unitOfMeasurement;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public int getMaxNumber() {
        return maxNumber;
    }

    public void setMaxNumber(int maxNumber) {
        this.maxNumber = maxNumber;
    }

    public boolean isSupply() {
        return supply;
    }

    public void setSupply(boolean supply) {
        this.supply = supply;
    }

    @Override
    public String toString() {
        return "Produkt{" + "id=" + id + ", idDostawca=" + idSupplier + ", nazwa='" + name + '\'' + ", jednostkaMiary='" + unitOfMeasurement + '\'' + ", cena=" + price + ", kraj='" + country + '\'' + ", kod='" + code + '\'' + ", kolor='" + color + '\'' + ", ilosc=" + number + ", maxIlosc=" + maxNumber + '}';
    }
}
