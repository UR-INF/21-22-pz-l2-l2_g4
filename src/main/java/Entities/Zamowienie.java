package Entities;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "zamowienie")
public class Zamowienie implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idZamowienia")
    private int idZamowienia;

    @ManyToOne(targetEntity = Klient.class)
    @JoinColumn(name = "idKlienta")
    private int idKlienta;

    @Column(name = "data")
    private String data;

    public Zamowienie(int idZamowienia, int idKlienta, String data) {
        this.idZamowienia = idZamowienia;
        this.idKlienta = idKlienta;
        this.data = data;
    }

    public int getIdZamowienia() {
        return idZamowienia;
    }

    public int getIdKlienta() {
        return idKlienta;
    }

    public String getData() {
        return data;
    }

    public void setIdZamowienia(int idZamowienia) {
        this.idZamowienia = idZamowienia;
    }

    public void setIdKlienta(int idKlienta) {
        this.idKlienta = idKlienta;
    }

    public void setData(String data) {
        this.data = data;
    }
}
