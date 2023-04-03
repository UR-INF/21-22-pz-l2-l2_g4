package OrderItems;

import Order.Zamowienie;
import Product.Produkt;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Reprezentuje tabelę 'ElementZamowienia' w bazie danych.
 */
@Entity
@Table(name = "element_zamowienia")
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

    @Column(name = "cenaElementu")
    private Double cenaElementu;

    @Column(name = "cenaZaJednostke")
    private Double cenaZaJednostke;

    public ElementZamowienia() {
    }

    public ElementZamowienia(Zamowienie idZamowienie, Produkt idProdukt, int ilosc, Double cenaElementu, Double cenaZaJednostke) {
        this.idZamowienie = idZamowienie;
        this.idProdukt = idProdukt;
        this.ilosc = ilosc;
        this.cenaElementu = cenaElementu;
        this.cenaZaJednostke = cenaZaJednostke;
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

    public Double getCenaElementu() {
        return cenaElementu;
    }

    public void setCenaElementu(Double cenaElementu) {
        this.cenaElementu = cenaElementu;
    }

    public Double getCenaZaJednostke() {
        return cenaZaJednostke;
    }

    public void setCenaZaJednostke(Double cenaZaJednostke) {
        this.cenaZaJednostke = cenaZaJednostke;
    }

    @Override
    public String toString() {
        return "ElementZamowienia{" + "id=" + id + ", idZamowienie=" + idZamowienie + ", idProdukt=" + idProdukt + ", ilosc=" + ilosc + ", cenaElementu=" + cenaElementu + ", cenaZaJednostke=" + cenaZaJednostke + '}';
    }
}