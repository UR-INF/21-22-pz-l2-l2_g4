package DatabaseQueries;

import Entities.Klient;
import Singleton.SingletonConnection;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.List;

/**
 * Zawiera metody dla tabeli 'klient'.
 */
public class KlientMethods {

    private SessionFactory sessionFactory;
    private Session session;
    private Transaction transaction;

    public KlientMethods() {
        this.sessionFactory = SingletonConnection.getSessionFactory();
    }

    public List<Klient> getKlienci() {
        session = sessionFactory.openSession();
        session.beginTransaction();
        session.flush();

        List<Klient> list = session.createSQLQuery("select * from klient").addEntity(Klient.class).list();

        session.getTransaction().commit();
        session.close();

        return list;
    }
}
