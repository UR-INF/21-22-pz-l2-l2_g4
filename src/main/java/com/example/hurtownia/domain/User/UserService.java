package com.example.hurtownia.domain.user;

import com.example.hurtownia.databaseaccess.SingletonConnection;
import javafx.scene.control.Alert;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.stereotype.Service;

import javax.persistence.PersistenceException;
import java.util.List;

/**
 * Zawiera metody dla tabeli 'uzytkownik'.
 */
@Service
public class UserService {

    private SessionFactory sessionFactory;
    private Session session;
    private Transaction transaction;

    public UserService() {
        this.sessionFactory = SingletonConnection.getSessionFactory();
    }

    /**
     * Pobiera wszystkich użytkowników z bazy danych.
     *
     * @return lista wszystkich użytkowników
     */
    public List<User> getUsers() {
        session = sessionFactory.openSession();
        session.beginTransaction();
        session.flush();

        List<User> list = session.createSQLQuery("select * from uzytkownik").addEntity(User.class).list();

        session.getTransaction().commit();
        session.close();

        return list;
    }

    /**
     * Usuwa użytkownika z bazy danych.
     *
     * @param user
     * @return true - jeśli pomyślnie usunięto;
     * false - jeśli wystąpiły błędy
     */
    public boolean deleteUser(User user) {
        session = sessionFactory.openSession();
        boolean result = false;

        try {
            transaction = session.beginTransaction();
            session.flush();
            session.delete(user);
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
     * Dodaje nowego użytkownika.
     *
     * @param name
     * @param surname
     * @param email
     * @param password
     * @param phoneNumber
     * @param isAdmin
     * @param generatingReports
     * @param grantingDiscounts
     * @return
     */
    public User saveUser(String name, String surname, String email, String password, String phoneNumber, int isAdmin, int generatingReports, int grantingDiscounts) {
        session = sessionFactory.openSession();
        session.beginTransaction();
        session.flush();

        User user = new User(name, surname, email, password, phoneNumber, isAdmin, generatingReports, grantingDiscounts);

        session.save(user);

        session.getTransaction().commit();
        session.close();

        return user;
    }

    /**
     * Aktualizuje użytkownika.
     *
     * @param user
     */
    public void updateUser(User user) {
        session = sessionFactory.openSession();
        session.beginTransaction();
        session.flush();
        session.update(user);
        session.getTransaction().commit();
        session.close();
    }
}
