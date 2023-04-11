package com.example.hurtownia.controllers;

import com.example.hurtownia.authentication.LoginService;
import com.example.hurtownia.domain.customer.CustomerCreateDTO;
import com.example.hurtownia.domain.customer.CustomerService;
import com.example.hurtownia.domain.order.OrderCreateDTO;
import com.example.hurtownia.domain.order.OrderService;
import com.example.hurtownia.domain.orderitem.OrderItemCreateDTO;
import com.example.hurtownia.domain.orderitem.OrderItemService;
import com.example.hurtownia.domain.product.ProductCreateDTO;
import com.example.hurtownia.domain.product.ProductService;
import com.example.hurtownia.domain.supplier.SupplierCreateDTO;
import com.example.hurtownia.domain.supplier.SupplierService;
import com.example.hurtownia.domain.user.UserCreateDTO;
import com.example.hurtownia.domain.user.UserService;
import javafx.application.Platform;
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
            String finalDate = date;
            Platform.runLater(() -> clockLabel.setText(finalDate));
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
        UserCreateDTO user = UserCreateDTO.builder()
                .name("imie")
                .surname("nazwisko")
                .email("email")
                .password("haslo")
                .phoneNumber("123456789")
                .isAdmin(Boolean.TRUE)
                .generatingReports(Boolean.TRUE)
                .grantingDiscounts(Boolean.TRUE)
                .build();
        userService.save(user);

        SupplierCreateDTO supplier = SupplierCreateDTO.builder()
                .name("nazwa")
                .email("email")
                .country("kraj")
                .place("miejscowosc")
                .street("ulica")
                .nip("nip")
                .build();
        supplierService.save(supplier);

        CustomerCreateDTO customer = CustomerCreateDTO.builder()
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

        ProductCreateDTO product = ProductCreateDTO.builder()
                .supplierId(1L)
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

        OrderCreateDTO order = OrderCreateDTO.builder()
                .customerId(1L)
                .date("22-02-2022")
                .state("w przygotowaniu")
                .discount(0.2)
                .build();
        orderService.save(order);

        OrderItemCreateDTO orderItem = OrderItemCreateDTO.builder()
                .orderid(1L)
                .productId(1L)
                .amount(10)
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
