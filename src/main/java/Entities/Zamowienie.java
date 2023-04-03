package Entities;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Reprezentuje tabelę 'Zamowienie' w bazie danych.
 */
@Entity
@Table(name = "Zamowienie")
public class Zamowienie implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @ManyToOne(targetEntity = Klient.class)
    @JoinColumn(name = "idKlient")
    private Klient idKlient;

    @Column(name = "data")
    private String data;

    @Column(name = "stanZamowienia")
    private String stanZamowienia;

    @Column(name = "rabat")
    private Double rabat;

    public Zamowienie() {
    }

    public Zamowienie(Klient idKlient, String data, String stanZamowienia, Double rabat) {
        this.idKlient = idKlient;
        this.data = data;
        this.stanZamowienia = stanZamowienia;
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

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getStanZamowienia() {
        return stanZamowienia;
    }

    public void setStanZamowienia(String stanZamowienia) {
        this.stanZamowienia = stanZamowienia;
    }

    public Double getRabat() {
        return rabat;
    }

    public void setRabat(Double rabat) {
        this.rabat = rabat;
    }

    @Override
    public String toString() {
        return "Zamowienie{" + "id=" + id + ", idKlient=" + idKlient + ", data='" + data + '\'' + ", stanZamowienia='" + stanZamowienia + '\'' + ", rabat='" + rabat + '\'' + '}';
    }
}
