package DatabaseQueries;

import Entities.*;
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
public class ElementZamowieniaMethods {

    private SessionFactory sessionFactory;
    private Session session;
    private Transaction transaction;

    public ElementZamowieniaMethods() {
        this.sessionFactory = SingletonConnection.getSessionFactory();
    }

    /**
     * Pobiera elementy zamówień z bazy danych.
     *
     * @return lista wszystkich elementów zamówień
     */
    public List<ElementZamowienia> getElementyZamowienia() {
        session = sessionFactory.openSession();
        session.beginTransaction();
        session.flush();

        List<ElementZamowienia> list = session.createSQLQuery("select * from element_zamowienia").addEntity(ElementZamowienia.class).list();

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
    public boolean deleteElementZamowienia(ElementZamowienia elementZamowienia) {
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
    public ElementZamowienia saveElementZamowienia(int idZamowienie, int idProdukt, int ilosc) {
        session = sessionFactory.openSession();
        session.beginTransaction();
        session.flush();

        Produkt p = (Produkt) session.createSQLQuery("select * from produkt where id=\'" + idProdukt + "\'").addEntity(Produkt.class).getSingleResult();
        Zamowienie z = (Zamowienie) session.createSQLQuery("select * from zamowienie where id=\'" + idZamowienie + "\'").addEntity(Zamowienie.class).getSingleResult();

        ElementZamowienia ez = new ElementZamowienia(z, p, ilosc, Math.round(p.getCena()*ilosc*100.0)/100.0, p.getCena());

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
    public void updateElementZamowieniaZamowienie(ElementZamowienia ez, String idZamowienia) {
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
    public void updateElementZamowieniaProdukt(ElementZamowienia ez, String idProduktu) {
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
    public void updateElementZamowienia(ElementZamowienia ez) {
        session = sessionFactory.openSession();
        session.beginTransaction();
        session.flush();
        session.update(ez);
        session.getTransaction().commit();
        session.close();
    }
}
