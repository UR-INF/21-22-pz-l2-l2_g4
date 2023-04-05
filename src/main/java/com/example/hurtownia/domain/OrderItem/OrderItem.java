package com.example.hurtownia.domain.orderitem;

import com.example.hurtownia.domain.order.Order;
import com.example.hurtownia.domain.product.Product;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Reprezentuje tabelę 'ElementZamowienia' w bazie danych.
 */
@Entity
@Table(name = "element_zamowienia")
public class OrderItem implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @ManyToOne(targetEntity = Order.class)
    @JoinColumn(name = "idZamowienie")
    private Order idOrder;

    @ManyToOne(targetEntity = Product.class)
    @JoinColumn(name = "idProdukt")
    private Product idProduct;

    @Column(name = "ilosc")
    private int number;

    @Column(name = "cenaElementu")
    private Double itemPrice;

    @Column(name = "cenaZaJednostke")
    private Double pricePerUnit;

    public OrderItem() {
    }

    public OrderItem(Order idOrder, Product idProduct, int number, Double itemPrice, Double pricePerUnit) {
        this.idOrder = idOrder;
        this.idProduct = idProduct;
        this.number = number;
        this.itemPrice = itemPrice;
        this.pricePerUnit = pricePerUnit;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Order getOrder() {
        return idOrder;
    }

    public void setOrder(Order idOrder) {
        this.idOrder = idOrder;
    }

    public Product getProduct() {
        return idProduct;
    }

    public void setProduct(Product idProduct) {
        this.idProduct = idProduct;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public Double getItemPrice() {
        return itemPrice;
    }

    public void setItemPrice(Double itemPrice) {
        this.itemPrice = itemPrice;
    }

    public Double getPricePerUnit() {
        return pricePerUnit;
    }

    public void setPricePerUnit(Double pricePerUnit) {
        this.pricePerUnit = pricePerUnit;
    }

    @Override
    public String toString() {
        return "ElementZamowienia{" + "id=" + id + ", idZamowienie=" + idOrder + ", idProdukt=" + idProduct + ", ilosc=" + number + ", cenaElementu=" + itemPrice + ", cenaZaJednostke=" + pricePerUnit + '}';
    }
}