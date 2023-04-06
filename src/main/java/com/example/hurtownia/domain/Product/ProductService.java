package com.example.hurtownia.domain.product;

import com.example.hurtownia.databaseaccess.SingletonConnection;
import com.example.hurtownia.domain.supplier.Supplier;
import javafx.scene.control.Alert;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.stereotype.Service;

import javax.persistence.PersistenceException;
import java.util.List;

/**
 * Zawiera metody dla tabeli 'produkt'.
 */
@Service
public class ProductService {

    private final SessionFactory sessionFactory;
    private Session session;
    private Transaction transaction;

    public ProductService() {
        this.sessionFactory = SingletonConnection.getSessionFactory();
    }

    /**
     * Pobiera wszystkie produkty z bazy danych.
     *
     * @return lista wszystkich produktów
     */
    public List<Product> getProducts() {
        session = sessionFactory.openSession();
        session.beginTransaction();
        session.flush();

        List<Product> list = session.createSQLQuery("select * from produkt").addEntity(Product.class).list();

        session.getTransaction().commit();
        session.close();

        return list;
    }

    /**
     * Usuwa produkt z bazy danych.
     *
     * @param product
     * @return true - jeśli pomyślnie usunięto;
     * false - jeśli wystąpiły błędy
     */
    public boolean deleteProduct(Product product) {
        session = sessionFactory.openSession();
        boolean result = false;

        try {
            transaction = session.beginTransaction();
            session.flush();
            session.delete(product);
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
     * Dodaje produkt.
     *
     * @param idSupplier
     * @param name
     * @param unitOfMeasurement
     * @param price
     * @param country
     * @param code
     * @param color
     * @param number
     * @param maxNumber
     * @return
     */
    public Product saveProduct(int idSupplier, String name, String unitOfMeasurement, Double price, String country, String code, String color, int number, int maxNumber) {
        session = sessionFactory.openSession();
        session.beginTransaction();
        session.flush();

        Supplier supplier = (Supplier) session.createSQLQuery("select * from dostawca where id=\'" + idSupplier + "\'").addEntity(Supplier.class).getSingleResult();
        Product product = new Product(supplier, name, unitOfMeasurement, price, country, code, color, number, maxNumber);

        session.save(product);

        session.getTransaction().commit();
        session.close();

        return product;
    }

    /**
     * Aktualizuje dostawcę produktu.
     *
     * @param product
     * @param idSupplier
     */
    public void updateProductSupplier(Product product, String idSupplier) {
        session = sessionFactory.openSession();
        session.beginTransaction();
        session.flush();

        Supplier supplier = (Supplier) session.createSQLQuery("select * from dostawca where id=\'" + idSupplier + "\'").addEntity(Supplier.class).getSingleResult();
        product.setSupplier(supplier);
        session.update(product);

        session.getTransaction().commit();
        session.close();
    }

    /**
     * Aktualizuje produkt.
     *
     * @param product
     */
    public void updateProduct(Product product) {
        session = sessionFactory.openSession();
        session.beginTransaction();
        session.flush();
        session.update(product);
        session.getTransaction().commit();
        session.close();
    }
}
