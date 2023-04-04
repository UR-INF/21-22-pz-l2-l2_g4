package domain.User;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Reprezentuje tabelę 'Uzytkownik' w bazie danych.
 */
@Entity
@Table(name = "Uzytkownik")
public class Uzytkownik implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "imie")
    private String imie;

    @Column(name = "nazwisko")
    private String nazwisko;

    @Column(name = "email")
    private String email;

    @Column(name = "haslo")
    private String haslo;

    @Column(name = "numerTelefonu")
    private String numerTelefonu;

    @Column(name = "isAdmin")
    private int isAdmin;

    @Column(name = "generowanieRaportow")
    private int generowanieRaportow;

    @Column(name = "udzielanieRabatow")
    private int udzielanieRabatow;

    public Uzytkownik() {
    }

    public Uzytkownik(String imie, String nazwisko, String email, String haslo, String numerTelefonu, int isAdmin, int generowanieRaportow, int udzielanieRabatow) {
        this.imie = imie;
        this.nazwisko = nazwisko;
        this.email = email;
        this.haslo = haslo;
        this.numerTelefonu = numerTelefonu;
        this.isAdmin = isAdmin;
        this.generowanieRaportow = generowanieRaportow;
        this.udzielanieRabatow = udzielanieRabatow;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getHaslo() {
        return haslo;
    }

    public void setHaslo(String haslo) {
        this.haslo = haslo;
    }

    public String getNumerTelefonu() {
        return numerTelefonu;
    }

    public void setNumerTelefonu(String numerTelefonu) {
        this.numerTelefonu = numerTelefonu;
    }

    public int getIsAdmin() {
        return isAdmin;
    }

    public void setIsAdmin(int isAdmin) {
        this.isAdmin = isAdmin;
    }

    public int getGenerowanieRaportow() {
        return generowanieRaportow;
    }

    public void setGenerowanieRaportow(int generowanieRaportow) {
        this.generowanieRaportow = generowanieRaportow;
    }

    public int getUdzielanieRabatow() {
        return udzielanieRabatow;
    }

    public void setUdzielanieRabatow(int udzielanieRabatow) {
        this.udzielanieRabatow = udzielanieRabatow;
    }

    @Override
    public String toString() {
        return "Uzytkownik{" + "id=" + id + ", imie='" + imie + '\'' + ", nazwisko='" + nazwisko + '\'' + ", email='" + email + '\'' + ", haslo='" + haslo + '\'' + ", numerTelefonu='" + numerTelefonu + '\'' + ", isAdmin=" + isAdmin + ", generowanieRaportow=" + generowanieRaportow + ", udzielanieRabatow=" + udzielanieRabatow + '}';
    }
}
