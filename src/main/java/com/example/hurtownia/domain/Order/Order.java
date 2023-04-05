package com.example.hurtownia.domain.order;

import com.example.hurtownia.domain.customer.Customer;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Reprezentuje tabelę 'Zamowienie' w bazie danych.
 */
@Entity
@Table(name = "Zamowienie")
public class Order implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @ManyToOne(targetEntity = Customer.class)
    @JoinColumn(name = "idKlient")
    private Customer idCustomer;

    @Column(name = "data")
    private String date;

    @Column(name = "stanZamowienia")
    private String state;

    @Column(name = "rabat")
    private Double discount;

    public Order() {
    }

    public Order(Customer idCustomer, String date, String state, Double discount) {
        this.idCustomer = idCustomer;
        this.date = date;
        this.state = state;
        this.discount = discount;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Customer getKlient() {
        return idCustomer;
    }

    public void setKlient(Customer idCustomer) {
        this.idCustomer = idCustomer;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Double getDiscount() {
        return discount;
    }

    public void setDiscount(Double discount) {
        this.discount = discount;
    }

    @Override
    public String toString() {
        return "Zamowienie{" + "id=" + id + ", idKlient=" + idCustomer + ", data='" + date + '\'' + ", stanZamowienia='" + state + '\'' + ", rabat='" + discount + '\'' + '}';
    }
}
