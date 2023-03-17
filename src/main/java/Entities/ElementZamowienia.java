package Entities;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Reprezentuje tabelę 'elementZamowienia' w bazie danych.
 */
@Entity
@Table(name = "elementZamowienia")
public class ElementZamowienia implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @ManyToOne(targetEntity = Zamowienie.class)
    @JoinColumn(name = "idZamowienie")
    private Zamowienie idZamowienie;

    @ManyToOne(targetEntity = Produkt.class)
    @JoinColumn(name = "idProdukt")
    private Produkt idProdukt;

    @Column(name = "ilosc")
    private int ilosc;

    @Column(name = "cenaProduktu")
    private Double cenaProduktu;

    public ElementZamowienia() {
    }

    public ElementZamowienia(Zamowienie idZamowienie, Produkt idProdukt, int ilosc, Double cenaProduktu) {
        this.idZamowienie = idZamowienie;
        this.idProdukt = idProdukt;
        this.ilosc = ilosc;
        this.cenaProduktu = cenaProduktu;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Zamowienie getZamowienie() {
        return idZamowienie;
    }

    public void setZamowienie(Zamowienie idZamowienie) {
        this.idZamowienie = idZamowienie;
    }

    public Produkt getProdukt() {
        return idProdukt;
    }

    public void setProdukt(Produkt idProdukt) {
        this.idProdukt = idProdukt;
    }

    public int getIlosc() {
        return ilosc;
    }

    public void setIlosc(int ilosc) {
        this.ilosc = ilosc;
    }

    public Double getCenaProduktu() {
        return cenaProduktu;
    }

    public void setCenaProduktu(Double cenaProduktu) {
        this.cenaProduktu = cenaProduktu;
    }

    @Override
    public String toString() {
        return "ElementZamowienia{" +
                "id=" + id +
                ", idZamowienie=" + idZamowienie +
                ", idProdukt=" + idProdukt +
                ", ilosc=" + ilosc +
                ", cenaProduktu=" + cenaProduktu +
                '}';
    }
}