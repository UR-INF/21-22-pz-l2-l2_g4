package com.example.hurtownia.controllers;

import com.example.hurtownia.authentication.LoginService;
import com.example.hurtownia.domain.customer.CustomerController;
import com.example.hurtownia.domain.customer.CustomerService;
import com.example.hurtownia.domain.order.OrderController;
import com.example.hurtownia.domain.order.OrderService;
import com.example.hurtownia.domain.orderitem.OrderItemController;
import com.example.hurtownia.domain.orderitem.OrderItemService;
import com.example.hurtownia.domain.product.ProductController;
import com.example.hurtownia.domain.product.ProductService;
import com.example.hurtownia.domain.supplier.SupplierController;
import com.example.hurtownia.domain.supplier.SupplierService;
import com.example.hurtownia.domain.user.User;
import com.example.hurtownia.domain.user.UserService;
import com.example.hurtownia.domain.user.request.UserCreateRequest;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import javax.naming.OperationNotSupportedException;
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
    public TabPane tabPane;
    @FXML
    public Tab customerTab, orderTab, userTab;
    @FXML
    private OrderController orderTabContentController;
    @FXML
    private CustomerController customerTabContentController;
    @FXML
    private OrderItemController orderItemTabContentController;
    @FXML
    private ProductController productTabContentController;
    @FXML
    private SupplierController supplierTabContentController;
    @FXML
    private Text clockLabel, userNameLabel, loginErrorLabel;;
    @FXML
    private TextField loginTextField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private AnchorPane loginPane;

    @Autowired
    private LoginService loginService;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        UserCreateRequest userCreateRequest = UserCreateRequest.builder()
                .name("name")
                .surname("surname")
                .email("admin")
                .password("admin")
                .phoneNumber("phoneNumber")
                .isAdmin(Boolean.TRUE)
                .generatingReports(Boolean.TRUE)
                .grantingDiscounts(Boolean.TRUE)
                .build();
        try {
            userService.create(userCreateRequest);
        } catch (UnsupportedOperationException e) {
        }

        new Thread(this::runClock).start();
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
     * Blokuje pewne funkcje aplikacji w zależności od uprawnień zalogowanego użytkownika.
     *
     * @param user
     */
    private void checkPermissions(User user) {
        if (Boolean.FALSE.equals(user.getIsAdmin())) {
            userTab.setDisable(true);
            SingleSelectionModel<Tab> selectionModel = tabPane.getSelectionModel();
            selectionModel.select(customerTab);
        }
        if (Boolean.FALSE.equals(user.getGrantingDiscounts())) {
            orderTabContentController.disableGrantingDiscounds();
        }
        if (Boolean.FALSE.equals(user.getGeneratingReports())) {
            customerTabContentController.disableGeneratingReports();
            orderTabContentController.disableGeneratingReports();
            orderItemTabContentController.disableGeneratingReports();
            productTabContentController.disableGeneratingReports();
            supplierTabContentController.disableGeneratingReports();
        }
    }

    /**
     * Odblokuwuje wszystkie funkcje aplikacji po wylogowaniu.
     */
    private void resetPermissions() {
        userTab.setDisable(false);
        SingleSelectionModel<Tab> selectionModel = tabPane.getSelectionModel();
        selectionModel.select(userTab);

        orderTabContentController.enableGrantingDiscounds();
        customerTabContentController.enableGeneratingReports();
        orderTabContentController.enableGeneratingReports();
        orderItemTabContentController.enableGeneratingReports();
        productTabContentController.enableGeneratingReports();
        supplierTabContentController.enableGeneratingReports();
    }

    /**
     * Obsługuje przycisk zalogowania.
     *
     * @param event
     */
    @FXML
    public void btnLogInClicked(MouseEvent event) {
        try {
            loginService.logIn(loginTextField.getText(), passwordField.getText());
        } catch (Exception e) {
            loginErrorLabel.setText("Błędne dane logowania.");
        }

        userNameLabel.setText(loginService.getCurrentUserName());
        checkPermissions(loginService.getCurrentUser());
        loginPane.setVisible(false);
    }

    /**
     * Obsługuje przycisk wylogowania.
     *
     * @param event
     */
    @FXML
    public void btnLogOutClicked(MouseEvent event) {
        loginService.logOut();
        resetPermissions();
        passwordField.setText("");
        loginTextField.setText("");
        loginErrorLabel.setText("");
        loginPane.setVisible(true);
    }

    /**
     * Obsługuje przycisk wyjścia z aplikacji.
     *
     * @param event
     */
    @FXML
    public void btnExitClicked(MouseEvent event) {
        loginService.logOut();
        Platform.exit();
    }
}
