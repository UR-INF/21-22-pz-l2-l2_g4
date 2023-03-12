package Entities;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "dostawca")
public class Dostawca implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idDostawcy")
    private int idDostawcy;

    @Column(name = "email")
    private String email;

    @Column(name = "kraj")
    private String kraj;

    @Column(name = "miejscowosc")
    private String miejscowosc;

    @Column(name = "ulica")
    private String ulica;

    @Column(name = "nazwa")
    private String nazwa;

    public Dostawca(int idDostawcy, String email, String kraj, String miejscowosc, String ulica, String nazwa) {
        this.idDostawcy = idDostawcy;
        this.email = email;
        this.kraj = kraj;
        this.miejscowosc = miejscowosc;
        this.ulica = ulica;
        this.nazwa = nazwa;
    }

    public int getIdDostawcy() {
        return idDostawcy;
    }

    public String getEmail() {
        return email;
    }

    public String getKraj() {
        return kraj;
    }

    public String getMiejscowosc() {
        return miejscowosc;
    }

    public String getUlica() {
        return ulica;
    }

    public String getNazwa() {
        return nazwa;
    }

    public void setIdDostawcy(int idDostawcy) {
        this.idDostawcy = idDostawcy;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setKraj(String kraj) {
        this.kraj = kraj;
    }

    public void setMiejscowosc(String miejscowosc) {
        this.miejscowosc = miejscowosc;
    }

    public void setUlica(String ulica) {
        this.ulica = ulica;
    }

    public void setNazwa(String nazwa) {
        this.nazwa = nazwa;
    }
}
