package Customer;

import Singleton.SingletonConnection;
import javafx.scene.control.Alert;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import javax.persistence.PersistenceException;
import java.util.List;

/**
 * Zawiera metody dla tabeli 'klient'.
 */
public class KlientMethods {

    private SessionFactory sessionFactory;
    private Session session;
    private Transaction transaction;

    public KlientMethods() {
        this.sessionFactory = SingletonConnection.getSessionFactory();
    }

    /**
     * Pobiera wszystkich klientów z bazy danych.
     *
     * @return lista wszystkich klientów
     */
    public List<Klient> getKlienci() {
        session = sessionFactory.openSession();
        session.beginTransaction();
        session.flush();

        List<Klient> list = session.createSQLQuery("select * from klient").addEntity(Klient.class).list();

        session.getTransaction().commit();
        session.close();

        return list;
    }

    /**
     * Zwraca klienta o podanym id.
     *
     * @param id
     * @return
     */
    public Klient getKlient(String id) {
        session = sessionFactory.openSession();
        session.beginTransaction();
        session.flush();

        Klient k = (Klient) session.createSQLQuery("select * from klient where id=\'" + id + "\'").addEntity(Klient.class).getSingleResult();

        session.getTransaction().commit();
        session.close();

        return k;
    }

    /**
     * Usuwa klienta z bazy danych.
     *
     * @param klient
     * @return true - jeśli pomyślnie usunięto;
     * false - jeśli wystąpiły błędy
     */
    public boolean deleteKlient(Klient klient) {
        session = sessionFactory.openSession();
        boolean result = false;

        try {
            transaction = session.beginTransaction();
            session.flush();
            session.delete(klient);
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
     * Dodaje nowego klienta.
     *
     * @param imie
     * @param nazwisko
     * @param pesel
     * @param numerTelefonu
     * @param email
     * @param miejscowosc
     * @param ulica
     * @param numerMieszkania
     * @param numerBudynku
     * @return
     */
    public Klient saveKlient(String imie, String nazwisko, String pesel, String numerTelefonu, String email, String miejscowosc, String ulica, int numerMieszkania, int numerBudynku) {
        session = sessionFactory.openSession();
        session.beginTransaction();
        session.flush();

        Klient k = new Klient(imie,  nazwisko,  pesel,  numerTelefonu,  email,  miejscowosc,  ulica,  numerMieszkania,  numerBudynku);

        session.save(k);

        session.getTransaction().commit();
        session.close();

        return k;
    }

    /**
     * Aktualizuje klienta.
     *
     * @param k
     */
    public void updateKlient(Klient k) {
        session = sessionFactory.openSession();
        session.beginTransaction();
        session.flush();
        session.update(k);
        session.getTransaction().commit();
        session.close();
    }
}
