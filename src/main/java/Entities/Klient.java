package Entities;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Reprezentuje tabelę 'pasazer' w bazie danych. 
 * 
 * @author Tomasz Pitak
 */
@Entity
@Table(name = "klient")
public class Klient implements Serializable {

    /**
     * Unikalny identyfikator pasażera. Generowany automatycznie.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idPasazera")
    private int idPasazera;
    
    /**
     * Imię pasażera.
     */
    @Column(name = "imie")
    private String imie;
    
    /**
     * Nazwisko pasażera.
     */
    @Column(name = "nazwisko")
    private String nazwisko;
    
    /**
     * numer PESEL pasażera.
     */
   /* @Column(name = "pesel")
    private String pesel;*/
    
    /**
     * Numer telefonu pasażera.
     */
    @Column(name = "numerTelefonu")
    private String numerTelefonu;
    
    /**
     * Adres e-mail pasażera.
     */
    @Column(name = "email")
    private String email;

    @Column(name = "miejscowosc")
    private String miejscowosc;

    @Column(name = "ulica")
    private String ulica;

    @Column(name = "nrMieszkania")
    private String nrMieszkania;

    /**
     * Konstruuje obiekt Pasazer.
     *
     */
    public Klient(int idPasazera, String imie, String nazwisko, String numerTelefonu, String email, String miejscowosc, String ulica, String nrMieszkania) {
        this.idPasazera = idPasazera;
        this.imie = imie;
        this.nazwisko = nazwisko;
        this.numerTelefonu = numerTelefonu;
        this.email = email;
        this.miejscowosc = miejscowosc;
        this.ulica = ulica;
        this.nrMieszkania = nrMieszkania;
    }

    public int getIdPasazera() {
        return idPasazera;
    }

    public String getImie() {
        return imie;
    }

    public String getNazwisko() {
        return nazwisko;
    }

    public String getNumerTelefonu() {
        return numerTelefonu;
    }

    public String getEmail() {
        return email;
    }

    public String getMiejscowosc() {
        return miejscowosc;
    }

    public String getUlica() {
        return ulica;
    }

    public String getNrMieszkania() {
        return nrMieszkania;
    }

    public void setIdPasazera(int idPasazera) {
        this.idPasazera = idPasazera;
    }

    public void setImie(String imie) {
        this.imie = imie;
    }

    public void setNazwisko(String nazwisko) {
        this.nazwisko = nazwisko;
    }

    public void setNumerTelefonu(String numerTelefonu) {
        this.numerTelefonu = numerTelefonu;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setMiejscowosc(String miejscowosc) {
        this.miejscowosc = miejscowosc;
    }

    public void setUlica(String ulica) {
        this.ulica = ulica;
    }

    public void setNrMieszkania(String nrMieszkania) {
        this.nrMieszkania = nrMieszkania;
    }

    @Override
    public String toString() {
        return "Pasazer{" +
                "idPasazera=" + idPasazera +
                ", imie='" + imie + '\'' +
                ", nazwisko='" + nazwisko + '\'' +
                ", numerTelefonu='" + numerTelefonu + '\'' +
                ", email='" + email + '\'' +
                ", miejscowosc='" + miejscowosc + '\'' +
                ", ulica='" + ulica + '\'' +
                ", nrMieszkania='" + nrMieszkania + '\'' +
                '}';
    }
}
