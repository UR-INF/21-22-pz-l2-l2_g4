package DatabaseQueries;

import Entities.Zamowienie;
import Singleton.SingletonConnection;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.List;

/**
 * Zawiera metody dla tabeli 'zamowienie'.
 */
public class ZamowienieMethods {

    private SessionFactory sessionFactory;
    private Session session;
    private Transaction transaction;

    public ZamowienieMethods() {
        this.sessionFactory = SingletonConnection.getSessionFactory();
    }

    public List<Zamowienie> getZamowienia() {
        session = sessionFactory.openSession();
        session.beginTransaction();
        session.flush();

        List<Zamowienie> list = session.createSQLQuery("select * from zamowienie").addEntity(Zamowienie.class).list();

        session.getTransaction().commit();
        session.close();

        return list;
    }
}
