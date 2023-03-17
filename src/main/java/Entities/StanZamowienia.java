package Entities;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Reprezentuje tabelę 'stanZamowienia' w bazie danych.
 */
@Entity
@Table(name = "stanZamowienia")
public class StanZamowienia implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "stan")
    private String stan;

    public StanZamowienia() {
    }

    public StanZamowienia(String stan) {
        this.stan = stan;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getStan() {
        return stan;
    }

    public void setStan(String stan) {
        this.stan = stan;
    }

    @Override
    public String toString() {
        return "StanZamowienia{" +
                "id=" + id +
                ", stan='" + stan + '\'' +
                '}';
    }
}
