package com.example.hurtownia.domain.supplier;

import com.example.hurtownia.databaseaccess.SingletonConnection;
import javafx.scene.control.Alert;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.stereotype.Service;

import javax.persistence.PersistenceException;
import java.util.List;

/**
 * Zawiera metody dla tabeli 'dostawca'.
 */
@Service
public class SupplierService {

    private SessionFactory sessionFactory;
    private Session session;
    private Transaction transaction;

    public SupplierService() {
        this.sessionFactory = SingletonConnection.getSessionFactory();
    }

    /**
     * Pobiera wszystkich dostawców z bazy danych.
     *
     * @return lista wszystkich dostawców
     */
    public List<Supplier> getSuppliers() {
        session = sessionFactory.openSession();
        session.beginTransaction();
        session.flush();

        List<Supplier> list = session.createSQLQuery("select * from dostawca").addEntity(Supplier.class).list();

        session.getTransaction().commit();
        session.close();

        return list;
    }

    /**
     * Usuwa dostawcę z bazy danych.
     *
     * @param supplier
     * @return true - jeśli pomyślnie usunięto;
     * false - jeśli wystąpiły błędy
     */
    public boolean deleteSupplier(Supplier supplier) {
        session = sessionFactory.openSession();
        boolean result = false;

        try {
            transaction = session.beginTransaction();
            session.flush();
            session.delete(supplier);
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
     * @param country
     * @param place
     * @param street
     * @param name
     * @param nip
     * @return
     */
    public Supplier saveSupplier(String email, String country, String place, String street, String name, String nip) {
        session = sessionFactory.openSession();
        session.beginTransaction();
        session.flush();

        Supplier supplier = new Supplier(email, country, place, street, name, nip);

        session.save(supplier);

        session.getTransaction().commit();
        session.close();

        return supplier;
    }

    /**
     * Aktualizuje dostawcę.
     *
     * @param supplier
     */
    public void updateSupplier(Supplier supplier) {
        session = sessionFactory.openSession();
        session.beginTransaction();
        session.flush();
        session.update(supplier);
        session.getTransaction().commit();
        session.close();
    }

}
