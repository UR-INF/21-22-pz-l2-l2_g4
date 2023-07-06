package com.example.hurtownia.controllers;

import com.example.hurtownia.Start;
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
import com.example.hurtownia.domain.user.UserController;
import com.example.hurtownia.domain.user.UserService;
import com.example.hurtownia.domain.user.request.UserCreateRequest;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.io.IOException;
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
    public Button importCSVBtn;
    @FXML
    public Button optionsBtn;
    @FXML
    public AnchorPane loginPane;
    @FXML
    public Text userNameLabel;
    @Autowired
    public LoginService loginService;
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
    private UserController userTabContentController;
    @FXML
    private Text clockLabel, loginErrorLabel;
    @FXML
    private TextField loginTextField;
    @FXML
    private PasswordField passwordField;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        UserCreateRequest userCreateRequest = UserCreateRequest.builder()
                .name("Kierownik")
                .surname("Magazynu")
                .email("admin")
                .password("admin")
                .phoneNumber("123684597")
                .isAdmin(Boolean.TRUE)
                .generatingReports(Boolean.TRUE)
                .grantingDiscounts(Boolean.TRUE)
                .build();

        try {
            userService.create(userCreateRequest);
        } catch (UnsupportedOperationException ignored) {
        }

        new Thread(this::runClock).start();
        clearData();
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
    public void checkPermissions(User user) {
        if (Boolean.FALSE.equals(user.getIsAdmin())) {
            userTab.setDisable(true);
            SingleSelectionModel<Tab> selectionModel = tabPane.getSelectionModel();
            selectionModel.select(customerTab);
            optionsBtn.setDisable(true);
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
        optionsBtn.setDisable(false);
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
            userNameLabel.setText(loginService.getCurrentUserName());
            checkPermissions(loginService.getCurrentUser());
            loginPane.setVisible(false);
            Start.user = loginService.getCurrentUser();
            Start.rawUserPassword = passwordField.getText();
        } catch (Exception e) {
            loginErrorLabel.setText("Błędne dane logowania.");
        }
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
        clearData();
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

    /**
     * Obsługuje przycisk wyświetlenia okna importu plików CSV.
     *
     * @param event
     */
    public void btnImportCSVClicked(MouseEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/FXML/importCSV-view.fxml"));
            Parent root = fxmlLoader.load();
            ImportCSVController importCSVController = (ImportCSVController) fxmlLoader.getController();
            importCSVController.setController(this);
            Stage newStage = new Stage();
            newStage.setTitle("Import CSV");
            newStage.setScene(new Scene(root));
            newStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Obsługuje przycisk wyświetlenia okna opcji.
     *
     * @param event
     */
    @FXML
    public void btnOptionsClicked(MouseEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/FXML/options-view.fxml"));
            Parent root = fxmlLoader.load();
            Stage newStage = new Stage();
            newStage.setTitle("Opcje");
            newStage.setScene(new Scene(root));
            newStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Czyści widoki po wylogowaniu.
     */
    private void clearData() {
        customerTabContentController.informationArea.clear();
        customerTabContentController.customersTable.getItems().clear();
        customerTabContentController.customersTable.setPlaceholder(new Label("Brak danych w tabeli"));
        customerTabContentController.emailTextField.clear();
        customerTabContentController.nameTextField.clear();
        customerTabContentController.placeTextField.clear();
        customerTabContentController.surnameTextField.clear();
        customerTabContentController.buildingNumberTextField.clear();
        customerTabContentController.apartmentNumberTextField.clear();
        customerTabContentController.phoneNumberTextField.clear();
        customerTabContentController.peselTextField.clear();
        customerTabContentController.streetTextField.clear();
        customerTabContentController.zipCodeTextField.clear();
        customerTabContentController.idSearchField.clear();
        customerTabContentController.nameSearchField.clear();
        customerTabContentController.surnameSearchField.clear();
        customerTabContentController.placeSearchField.clear();
        customerTabContentController.streetSearchField.clear();
        customerTabContentController.buildingNumberSearchField.clear();
        customerTabContentController.apartmentNumberSearchField.clear();
        customerTabContentController.emailSearchField.clear();
        customerTabContentController.phoneNumberSearchField.clear();
        customerTabContentController.peselSearchField.clear();
        customerTabContentController.zipCodeSearchField.clear();

        orderTabContentController.informationArea.clear();
        orderTabContentController.ordersTable.getItems().clear();
        orderTabContentController.ordersTable.setPlaceholder(new Label("Brak danych w tabeli"));
        orderTabContentController.customerComboBox.setValue(null);
        orderTabContentController.dateTextField.setValue(null);
        orderTabContentController.discountTextField.clear();
        orderTabContentController.idSearchField.clear();
        orderTabContentController.orderIdSearchField.clear();
        orderTabContentController.dateSearchField.clear();
        orderTabContentController.valueSearchField.clear();
        orderTabContentController.discountSearchField.clear();
        orderTabContentController.stateSearchField.clear();

        orderItemTabContentController.informationArea.clear();
        orderItemTabContentController.orderItemTable.getItems().clear();
        orderItemTabContentController.orderItemTable.setPlaceholder(new Label("Brak danych w tabeli"));
        orderItemTabContentController.productComboBox.setValue(null);
        orderItemTabContentController.orderComboBox.setValue(null);
        orderItemTabContentController.numberTextField.clear();
        orderItemTabContentController.idSearchField.clear();
        orderItemTabContentController.orderIdSearchField.clear();
        orderItemTabContentController.productIdSearchField.clear();
        orderItemTabContentController.itemPriceSearchField.clear();
        orderItemTabContentController.pricePerUnitSearchField.clear();
        orderItemTabContentController.numberSearchField.clear();

        productTabContentController.informationArea.clear();
        productTabContentController.productsTable.getItems().clear();
        productTabContentController.productsTable.setPlaceholder(new Label("Brak danych w tabeli"));
        productTabContentController.priceTextField.clear();
        productTabContentController.supplierComboBox.setValue(null);
        productTabContentController.numberTextField.clear();
        productTabContentController.unitComboBox.setValue(null);
        productTabContentController.codeTextField.clear();
        productTabContentController.colorTextField.clear();
        productTabContentController.countryTextField.clear();
        productTabContentController.maxNumberTextField.clear();
        productTabContentController.nameTextField.clear();
        productTabContentController.idSearchField.clear();
        productTabContentController.nameSearchField.clear();
        productTabContentController.supplierIdSearchField.clear();
        productTabContentController.codeSearchField.clear();
        productTabContentController.priceSearchField.clear();
        productTabContentController.numberSearchField.clear();
        productTabContentController.unitSearchField.clear();
        productTabContentController.countrySearchField.clear();
        productTabContentController.colorSearchField.clear();
        productTabContentController.maxNumberSearchField.clear();
        productTabContentController.stateSearchField.clear();

        supplierTabContentController.informationArea.clear();
        supplierTabContentController.suppliersTable.getItems().clear();
        supplierTabContentController.suppliersTable.setPlaceholder(new Label("Brak danych w tabeli"));
        supplierTabContentController.emailTextField.clear();
        supplierTabContentController.countryTextField.clear();
        supplierTabContentController.placeTextField.clear();
        supplierTabContentController.nameTextField.clear();
        supplierTabContentController.nipTextField.clear();
        supplierTabContentController.streetTextField.clear();
        supplierTabContentController.idSearchField.clear();
        supplierTabContentController.nameSearchField.clear();
        supplierTabContentController.nipSearchField.clear();
        supplierTabContentController.emailSearchField.clear();
        supplierTabContentController.placeSearchField.clear();
        supplierTabContentController.streetSearchField.clear();
        supplierTabContentController.countrySearchField.clear();

        userTabContentController.informationArea.clear();
        userTabContentController.usersTable.getItems().clear();
        userTabContentController.usersTable.setPlaceholder(new Label("Brak danych w tabeli"));
        userTabContentController.emailTextField.clear();
        userTabContentController.passwordTextField.clear();
        userTabContentController.nameTextField.clear();
        userTabContentController.surnameTextField.clear();
        userTabContentController.phoneNumberTextField.clear();
        userTabContentController.idSearchField.clear();
        userTabContentController.nameSearchField.clear();
        userTabContentController.surnameSearchField.clear();
        userTabContentController.phoneNumberSearchField.clear();
        userTabContentController.emailSearchField.clear();
        userTabContentController.grantingDiscountsCheckBox.setSelected(false);
        userTabContentController.generatingReportsCheckBox.setSelected(false);
        userTabContentController.isAdminCheckBox.setSelected(false);
    }
}
