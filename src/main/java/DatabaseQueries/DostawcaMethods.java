package DatabaseQueries;

import Entities.Dostawca;
import Singleton.SingletonConnection;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.List;

/**
 * Zawiera metody dla tabeli 'dostawca'.
 */
public class DostawcaMethods {

    private SessionFactory sessionFactory;
    private Session session;
    private Transaction transaction;

    public DostawcaMethods() {
        this.sessionFactory = SingletonConnection.getSessionFactory();
    }

    public List<Dostawca> getDostawcy() {
        session = sessionFactory.openSession();
        session.beginTransaction();
        session.flush();

        List<Dostawca> list = session.createSQLQuery("select * from dostawca").addEntity(Dostawca.class).list();

        session.getTransaction().commit();
        session.close();

        return list;
    }
}
