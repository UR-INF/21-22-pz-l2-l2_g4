package Entities;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "produkt")
public class Produkt implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idProduktu")
    private int idProduktu;

    @ManyToOne(targetEntity = Dostawca.class)
    @JoinColumn(name = "idDostawcy")
    private int idDostawcy;

    @Column(name = "nazwa")
    private String nazwa;

    @Column(name = "cena")
    private Double cena;

    @Column(name = "waluta")
    private String waluta;

    @Column(name = "kraj")
    private String kraj;
}
