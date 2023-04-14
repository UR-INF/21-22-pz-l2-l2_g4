package com.example.hurtownia.domain.order;

import com.example.hurtownia.controllers.ReportController;
import com.example.hurtownia.domain.AbstractReport;
import com.example.hurtownia.domain.Customer.CustomerMapper;
import com.example.hurtownia.domain.Order.OrderDTO;
import com.example.hurtownia.domain.Order.OrderMapper;
import com.example.hurtownia.domain.customer.Customer;
import com.example.hurtownia.domain.customer.CustomerService;
import com.example.hurtownia.domain.orderitem.OrderItem;
import com.example.hurtownia.domain.orderitem.OrderItemService;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.StringConverter;
import javafx.util.converter.DefaultStringConverter;
import org.hibernate.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;

@Controller
public class OrderController implements Initializable {

    public static ObservableList<OrderDTO> orders = FXCollections.observableArrayList();
    private final String[] orderStates = {"w przygotowaniu", "gotowe", "odebrane"};
    @Autowired
    public OrderService orderService;

    @Autowired
    public OrderMapper orderMapper;

    @Autowired
    public CustomerMapper customerMapper;
    @Autowired
    public OrderItemService orderItemService;
    @Autowired
    public CustomerService customerService;
    @FXML
    private TextField customerIdTextField, dateTextField, discountTextField;
    @FXML
    private TextArea informationArea;
    @FXML
    private TableView<OrderDTO> ordersTable;
    @FXML
    private TableColumn<OrderDTO, String> dateColumn, idColumn, customerIdColumn, discountColumn, stateColumn, valueColumn;
    @FXML
    private TableColumn<OrderDTO, Void> invoiceColumn, deleteColumn;
    @FXML
    private TextField idSearchField, orderIdSearchField, dateSearchField, valueSearchField, discountSearchField, stateSearchField;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ordersTable.setPlaceholder(new Label("Brak danych w tabeli"));
        informationArea.textProperty().addListener((ChangeListener<Object>) (observable, oldValue, newValue) -> informationArea.setScrollTop(Double.MAX_VALUE));
        setTable();
    }

    /**
     * Obsługuje przycisk wyświetlenia wszystkich zamówień.
     *
     * @param event
     */
    @FXML
    public void ordersBtnShowClicked(MouseEvent event) {
        ordersTable.getItems().clear();
        orders.setAll(orderMapper.mapToDto(orderService.findAll()));

    }

    /**
     * Obsługuje przycisk dodawania nowego zamówienia.
     *
     * @param event
     */
    @FXML
    public void ordersBtnAddClicked(MouseEvent event) {
        try {
            Double discount;
            switch (discountTextField.getText()) {
                case "KOD1":
                    discount = 0.1;
                    break;
                case "KOD2":
                    discount = 0.2;
                    break;
                default:
                    discount = 0.0;
            }

            Customer customer = customerService.findById(Long.parseLong(customerIdTextField.getText()));
            String date = dateTextField.getText();
            String state = orderStates[0];
            Order order = Order.builder()
                    .customer(customer)
                    .date(date)
                    .state(state)
                    .discount(discount)
                    .build();
            orderService.save(order);
            informationArea.appendText("\nDodano nowe zamówienie");
        } catch (Exception e) {
            informationArea.appendText("\nNie udało się dodać nowego zamówienia");
        }
    }

    /**
     * Obsługuje przycisk wyszukiwania zamówień z uwzględnieniem filtrów.
     *
     * @param event
     */
    @FXML
    public void ordersBtnSearchClicked(MouseEvent event) {
        //TODO: obsłużyć przycisk wyszukiwania
    }

    /**
     * Obsługuje przycisk generowania raportu z aktualnego widoku tabeli zamówień.
     *
     * @param event
     */
    @FXML
    public void ordersBtnReportClicked(MouseEvent event) {
        OrderReport report = new OrderReport(orderService.findAll());
        try {
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setOpacity(1);
            stage.setTitle("Generuj raport");
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/FXML/PDF-save-view.fxml"));
            Parent root = fxmlLoader.load();
            ReportController controller = fxmlLoader.getController();
            controller.setReport(report);
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.showAndWait();
        } catch (IOException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERROR");
            alert.setHeaderText("ERROR");
            alert.setContentText("Błąd załadowania modułu generowania raportu.");
            alert.showAndWait();
        }
    }

    /**
     * Inicjalizuje tabelę.
     */
    private void setTable() {
        StringConverter<String> converter = new DefaultStringConverter();
        customerIdColumn.setCellFactory(cell -> new TextFieldTableCell<>(converter) {
            @Override
            public void commitEdit(String newValue) {
                if (!Objects.equals(newValue, getItem())) {
                    Long orderId = ordersTable.getSelectionModel().getSelectedItem().getId();
                    try {
                        Customer customer = customerService.findById(Long.valueOf(newValue));
                        Order order = orderService.findById(orderId);
                        order.setCustomer(customer);
                        orderService.update(order);
                        informationArea.appendText("\nPomyślnie edytowano zamowienie o id " + orderId);
                    } catch (ObjectNotFoundException e) {
                        informationArea.appendText("\n" + e.getMessage());
                    } catch (Exception e) {
                        informationArea.appendText("\nNie udało się edytować zamowienia o id " + orderId);
                    }
                }
                super.commitEdit(newValue);
            }
        });
        dateColumn.setCellFactory(cell -> new TextFieldTableCell<>(converter) {
            @Override
            public void commitEdit(String newValue) {
                if (!Objects.equals(newValue, getItem())) {
                    Long orderId = ordersTable.getSelectionModel().getSelectedItem().getId();
                    try {
                        Order order = orderService.findById(orderId);
                        order.setDate(newValue);
                        orderService.update(order);
                        informationArea.appendText("\nPomyślnie edytowano zamowienie o id " + orderId);
                    } catch (Exception e) {
                        informationArea.appendText("\nNie udało się edytować zamowienia o id " + orderId);
                    }
                }
                super.commitEdit(newValue);
            }
        });
        discountColumn.setCellFactory(cell -> new TextFieldTableCell<>(converter) {
            @Override
            public void commitEdit(String newValue) {
                if (!Objects.equals(newValue, getItem())) {
                    Long orderId = ordersTable.getSelectionModel().getSelectedItem().getId();
                    double discount;
                    switch (newValue) {
                        case "KOD1":
                            discount = 0.1;
                            newValue = "-10%";
                            break;
                        case "KOD2":
                            discount = 0.2;
                            newValue = "-20%";
                            break;
                        default:
                            discount = 0.0;
                            newValue = "nie udzielono";
                    }

                    try {
                        Order order = orderService.findById(orderId);
                        order.setDiscount(discount);
                        orderService.update(order);
                        informationArea.appendText("\nPomyślnie edytowano zamowienie o id " + orderId);
                    } catch (Exception e) {
                        informationArea.appendText("\nNie udało się edytować zamowienia o id " + orderId);
                    }
                }
                super.commitEdit(newValue);
            }
        });
        stateColumn.setCellFactory(ComboBoxTableCell.forTableColumn(orderStates));
        stateColumn.setOnEditCommit(t -> {
            Long orderId = ordersTable.getSelectionModel().getSelectedItem().getId();
            if (!Objects.equals(t.getNewValue(), t.getOldValue())) {
                try {
                    Order order = orderService.findById(orderId);
                    order.setState(t.getNewValue());
                    orderService.update(order);
                    informationArea.appendText("\nPomyślnie edytowano zamowienie o id " + orderId);
                } catch (Exception e) {
                    informationArea.appendText("\nNie udało się edytować zamowienia o id " + orderId);
                }
            }
        });

        idColumn.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getId())));
        customerIdColumn.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getCustomerDTO().getId())));
        dateColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDate()));
        valueColumn.setCellValueFactory(cellData -> {
            List<OrderItem> list = orderItemService.findAllByOrderId(cellData.getValue().getId());
            double price = 0.0;
            for (OrderItem el : list) price += el.getItemPrice();
            return new SimpleStringProperty(String.valueOf(Math.round(price * 100.0) / 100.0));
        });
        discountColumn.setCellValueFactory(cellData -> {
            String value = String.valueOf(cellData.getValue().getDiscount());
            switch (value) {
                case "0.1":
                    return new SimpleStringProperty("-10%");
                case "0.2":
                    return new SimpleStringProperty("-20%");
                default:
                    return new SimpleStringProperty("nie udzielono");
            }
        });
        stateColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getState()));
        invoiceColumn.setCellFactory(new Callback<>() {
            @Override
            public TableCell<OrderDTO, Void> call(final TableColumn<OrderDTO, Void> param) {
                final TableCell<OrderDTO, Void> cell = new TableCell<>() {
                    final Image image = new Image(getClass().getResourceAsStream("/Images/invoiceBtn.png"), 25, 25, false, false);
                    private final Button btn = new Button();

                    {
                        btn.setOnAction((ActionEvent event) -> {
                            Long orderId = getTableView().getItems().get(getIndex()).getId();
                            Order order = orderService.findById(orderId);
                            AbstractReport report = new InvoiceReport(order);
                            try {
                                Stage stage = new Stage();
                                stage.initModality(Modality.APPLICATION_MODAL);
                                stage.setOpacity(1);
                                stage.setTitle("Generuj raport");
                                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/FXML/report-save-view.fxml"));
                                Parent root = fxmlLoader.load();
                                ReportController controller = fxmlLoader.getController();
                                controller.setReport(report);
                                Scene scene = new Scene(root);
                                stage.setScene(scene);
                                stage.showAndWait();
                            } catch (IOException e) {
                                Alert alert = new Alert(Alert.AlertType.ERROR);
                                alert.setTitle("ERROR");
                                alert.setHeaderText("ERROR");
                                alert.setContentText("Błąd załadowania modułu generowania raportu.");
                                alert.showAndWait();
                            }
                        });
                        btn.setOnMouseEntered((EventHandler) event -> getScene().setCursor(Cursor.HAND));
                        btn.setOnMouseExited((EventHandler) event -> getScene().setCursor(Cursor.DEFAULT));
                    }

                    @Override
                    public void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) setGraphic(null);
                        else {
                            btn.setStyle("-fx-background-color: #ffffff; ");
                            btn.setGraphic(new ImageView(image));
                            setGraphic(btn);
                        }
                    }
                };
                cell.setAlignment(Pos.CENTER);
                return cell;
            }
        });
        deleteColumn.setCellFactory(new Callback<>() {
            @Override
            public TableCell<OrderDTO, Void> call(final TableColumn<OrderDTO, Void> param) {
                final TableCell<OrderDTO, Void> cell = new TableCell<>() {
                    final Image image = new Image(getClass().getResourceAsStream("/Images/deleteBtn.jpg"), 25, 25, false, false);
                    private final Button btn = new Button();

                    {
                        btn.setOnAction((ActionEvent event) -> {
                            Long orderId = getTableView().getItems().get(getIndex()).getId();
                            Order order = orderService.findById(orderId);
                            if (orderService.delete(order)) {
//                                orders.remove(orderMapper.mapToDto(order));
                                ordersTable.getItems().clear();
                                orders.setAll(orderMapper.mapToDto(orderService.findAll()));
                                informationArea.appendText("\nPomyślnie usunięto zamowienie o id " + orderId);
                            } else
                                informationArea.appendText("\nBłąd przy próbie usunięcia zamowienia o id " + orderId);
                        });
                        btn.setOnMouseEntered((EventHandler) event -> getScene().setCursor(Cursor.HAND));
                        btn.setOnMouseExited((EventHandler) event -> getScene().setCursor(Cursor.DEFAULT));
                    }

                    @Override
                    public void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) setGraphic(null);
                        else {
                            btn.setStyle("-fx-background-color: #ffffff; ");
                            btn.setGraphic(new ImageView(image));
                            setGraphic(btn);
                        }
                    }
                };
                cell.setAlignment(Pos.CENTER);
                return cell;
            }
        });
        ordersTable.setItems(orders);
    }
}
