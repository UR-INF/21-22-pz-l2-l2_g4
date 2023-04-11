package com.example.hurtownia.domain.order;

import com.example.hurtownia.databaseaccess.SingletonConnection;
import com.example.hurtownia.domain.customer.Customer;
import javafx.scene.control.Alert;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.stereotype.Service;

import javax.persistence.PersistenceException;
import java.util.List;

/**
 * Zawiera metody dla tabeli 'zamowienie'.
 */
@Service
public class OrderService {

    private SessionFactory sessionFactory;
    private Session session;
    private Transaction transaction;

    public OrderService() {
        this.sessionFactory = SingletonConnection.getSessionFactory();
    }

    /**
     * Pobiera wszystkie zamówienia z bazy danych.
     *
     * @return lista wszystkich zamówień
     */
    public List<Order> getOrder() {
        session = sessionFactory.openSession();
        session.beginTransaction();
        session.flush();

        List<Order> list = session.createSQLQuery("select * from zamowienie").addEntity(Order.class).list();

        session.getTransaction().commit();
        session.close();

        return list;
    }

    /**
     * Usuwa zamówienie z bazy danych.
     *
     * @param order
     * @return true - jeśli pomyślnie usunięto;
     * false - jeśli wystąpiły błędy
     */
    public boolean deleteOrder(Order order) {
        session = sessionFactory.openSession();
        boolean result = false;

        try {
            transaction = session.beginTransaction();
            session.flush();
            session.delete(order);
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
     * @param idCustomer
     * @param date
     * @param status
     * @param discount
     * @return
     */
    public Order saveOrder(int idCustomer, String date, String status, Double discount) {
        session = sessionFactory.openSession();
        session.beginTransaction();
        session.flush();

        Customer customer = (Customer) session.createSQLQuery("select * from klient where id=\'" + idCustomer + "\'").addEntity(Customer.class).getSingleResult();
        Order order = new Order(customer, date, status, discount);

        session.save(order);

        session.getTransaction().commit();
        session.close();

        return order;
    }

    /**
     * Aktualizuje zamówienie.
     *
     * @param order
     */
    public void updateOrder(Order order) {
        session = sessionFactory.openSession();
        session.beginTransaction();
        session.flush();
        session.update(order);
        session.getTransaction().commit();
        session.close();
    }

    /**
     * Aktualizuje klienta zamówienia.
     *
     * @param order
     * @param idCustomer
     */
    public void updateOrderCustomer(Order order, String idCustomer) {
        session = sessionFactory.openSession();
        session.beginTransaction();
        session.flush();

        Customer customer = (Customer) session.createSQLQuery("select * from klient where id=\'" + idCustomer + "\'").addEntity(Customer.class).getSingleResult();
        order.setCustomer(customer);
        session.update(customer);

        session.getTransaction().commit();
        session.close();
    }

}
