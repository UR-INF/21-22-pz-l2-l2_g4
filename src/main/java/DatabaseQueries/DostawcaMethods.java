package DatabaseQueries;

import Entities.Dostawca;
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
public class DostawcaMethods {

    private SessionFactory sessionFactory;
    private Session session;
    private Transaction transaction;

    public DostawcaMethods() {
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

}
