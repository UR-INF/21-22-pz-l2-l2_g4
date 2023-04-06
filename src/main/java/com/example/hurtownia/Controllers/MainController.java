package com.example.hurtownia.controllers;

import com.example.hurtownia.authentication.LoginService;
import com.example.hurtownia.databaseaccess.DbAccess;
import com.example.hurtownia.databaseaccess.SingletonConnection;
import com.example.hurtownia.domain.customer.Customer;
import com.example.hurtownia.domain.order.Order;
import com.example.hurtownia.domain.orderitem.OrderItem;
import com.example.hurtownia.domain.product.Product;
import com.example.hurtownia.domain.supplier.Supplier;
import com.example.hurtownia.domain.user.User;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Controller;

import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.ResourceBundle;

/**
 * Kontroler głównego okna aplikacji.
 */
@Controller
public class MainController implements Initializable {

    /*
    @FXML private UserController userController;
    @FXML private CustomerController customerController;
    @FXML private OrderController orderController;
    @FXML private OrderItemController orderItemController;
    @FXML private ProductController productController;
    @FXML private SupplierController supplierController;
    */

    @FXML
    private Text clockLabel, userNameLabel;

    private DbAccess dbAccess;
    private LoginService loginService;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //TODO: Panel logowania
        //paneLogowanie.toFront();

        dbAccess = new DbAccess();
        dbAccess.getDatabaseName();
        loginService = new LoginService();
        loginService.logIn("admin", "1234");
        userNameLabel.setText(loginService.getLogin());

        insertData();

        new Thread(() -> runClock()).start();
    }

    /**
     * Odpowiedzialna za działanie zegara.
     */
    private void runClock() {
        DateFormat outputformat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        String date;

        while (true) {
            date = outputformat.format(Calendar.getInstance().getTime());
            clockLabel.setText(date);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Testowy insert danych.
     */
    private void insertData() {
        SessionFactory sessionFactory = SingletonConnection.getSessionFactory();
        Session session;

        session = sessionFactory.openSession();
        session.beginTransaction();
        session.flush();

        session.save(new User("imie", "nazwisko", "email", "haslo", "123456789", 1, 1, 1));
        User u = (User) session.createSQLQuery("select * from uzytkownik where id='" + 1 + "'").addEntity(User.class).getSingleResult();

        session.save(new Supplier("email", "chiny", "pekin", "ulica", "nazwa", "NIP"));
        Supplier d = (Supplier) session.createSQLQuery("select * from dostawca where id='" + 1 + "'").addEntity(Supplier.class).getSingleResult();

        session.save(new Customer("imie", "nazwisko", "pesel", "nrtel", "email", "miejscowosc", "ulica", 10, 12));
        Customer k = (Customer) session.createSQLQuery("select * from klient where id='" + 1 + "'").addEntity(Customer.class).getSingleResult();

        session.save(new Product(d, "nazwa", "sztuka", 22.56, "Polska", "AS2345", "brak", 80, 100));
        Product p = (Product) session.createSQLQuery("select * from produkt where id='" + 1 + "'").addEntity(Product.class).getSingleResult();

        session.save(new Order(k, "22-02-2022", "złożone", 0.2));
        Order z = (Order) session.createSQLQuery("select * from zamowienie where id='" + 1 + "'").addEntity(Order.class).getSingleResult();

        session.save(new OrderItem(z, p, 10, p.getPrice() * 10, p.getPrice()));

        session.getTransaction().commit();
        session.close();
    }

    /**
     * Obsługuje przycisk zalogowania.
     *
     * @param event
     */
    @FXML
    public void btnLogInClicked(MouseEvent event) {
        //TODO: obsłużyć logowanie
    }

    /**
     * Obsługuje przycisk wylogowania.
     *
     * @param event
     */
    @FXML
    public void btnLogOutClicked(MouseEvent event) {
        //TODO: obsłużyć wylogowanie
    }

    /**
     * Obsługuje przycisk wyjścia z aplikacji.
     *
     * @param event
     */
    @FXML
    public void btnExitClicked(MouseEvent event) {
        //TODO: obsłużyć wyjście z aplikacji (logout and close)
    }
}
