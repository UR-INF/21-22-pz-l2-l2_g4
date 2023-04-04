package domain.OrderItem;

import domain.Order.Zamowienie;
import domain.Product.Produkt;
import Singleton.SingletonConnection;
import javafx.scene.control.Alert;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import javax.persistence.PersistenceException;
import java.util.List;

/**
 * Zawiera metody dla tabeli 'element_zamowienia'.
 */
public class ElementZamowieniaService {

    private SessionFactory sessionFactory;
    private Session session;
    private Transaction transaction;

    public ElementZamowieniaService() {
        this.sessionFactory = SingletonConnection.getSessionFactory();
    }

    /**
     * Pobiera elementy zamówień z bazy danych.
     *
     * @return lista wszystkich elementów zamówień
     */
    public List<OrderItem> getElementyZamowienia() {
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
    public List<OrderItem> getElementZamowienia(int idZamowienie) {
        session = sessionFactory.openSession();
        session.beginTransaction();
        session.flush();

        List<OrderItem> list = session.createSQLQuery("select * from element_zamowienia where idZamowienie=\'" + idZamowienie +"\'").addEntity(OrderItem.class).list();

        session.getTransaction().commit();
        session.close();

        return list;
    }

    /**
     * Usuwa element zamówienia z bazy danych.
     *
     * @param elementZamowienia
     * @return true - jeśli pomyślnie usunięto;
     * false - jeśli wystąpiły błędy
     */
    public boolean deleteElementZamowienia(OrderItem elementZamowienia) {
        session = sessionFactory.openSession();
        boolean result = false;

        try {
            transaction = session.beginTransaction();
            session.flush();
            session.delete(elementZamowienia);
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
     * @param idZamowienie
     * @param idProdukt
     * @param ilosc
     * @return
     */
    public OrderItem saveElementZamowienia(int idProdukt, int idZamowienie, int ilosc) {
        session = sessionFactory.openSession();
        session.beginTransaction();
        session.flush();

        Produkt p = (Produkt) session.createSQLQuery("select * from produkt where id=\'" + idProdukt + "\'").addEntity(Produkt.class).getSingleResult();
        Zamowienie z = (Zamowienie) session.createSQLQuery("select * from zamowienie where id=\'" + idZamowienie + "\'").addEntity(Zamowienie.class).getSingleResult();

        OrderItem ez = new OrderItem(z, p, ilosc, Math.round(p.getCena()*ilosc*100.0)/100.0, p.getCena());

        session.save(ez);

        session.getTransaction().commit();
        session.close();

        return ez;
    }

    /**
     * Aktualizuje zamówienie elementu zamówienia.
     *
     * @param ez
     * @param idZamowienia
     */
    public void updateElementZamowieniaZamowienie(OrderItem ez, String idZamowienia) {
        session = sessionFactory.openSession();
        session.beginTransaction();
        session.flush();

        Zamowienie z = (Zamowienie) session.createSQLQuery("select * from zamowienie where id=\'" + idZamowienia + "\'").addEntity(Zamowienie.class).getSingleResult();
        ez.setZamowienie(z);
        session.update(ez);

        session.getTransaction().commit();
        session.close();
    }

    /**
     * Aktualizuje produkt elementu zamówienia.
     *
     * @param ez
     * @param idProduktu
     */
    public void updateElementZamowieniaProdukt(OrderItem ez, String idProduktu) {
        session = sessionFactory.openSession();
        session.beginTransaction();
        session.flush();

        Produkt p = (Produkt) session.createSQLQuery("select * from produkt where id=\'" + idProduktu + "\'").addEntity(Produkt.class).getSingleResult();
        ez.setProdukt(p);
        ez.setCenaZaJednostke(p.getCena());
        ez.setCenaElementu(ez.getCenaZaJednostke()*ez.getIlosc());
        session.update(ez);

        session.getTransaction().commit();
        session.close();
    }

    /**
     * Aktualizuje element zamówienia.
     *
     * @param ez
     */
    public void updateElementZamowienia(OrderItem ez) {
        session = sessionFactory.openSession();
        session.beginTransaction();
        session.flush();
        session.update(ez);
        session.getTransaction().commit();
        session.close();
    }
}
