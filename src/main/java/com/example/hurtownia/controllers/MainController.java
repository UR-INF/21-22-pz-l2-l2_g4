package com.example.hurtownia.controllers;

import com.example.hurtownia.authentication.LoginService;
import com.example.hurtownia.domain.customer.Customer;
import com.example.hurtownia.domain.customer.CustomerService;
import com.example.hurtownia.domain.order.Order;
import com.example.hurtownia.domain.order.OrderService;
import com.example.hurtownia.domain.orderitem.OrderItem;
import com.example.hurtownia.domain.orderitem.OrderItemService;
import com.example.hurtownia.domain.product.Product;
import com.example.hurtownia.domain.product.ProductService;
import com.example.hurtownia.domain.supplier.Supplier;
import com.example.hurtownia.domain.supplier.SupplierService;
import com.example.hurtownia.domain.user.User;
import com.example.hurtownia.domain.user.UserService;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    public CustomerService customerService;
    @Autowired
    public OrderService orderService;
    @Autowired
    public OrderItemService orderItemService;
    @Autowired
    public ProductService productService;
    @Autowired
    public SupplierService supplierService;
    @Autowired
    public UserService userService;
    @FXML
    private Text clockLabel, userNameLabel;
    private LoginService loginService;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //TODO: Panel logowania

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
        User user = User.builder()
                .name("imie")
                .surname("nazwisko")
                .email("email")
                .password("haslo")
                .phoneNumber("123456789")
                .isAdmin(true)
                .generatingReports(true)
                .grantingDiscounts(true)
                .build();
        userService.save(user);

        Supplier supplier = Supplier.builder()
                .name("nazwa")
                .email("email")
                .country("kraj")
                .place("miejscowosc")
                .street("ulica")
                .nip("nip")
                .build();
        supplierService.save(supplier);

        Customer customer = Customer.builder()
                .name("imie")
                .surname("nazwisko")
                .pesel("12345678911")
                .phoneNumber("123456789")
                .email("email")
                .place("miejscowosc")
                .street("ulica")
                .buildingNumber(10)
                .apartmentNumber(12)
                .build();
        customerService.save(customer);

        Product product = Product.builder()
                .supplier(supplier)
                .name("nazwa")
                .unitOfMeasurement("sztuka")
                .price(12.5)
                .country("kraj")
                .code("KOD")
                .color("czerwony")
                .number(50)
                .maxNumber(100)
                .build();
        productService.save(product);

        Order order = Order.builder()
                .customer(customer)
                .date("22-02-2022")
                .state("w przygotowaniu")
                .discount(0.2)
                .build();
        orderService.save(order);

        OrderItem orderItem = OrderItem.builder()
                .order(order)
                .product(product)
                .amount(10)
                .itemPrice(product.getPrice() * 10)
                .pricePerUnit(product.getPrice())
                .build();
        orderItemService.save(orderItem);
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
