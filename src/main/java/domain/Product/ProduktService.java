package domain.Product;

import domain.Supplier.Dostawca;
import Singleton.SingletonConnection;
import javafx.scene.control.Alert;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import javax.persistence.PersistenceException;
import java.util.List;

/**
 * Zawiera metody dla tabeli 'produkt'.
 */
public class ProduktService {

    private final SessionFactory sessionFactory;
    private Session session;
    private Transaction transaction;

    public ProduktService() {
        this.sessionFactory = SingletonConnection.getSessionFactory();
    }

    /**
     * Pobiera wszystkie produkty z bazy danych.
     *
     * @return lista wszystkich produktów
     */
    public List<Produkt> getProdukty() {
        session = sessionFactory.openSession();
        session.beginTransaction();
        session.flush();

        List<Produkt> list = session.createSQLQuery("select * from produkt").addEntity(Produkt.class).list();

        session.getTransaction().commit();
        session.close();

        return list;
    }

    /**
     * Zwraca produkt o podanym id.
     *
     * @param id
     * @return
     */
    public Produkt getProdukt(String id) {
        session = sessionFactory.openSession();
        session.beginTransaction();
        session.flush();

        Produkt p = (Produkt) session.createSQLQuery("select * from produkt where id=\'" + id + "\'").addEntity(Produkt.class).getSingleResult();

        session.getTransaction().commit();
        session.close();

        return p;
    }

    /**
     * Usuwa produkt z bazy danych.
     *
     * @param produkt
     * @return true - jeśli pomyślnie usunięto;
     * false - jeśli wystąpiły błędy
     */
    public boolean deleteProdukt(Produkt produkt) {
        session = sessionFactory.openSession();
        boolean result = false;

        try {
            transaction = session.beginTransaction();
            session.flush();
            session.delete(produkt);
            transaction.commit();
            result = true;
        } catch (PersistenceException e) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setContentText("Rekord jest używany przez inne tabele");
            alert.show();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setContentText(e.getMessage());
            alert.show();
        } finally {
            session.close();
        }

        return result;
    }

    /**
     * Dodaje produkt.
     *
     * @param idDostawca
     * @param nazwa
     * @param jednostkaMiary
     * @param cena
     * @param kraj
     * @param kod
     * @param kolor
     * @param ilosc
     * @param maxIlosc
     * @return
     */
    public Produkt saveProdukt(int idDostawca, String nazwa, String jednostkaMiary, Double cena, String kraj, String kod, String kolor, int ilosc, int maxIlosc) {
        session = sessionFactory.openSession();
        session.beginTransaction();
        session.flush();

        Dostawca d = (Dostawca) session.createSQLQuery("select * from dostawca where id=\'" + idDostawca + "\'").addEntity(Dostawca.class).getSingleResult();
        Produkt p = new Produkt(d, nazwa, jednostkaMiary, cena, kraj, kod, kolor, ilosc, maxIlosc);

        session.save(p);

        session.getTransaction().commit();
        session.close();

        return p;
    }

    /**
     * Aktualizuje dostawcę produktu.
     *
     * @param p
     * @param idDostawca
     */
    public void updateProduktDostawca(Produkt p, String idDostawca) {
        session = sessionFactory.openSession();
        session.beginTransaction();
        session.flush();

        Dostawca d = (Dostawca) session.createSQLQuery("select * from dostawca where id=\'" + idDostawca + "\'").addEntity(Dostawca.class).getSingleResult();
        p.setDostawca(d);
        session.update(p);

        session.getTransaction().commit();
        session.close();
    }

    /**
     * Aktualizuje produkt.
     *
     * @param p
     */
    public void updateProdukt(Produkt p) {
        session = sessionFactory.openSession();
        session.beginTransaction();
        session.flush();
        session.update(p);
        session.getTransaction().commit();
        session.close();
    }
}
