package Entities;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Reprezentuje tabelę 'Klient' w bazie danych.
 */
@Entity
@Table(name = "Klient")
public class Klient implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "imie")
    private String imie;

    @Column(name = "nazwisko")
    private String nazwisko;

    @Column(name = "pesel")
    private String pesel;

    @Column(name = "numerTelefonu")
    private String numerTelefonu;

    @Column(name = "email")
    private String email;

    @Column(name = "miejscowosc")
    private String miejscowosc;

    @Column(name = "ulica")
    private String ulica;

    @Column(name = "numerMieszkania")
    private int numerMieszkania;

    @Column(name = "numerBudynku")
    private int numerBudynku;

    public Klient() {
    }

    public Klient(String imie, String nazwisko, String pesel, String numerTelefonu, String email, String miejscowosc, String ulica, int numerMieszkania, int numerBudynku) {
        this.imie = imie;
        this.nazwisko = nazwisko;
        this.pesel = pesel;
        this.numerTelefonu = numerTelefonu;
        this.email = email;
        this.miejscowosc = miejscowosc;
        this.ulica = ulica;
        this.numerMieszkania = numerMieszkania;
        this.numerBudynku = numerBudynku;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImie() {
        return imie;
    }

    public void setImie(String imie) {
        this.imie = imie;
    }

    public String getNazwisko() {
        return nazwisko;
    }

    public void setNazwisko(String nazwisko) {
        this.nazwisko = nazwisko;
    }

    public String getPesel() {
        return pesel;
    }

    public void setPesel(String pesel) {
        this.pesel = pesel;
    }

    public String getNumerTelefonu() {
        return numerTelefonu;
    }

    public void setNumerTelefonu(String numerTelefonu) {
        this.numerTelefonu = numerTelefonu;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMiejscowosc() {
        return miejscowosc;
    }

    public void setMiejscowosc(String miejscowosc) {
        this.miejscowosc = miejscowosc;
    }

    public String getUlica() {
        return ulica;
    }

    public void setUlica(String ulica) {
        this.ulica = ulica;
    }

    public int getNumerMieszkania() {
        return numerMieszkania;
    }

    public void setNumerMieszkania(int numerMieszkania) {
        this.numerMieszkania = numerMieszkania;
    }

    public int getNumerBudynku() {
        return numerBudynku;
    }

    public void setNumerBudynku(int numerBudynku) {
        this.numerBudynku = numerBudynku;
    }

    @Override
    public String toString() {
        return "Klient{" + "id=" + id + ", imie='" + imie + '\'' + ", nazwisko='" + nazwisko + '\'' + ", pesel='" + pesel + '\'' + ", numerTelefonu='" + numerTelefonu + '\'' + ", email='" + email + '\'' + ", miejscowosc='" + miejscowosc + '\'' + ", ulica='" + ulica + '\'' + ", numerMieszkania='" + numerMieszkania + '\'' + ", numerBudynku='" + numerBudynku + '\'' + '}';
    }
}
