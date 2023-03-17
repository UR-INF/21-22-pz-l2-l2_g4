package DatabaseQueries;

import Entities.Produkt;
import Singleton.SingletonConnection;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.List;

/**
 * Zawiera metody dla tabeli 'produkt'.
 */
public class ProduktMethods {

    private SessionFactory sessionFactory;
    private Session session;
    private Transaction transaction;

    public ProduktMethods() {
        this.sessionFactory = SingletonConnection.getSessionFactory();
    }

    public List<Produkt> getProdukty() {
        session = sessionFactory.openSession();
        session.beginTransaction();
        session.flush();

        List<Produkt> list = session.createSQLQuery("select * from produkt").addEntity(Produkt.class).list();

        session.getTransaction().commit();
        session.close();

        return list;
    }
}
