package DatabaseAccess;

import Singleton.SingletonConnection;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

/**
 * Sprawdza połączenie z bazą danych
 */
public class DbAccess {

    public static boolean CONNECTION;
    private static SessionFactory sessionFactory;
    private static Session session;

    static {
        try {
            sessionFactory = SingletonConnection.getSessionFactory();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Pobiera nazwe bazy danych, z którą się łączy.
     *
     * @return name nazwa bazy danych, jeśi nawiązano połączenie
     */
    public String getDatabaseName() {
        String name;
        try {
            session = sessionFactory.openSession();
            session.beginTransaction();
            name = session.createSQLQuery("SELECT DATABASE()").getSingleResult().toString();
            session.getTransaction().commit();
            session.close();
        } catch (Exception e) {
            CONNECTION = false;
            return "błąd połączenia";
        }
        CONNECTION = true;
        return name;
    }

}
