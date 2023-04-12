package com.example.hurtownia.controllers;

import com.example.hurtownia.authentication.LoginService;
import com.example.hurtownia.domain.customer.CustomerService;
import com.example.hurtownia.domain.order.OrderService;
import com.example.hurtownia.domain.orderitem.OrderItemService;
import com.example.hurtownia.domain.product.ProductService;
import com.example.hurtownia.domain.supplier.SupplierService;
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
