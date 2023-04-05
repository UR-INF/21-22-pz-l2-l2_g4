package com.example.hurtownia.domain.orderitem;

import com.example.hurtownia.databaseaccess.SingletonConnection;
import com.example.hurtownia.domain.order.Order;
import com.example.hurtownia.domain.product.Product;
import javafx.scene.control.Alert;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import javax.persistence.PersistenceException;
import java.util.List;

/**
 * Zawiera metody dla tabeli 'element_zamowienia'.
 */
public class OrderItemService {

    private SessionFactory sessionFactory;
    private Session session;
    private Transaction transaction;

    public OrderItemService() {
        this.sessionFactory = SingletonConnection.getSessionFactory();
    }

    /**
     * Pobiera elementy zamówień z bazy danych.
     *
     * @return lista wszystkich elementów zamówień
     */
    public List<OrderItem> getOrderItems() {
        session = sessionFactory.openSession();
        session.beginTransaction();
        session.flush();

        List<OrderItem> list = session.createSQLQuery("select * from element_zamowienia").addEntity(OrderItem.class).list();

        session.getTransaction().commit();
        session.close();

        return list;
    }

    /**
     * Pobiera elementy zamówienia o podanym id.
     *
     * @return lista elementów zamówienia o podanym id
     */
    public List<OrderItem> getOrderItem(int idOrder) {
        session = sessionFactory.openSession();
        session.beginTransaction();
        session.flush();

        List<OrderItem> list = session.createSQLQuery("select * from element_zamowienia where idZamowienie=\'" + idOrder +"\'").addEntity(OrderItem.class).list();

        session.getTransaction().commit();
        session.close();

        return list;
    }

    /**
     * Usuwa element zamówienia z bazy danych.
     *
     * @param orderItem
     * @return true - jeśli pomyślnie usunięto;
     * false - jeśli wystąpiły błędy
     */
    public boolean deleteOrderItem(OrderItem orderItem) {
        session = sessionFactory.openSession();
        boolean result = false;

        try {
            transaction = session.beginTransaction();
            session.flush();
            session.delete(orderItem);
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
     * Dodaje nowy element zamówienia.
     *
     * @param idOrder
     * @param idProduct
     * @param number
     * @return
     */
    public OrderItem saveOrderItem(int idProduct, int idOrder, int number) {
        session = sessionFactory.openSession();
        session.beginTransaction();
        session.flush();

        Product product = (Product) session.createSQLQuery("select * from produkt where id=\'" + idProduct + "\'").addEntity(Product.class).getSingleResult();
        Order order = (Order) session.createSQLQuery("select * from zamowienie where id=\'" + idOrder + "\'").addEntity(Order.class).getSingleResult();

        OrderItem orderItem = new OrderItem(order, product, number, Math.round(product.getPrice()*number*100.0)/100.0, product.getPrice());

        session.save(orderItem);

        session.getTransaction().commit();
        session.close();

        return orderItem;
    }

    /**
     * Aktualizuje zamówienie elementu zamówienia.
     *
     * @param orderItem
     * @param idOrder
     */
    public void updateOrderItemOrder(OrderItem orderItem, String idOrder) {
        session = sessionFactory.openSession();
        session.beginTransaction();
        session.flush();

        Order order = (Order) session.createSQLQuery("select * from zamowienie where id=\'" + idOrder + "\'").addEntity(Order.class).getSingleResult();
        orderItem.setOrder(order);
        session.update(orderItem);

        session.getTransaction().commit();
        session.close();
    }

    /**
     * Aktualizuje produkt elementu zamówienia.
     *
     * @param orderItem
     * @param idProduct
     */
    public void updateOrderItemProduct(OrderItem orderItem, String idProduct) {
        session = sessionFactory.openSession();
        session.beginTransaction();
        session.flush();

        Product product = (Product) session.createSQLQuery("select * from produkt where id=\'" + idProduct + "\'").addEntity(Product.class).getSingleResult();
        orderItem.setProduct(product);
        orderItem.setPricePerUnit(product.getPrice());
        orderItem.setItemPrice(orderItem.getPricePerUnit()*orderItem.getNumber());
        session.update(orderItem);

        session.getTransaction().commit();
        session.close();
    }

    /**
     * Aktualizuje element zamówienia.
     *
     * @param orderItem
     */
    public void updateOrderItem(OrderItem orderItem) {
        session = sessionFactory.openSession();
        session.beginTransaction();
        session.flush();
        session.update(orderItem);
        session.getTransaction().commit();
        session.close();
    }

}
