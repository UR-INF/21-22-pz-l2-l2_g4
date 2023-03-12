package Entities;

import javax.persistence.*;

@Entity
@Table(name = "element_zamowienia")
public class ElementZamowienia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idElementZamowienia")
    private int idElementZamowienia;

    @ManyToOne(targetEntity = Zamowienie.class)
    @JoinColumn(name = "idZamowienia")
    private int idZamowienia;

    @ManyToOne(targetEntity = Produkt.class)
    @JoinColumn(name = "idProduktu")
    private int idProduktu;

    @Column(name = "ilosc")
    private int ilosc;

    @Column(name = "cena")
    private Double cena;

    @Column(name = "waluta")
    private String waluta;

    public ElementZamowienia(int idElementZamowienia, int idZamowienia, int idProduktu, int ilosc, Double cena, String waluta) {
        this.idElementZamowienia = idElementZamowienia;
        this.idZamowienia = idZamowienia;
        this.idProduktu = idProduktu;
        this.ilosc = ilosc;
        this.cena = cena;
        this.waluta = waluta;
    }

    public int getIdElementZamowienia() {
        return idElementZamowienia;
    }

    public int getIdZamowienia() {
        return idZamowienia;
    }

    public int getIdProduktu() {
        return idProduktu;
    }

    public int getIlosc() {
        return ilosc;
    }

    public Double getCena() {
        return cena;
    }

    public String getWaluta() {
        return waluta;
    }

    public void setIdElementZamowienia(int idElementZamowienia) {
        this.idElementZamowienia = idElementZamowienia;
    }

    public void setIdZamowienia(int idZamowienia) {
        this.idZamowienia = idZamowienia;
    }

    public void setIdProduktu(int idProduktu) {
        this.idProduktu = idProduktu;
    }

    public void setIlosc(int ilosc) {
        this.ilosc = ilosc;
    }

    public void setCena(Double cena) {
        this.cena = cena;
    }

    public void setWaluta(String waluta) {
        this.waluta = waluta;
    }
}