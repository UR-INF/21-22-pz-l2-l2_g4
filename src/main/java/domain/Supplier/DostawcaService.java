package domain.Supplier;

import Singleton.SingletonConnection;
import javafx.scene.control.Alert;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import javax.persistence.PersistenceException;
import java.util.List;

/**
 * Zawiera metody dla tabeli 'dostawca'.
 */
public class DostawcaService {

    private SessionFactory sessionFactory;
    private Session session;
    private Transaction transaction;

    public DostawcaService() {
        this.sessionFactory = SingletonConnection.getSessionFactory();
    }

    /**
     * Pobiera wszystkich dostawców z bazy danych.
     *
     * @return lista wszystkich dostawców
     */
    public List<Dostawca> getDostawcy() {
        session = sessionFactory.openSession();
        session.beginTransaction();
        session.flush();

        List<Dostawca> list = session.createSQLQuery("select * from dostawca").addEntity(Dostawca.class).list();

        session.getTransaction().commit();
        session.close();

        return list;
    }

    /**
     * Zwraca dostawcę o podanym id.
     *
     * @param id
     * @return
     */
    public Dostawca getDostawca(String id) {
        session = sessionFactory.openSession();
        session.beginTransaction();
        session.flush();

        Dostawca d = (Dostawca) session.createSQLQuery("select * from dostawca where id=\'" + id + "\'").addEntity(Dostawca.class).getSingleResult();

        session.getTransaction().commit();
        session.close();

        return d;
    }

    /**
     * Usuwa dostawcę z bazy danych.
     *
     * @param dostawca
     * @return true - jeśli pomyślnie usunięto;
     * false - jeśli wystąpiły błędy
     */
    public boolean deleteDostawca(Dostawca dostawca) {
        session = sessionFactory.openSession();
        boolean result = false;

        try {
            transaction = session.beginTransaction();
            session.flush();
            session.delete(dostawca);
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
     * Dodaje nowego dostawcę.
     *
     * @param email
     * @param kraj
     * @param miejscowosc
     * @param ulica
     * @param nazwa
     * @param nip
     * @return
     */
    public Dostawca saveDostawca(String email, String kraj, String miejscowosc, String ulica, String nazwa, String nip) {
        session = sessionFactory.openSession();
        session.beginTransaction();
        session.flush();

        Dostawca d = new Dostawca(email, kraj, miejscowosc, ulica, nazwa, nip);

        session.save(d);

        session.getTransaction().commit();
        session.close();

        return d;
    }

    /**
     * Aktualizuje dostawcę.
     *
     * @param d
     */
    public void updateDostawca(Dostawca d) {
        session = sessionFactory.openSession();
        session.beginTransaction();
        session.flush();
        session.update(d);
        session.getTransaction().commit();
        session.close();
    }

}
