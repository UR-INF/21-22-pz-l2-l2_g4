package Entities;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Reprezentuje tabelę 'zamowienie' w bazie danych.
 */
@Entity
@Table(name = "zamowienie")
public class Zamowienie implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @ManyToOne(targetEntity = Klient.class)
    @JoinColumn(name = "idKlient")
    private Klient idKlient;

    @ManyToOne(targetEntity = StanZamowienia.class)
    @JoinColumn(name = "idStanZamowienia")
    private StanZamowienia idStanZamowienia;

    @Column(name = "data")
    private String data;

    @Column(name = "rabat")
    private String rabat;

    public Zamowienie() {
    }

    public Zamowienie(Klient idKlient, StanZamowienia idStanZamowienia, String data, String rabat) {
        this.idKlient = idKlient;
        this.idStanZamowienia = idStanZamowienia;
        this.data = data;
        this.rabat = rabat;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Klient getKlient() {
        return idKlient;
    }

    public void setKlient(Klient idKlient) {
        this.idKlient = idKlient;
    }

    public StanZamowienia getStanZamowienia() {
        return idStanZamowienia;
    }

    public void setStanZamowienia(StanZamowienia idStanZamowienia) {
        this.idStanZamowienia = idStanZamowienia;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getRabat() {
        return rabat;
    }

    public void setRabat(String rabat) {
        this.rabat = rabat;
    }

    @Override
    public String toString() {
        return "Zamowienie{" +
                "id=" + id +
                ", idKlient=" + idKlient +
                ", idStanZamowienia=" + idStanZamowienia +
                ", data='" + data + '\'' +
                ", rabat='" + rabat + '\'' +
                '}';
    }
}
