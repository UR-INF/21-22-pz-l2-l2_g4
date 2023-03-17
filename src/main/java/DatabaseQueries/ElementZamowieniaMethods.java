package DatabaseQueries;

import Entities.ElementZamowienia;
import Singleton.SingletonConnection;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.List;

/**
 * Zawiera metody dla tabeli 'elementZamowienia'.
 */
public class ElementZamowieniaMethods {

    private SessionFactory sessionFactory;
    private Session session;
    private Transaction transaction;

    public ElementZamowieniaMethods() {
        this.sessionFactory = SingletonConnection.getSessionFactory();
    }

    public List<ElementZamowienia> getElementyZamowienia() {
        session = sessionFactory.openSession();
        session.beginTransaction();
        session.flush();

        List<ElementZamowienia> list = session.createSQLQuery("select * from elementzamowienia").addEntity(ElementZamowienia.class).list();

        session.getTransaction().commit();
        session.close();

        return list;
    }

    public List<ElementZamowienia> getElementyZamowienia(int id) {
        session = sessionFactory.openSession();
        session.beginTransaction();
        session.flush();

        List<ElementZamowienia> list = session.createSQLQuery("select * from elementzamowienia where idZamowienie =" + id ).addEntity(ElementZamowienia.class).list();

        session.getTransaction().commit();
        session.close();

        return list;
    }
}
