package DatabaseQueries;

import Entities.Uzytkownik;
import Singleton.SingletonConnection;
import javafx.scene.control.Alert;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import javax.persistence.PersistenceException;
import java.util.List;

/**
 * Zawiera metody dla tabeli 'uzytkownik'.
 */
public class UzytkownikMethods {

    private SessionFactory sessionFactory;
    private Session session;
    private Transaction transaction;

    public UzytkownikMethods() {
        this.sessionFactory = SingletonConnection.getSessionFactory();
    }

    /**
     * Pobiera wszystkich użytkowników z bazy danych.
     *
     * @return lista wszystkich użytkowników
     */
    public List<Uzytkownik> getUzytkownicy() {
        session = sessionFactory.openSession();
        session.beginTransaction();
        session.flush();

        List<Uzytkownik> list = session.createSQLQuery("select * from uzytkownik").addEntity(Uzytkownik.class).list();

        session.getTransaction().commit();
        session.close();

        return list;
    }

    /**
     * Usuwa użytkownika z bazy danych.
     *
     * @param uzytkownik
     * @return true - jeśli pomyślnie usunięto;
     *         false - jeśli wystąpiły błędy
     */
    public boolean deleteUzytkownik(Uzytkownik uzytkownik) {
        session = sessionFactory.openSession();
        boolean result = false;

        try {
            transaction = session.beginTransaction();
            session.flush();
            session.delete(uzytkownik);
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
