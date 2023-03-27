package DatabaseQueries;

import Entities.Produkt;
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
public class ProduktMethods {

    private SessionFactory sessionFactory;
    private Session session;
    private Transaction transaction;

    public ProduktMethods() {
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
     * Usuwa produkt z bazy danych.
     *
     * @param produkt
     * @return true - jeśli pomyślnie usunięto;
     *         false - jeśli wystąpiły błędy
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
}
