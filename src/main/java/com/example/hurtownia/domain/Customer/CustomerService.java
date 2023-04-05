package com.example.hurtownia.domain.customer;

import com.example.hurtownia.databaseaccess.SingletonConnection;
import javafx.scene.control.Alert;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import javax.persistence.PersistenceException;
import java.util.List;

/**
 * Zawiera metody dla tabeli 'klient'.
 */
public class CustomerService {

    private SessionFactory sessionFactory;
    private Session session;
    private Transaction transaction;

    public CustomerService() {
        this.sessionFactory = SingletonConnection.getSessionFactory();
    }

    /**
     * Pobiera wszystkich klientów z bazy danych.
     *
     * @return lista wszystkich klientów
     */
    public List<Customer> getCustomers() {
        session = sessionFactory.openSession();
        session.beginTransaction();
        session.flush();

        List<Customer> list = session.createSQLQuery("select * from klient").addEntity(Customer.class).list();

        session.getTransaction().commit();
        session.close();

        return list;
    }

    /**
     * Usuwa klienta z bazy danych.
     *
     * @param customer
     * @return true - jeśli pomyślnie usunięto;
     * false - jeśli wystąpiły błędy
     */
    public boolean deleteCustomer(Customer customer) {
        session = sessionFactory.openSession();
        boolean result = false;

        try {
            transaction = session.beginTransaction();
            session.flush();
            session.delete(customer);
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
     * @param name
     * @param surname
     * @param pesel
     * @param phoneNumber
     * @param email
     * @param place
     * @param street
     * @param apartmentNumber
     * @param buildingNumber
     * @return
     */
    public Customer saveCustomer(String name, String surname, String pesel, String phoneNumber, String email, String place, String street, int apartmentNumber, int buildingNumber) {
        session = sessionFactory.openSession();
        session.beginTransaction();
        session.flush();

        Customer customer = new Customer(name,  surname,  pesel,  phoneNumber,  email,  place,  street,  apartmentNumber,  buildingNumber);

        session.save(customer);

        session.getTransaction().commit();
        session.close();

        return customer;
    }

    /**
     * Aktualizuje klienta.
     *
     * @param customer
     */
    public void updateCustomer(Customer customer) {
        session = sessionFactory.openSession();
        session.beginTransaction();
        session.flush();
        session.update(customer);
        session.getTransaction().commit();
        session.close();
    }
}
