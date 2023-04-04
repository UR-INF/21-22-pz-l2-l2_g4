package com.example.hurtownia.domain.Product;

import com.example.hurtownia.domain.Supplier.Dostawca;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Reprezentuje tabelę 'Produkt' w bazie danych.
 */
@Entity
@Table(name = "Produkt")
public class Produkt implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @ManyToOne(targetEntity = Dostawca.class)
    @JoinColumn(name = "idDostawca")
    private Dostawca idDostawca;

    @Column(name = "nazwa")
    private String nazwa;

    @Column(name = "jednostkaMiary")
    private String jednostkaMiary;

    @Column(name = "cena")
    private Double cena;

    @Column(name = "kraj")
    private String kraj;

    @Column(name = "kod")
    private String kod;

    @Column(name = "kolor")
    private String kolor;

    @Column(name = "ilosc")
    private int ilosc;

    @Column(name = "maxIlosc")
    private int maxIlosc;

    @Transient
    private boolean dostawa = false;

    public Produkt() {
    }

    public Produkt(Dostawca idDostawca, String nazwa, String jednostkaMiary, Double cena, String kraj, String kod, String kolor, int ilosc, int maxIlosc) {
        this.idDostawca = idDostawca;
        this.nazwa = nazwa;
        this.jednostkaMiary = jednostkaMiary;
        this.cena = cena;
        this.kraj = kraj;
        this.kod = kod;
        this.kolor = kolor;
        this.ilosc = ilosc;
        this.maxIlosc = maxIlosc;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Dostawca getDostawca() {
        return idDostawca;
    }

    public void setDostawca(Dostawca idDostawca) {
        this.idDostawca = idDostawca;
    }

    public String getNazwa() {
        return nazwa;
    }

    public void setNazwa(String nazwa) {
        this.nazwa = nazwa;
    }

    public String getJednostkaMiary() {
        return jednostkaMiary;
    }

    public void setJednostkaMiary(String jednostkaMiary) {
        this.jednostkaMiary = jednostkaMiary;
    }

    public Double getCena() {
        return cena;
    }

    public void setCena(Double cena) {
        this.cena = cena;
    }

    public String getKraj() {
        return kraj;
    }

    public void setKraj(String kraj) {
        this.kraj = kraj;
    }

    public String getKod() {
        return kod;
    }

    public void setKod(String kod) {
        this.kod = kod;
    }

    public String getKolor() {
        return kolor;
    }

    public void setKolor(String kolor) {
        this.kolor = kolor;
    }

    public int getIlosc() {
        return ilosc;
    }

    public void setIlosc(int ilosc) {
        this.ilosc = ilosc;
    }

    public int getMaxIlosc() {
        return maxIlosc;
    }

    public void setMaxIlosc(int maxIlosc) {
        this.maxIlosc = maxIlosc;
    }

    public boolean isDostawa() {
        return dostawa;
    }

    public void setDostawa(boolean dostawa) {
        this.dostawa = dostawa;
    }

    @Override
    public String toString() {
        return "Produkt{" + "id=" + id + ", idDostawca=" + idDostawca + ", nazwa='" + nazwa + '\'' + ", jednostkaMiary='" + jednostkaMiary + '\'' + ", cena=" + cena + ", kraj='" + kraj + '\'' + ", kod='" + kod + '\'' + ", kolor='" + kolor + '\'' + ", ilosc=" + ilosc + ", maxIlosc=" + maxIlosc + '}';
    }
}
