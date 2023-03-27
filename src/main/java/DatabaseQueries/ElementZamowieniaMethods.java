package DatabaseQueries;

import Entities.ElementZamowienia;
import Entities.Klient;
import Singleton.SingletonConnection;
import javafx.scene.control.Alert;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import javax.persistence.PersistenceException;
import java.util.List;

/**
 * Zawiera metody dla tabeli 'elementZamowienia'.
 */
public class ElementZamowieniaMethods {

    private SessionFactory sessionFactory;
    private Session session;
    private Transaction transaction;

    public ElementZamowieniaMethods() {
        this.sessionFactory = SingletonConnection.getSessionFactory();
    }

    /**
     * Pobiera elementy zamówień z bazy danych.
     *
     * @return lista wszystkich elementów zamówień
     */
    public List<ElementZamowienia> getElementyZamowienia() {
        session = sessionFactory.openSession();
        session.beginTransaction();
        session.flush();

        List<ElementZamowienia> list = session.createSQLQuery("select * from element_zamowienia").addEntity(ElementZamowienia.class).list();

        session.getTransaction().commit();
        session.close();

        return list;
    }


    /**
     * Pobiera wszystkie elementy zamówienia o podanym identyfikatorze.
     *
     * @param id id zamówienia
     * @return lista elementów zamówienia
     */
    public List<ElementZamowienia> getElementyZamowienia(int id) {
        session = sessionFactory.openSession();
        session.beginTransaction();
        session.flush();

        List<ElementZamowienia> list = session.createSQLQuery("select * from elementzamowienia where idZamowienie =" + id ).addEntity(ElementZamowienia.class).list();

        session.getTransaction().commit();
        session.close();

        return list;
    }

    /**
     * Usuwa element zamówienia z bazy danych.
     *
     * @param elementZamowienia
     * @return true - jeśli pomyślnie usunięto;
     *         false - jeśli wystąpiły błędy
     */
    public boolean deleteElementZamowienia(ElementZamowienia elementZamowienia) {
        session = sessionFactory.openSession();
        boolean result = false;

        try {
            transaction = session.beginTransaction();
            session.flush();
            session.delete(elementZamowienia);
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
}
