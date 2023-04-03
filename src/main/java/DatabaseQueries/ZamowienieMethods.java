package DatabaseQueries;

import Entities.Dostawca;
import Entities.Klient;
import Entities.Produkt;
import Entities.Zamowienie;
import Singleton.SingletonConnection;
import javafx.scene.control.Alert;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import javax.persistence.PersistenceException;
import java.util.List;

/**
 * Zawiera metody dla tabeli 'zamowienie'.
 */
public class ZamowienieMethods {

    private SessionFactory sessionFactory;
    private Session session;
    private Transaction transaction;

    public ZamowienieMethods() {
        this.sessionFactory = SingletonConnection.getSessionFactory();
    }

    /**
     * Pobiera wszystkie zamówienia z bazy danych.
     *
     * @return lista wszystkich zamówień
     */
    public List<Zamowienie> getZamowienia() {
        session = sessionFactory.openSession();
        session.beginTransaction();
        session.flush();

        List<Zamowienie> list = session.createSQLQuery("select * from zamowienie").addEntity(Zamowienie.class).list();

        session.getTransaction().commit();
        session.close();

        return list;
    }

    /**
     * Zwraca zamówienie o podanym id.
     *
     * @param id
     * @return
     */
    public Zamowienie getZamowienie(String id) {
        session = sessionFactory.openSession();
        session.beginTransaction();
        session.flush();

        Zamowienie z = (Zamowienie) session.createSQLQuery("select * from zamowienie where id=\'" + id + "\'").addEntity(Zamowienie.class).getSingleResult();

        session.getTransaction().commit();
        session.close();

        return z;
    }

    /**
     * Usuwa zamówienie z bazy danych.
     *
     * @param zamowienie
     * @return true - jeśli pomyślnie usunięto;
     * false - jeśli wystąpiły błędy
     */
    public boolean deleteZamowienie(Zamowienie zamowienie) {
        session = sessionFactory.openSession();
        boolean result = false;

        try {
            transaction = session.beginTransaction();
            session.flush();
            session.delete(zamowienie);
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
     * Dodaje zamowienie.
     *
     * @param idKlient
     * @param data
     * @param stanZamowienia
     * @param rabat
     * @return
     */
    public Zamowienie saveZamowienie(int idKlient, String data, String stanZamowienia, Double rabat) {
        session = sessionFactory.openSession();
        session.beginTransaction();
        session.flush();

        Klient k = (Klient) session.createSQLQuery("select * from klient where id=\'" + idKlient + "\'").addEntity(Klient.class).getSingleResult();
        Zamowienie z = new Zamowienie(k, data, stanZamowienia, rabat);

        session.save(z);

        session.getTransaction().commit();
        session.close();

        return z;
    }

    /**
     * Aktualizuje zamówienie.
     *
     * @param z
     */
    public void updateZamowienie(Zamowienie z) {
        session = sessionFactory.openSession();
        session.beginTransaction();
        session.flush();
        session.update(z);
        session.getTransaction().commit();
        session.close();
    }

    /**
     * Aktualizuje klienta zamówienia.
     *
     * @param z
     * @param idKlient
     */
    public void updateZamowienieKlient(Zamowienie z, String idKlient) {
        session = sessionFactory.openSession();
        session.beginTransaction();
        session.flush();

        Klient k = (Klient) session.createSQLQuery("select * from klient where id=\'" + idKlient + "\'").addEntity(Klient.class).getSingleResult();
        z.setKlient(k);
        session.update(k);

        session.getTransaction().commit();
        session.close();
    }

}
