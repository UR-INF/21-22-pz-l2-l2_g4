package DatabaseAccess;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import Singleton.SingletonConnection;

/**
 * Sprawdza połączenie z bazą danych
 */
public class DbAccess {

    private static SessionFactory sessionFactory;
    private static Session session;
    public static boolean CONNECTION;
    
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
