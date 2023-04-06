package com.example.hurtownia.domain.order;

import com.example.hurtownia.controllers.PDFController;
import com.example.hurtownia.domain.AbstractReport;
import com.example.hurtownia.domain.customer.CustomerService;
import com.example.hurtownia.domain.orderitem.OrderItemService;
import com.example.hurtownia.domain.orderitem.OrderItem;
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
import org.springframework.stereotype.Controller;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;

@Controller
public class OrderController implements Initializable {

    @FXML
    private TextField customerIdTextField, dateTextField, discountTextField;
    @FXML
    private TextArea informationArea;
    @FXML
    private TableView<Order> ordersTable;
    @FXML
    private TableColumn<Order, String> dateColumn, idColumn, customerIdColumn, discountColumn, stateColumn, valueColumn;
    @FXML
    private TableColumn<Order, Void> invoiceColumn, deleteColumn;
    @FXML
    private TextField idSearchField, orderIdSearchField, dateSearchField, valueSearchField, discountSearchField, stateSearchField;

    public static ObservableList<Order> orders = FXCollections.observableArrayList();
    private final String[] orderStates = {"w przygotowaniu", "gotowe", "odebrane"};
    private OrderService orderService;
    private OrderItemService orderItemService;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ordersTable.setPlaceholder(new Label("Brak danych w tabeli"));
        informationArea.textProperty().addListener((ChangeListener<Object>) (observable, oldValue, newValue) -> informationArea.setScrollTop(Double.MAX_VALUE));
        orderService = new OrderService();
        orderItemService = new OrderItemService();
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
        orders.setAll(orderService.getOrder());
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

            Order order = orderService.saveOrder(Integer.parseInt(customerIdTextField.getText()), dateTextField.getText(), "w przygotowaniu", discount);
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
        OrderReport report = new OrderReport(ordersTable.getItems());
        try {
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setOpacity(1);
            stage.setTitle("Generuj raport");
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/FXML/PDF-save-view.fxml"));
            Parent root = fxmlLoader.load();
            PDFController controller = fxmlLoader.getController();
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
                    Order order = ordersTable.getSelectionModel().getSelectedItem();
                    orderService.updateOrderCustomer(order, newValue);
                }
                super.commitEdit(newValue);
            }
        });
        dateColumn.setCellFactory(cell -> new TextFieldTableCell<>(converter) {
            @Override
            public void commitEdit(String newValue) {
                if (!Objects.equals(newValue, getItem())) {
                    Order order = ordersTable.getSelectionModel().getSelectedItem();
                    try {
                        order.setDate(newValue);
                        orderService.updateOrder(order);
                        informationArea.appendText("\nPomyślnie edytowano zamowienie o id " + order.getId());
                    } catch (Exception e) {
                        informationArea.appendText("\nNie udało się edytować zamowienia o id " + order.getId());
                    }
                }
                super.commitEdit(newValue);
            }
        });
        discountColumn.setCellFactory(cell -> new TextFieldTableCell<>(converter) {
            @Override
            public void commitEdit(String newValue) {
                if (!Objects.equals(newValue, getItem())) {
                    Order order = ordersTable.getSelectionModel().getSelectedItem();
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
                        order.setDiscount(discount);
                        orderService.updateOrder(order);
                        informationArea.appendText("\nPomyślnie edytowano zamowienie o id " + order.getId());
                    } catch (Exception e) {
                        informationArea.appendText("\nNie udało się edytować zamowienia o id " + order.getId());
                    }
                }
                super.commitEdit(newValue);
            }
        });
        stateColumn.setCellFactory(ComboBoxTableCell.forTableColumn(orderStates));
        stateColumn.setOnEditCommit(t -> {
            Order order = ordersTable.getSelectionModel().getSelectedItem();
            if (!Objects.equals(t.getNewValue(), t.getOldValue())) {
                try {
                    order.setState(t.getNewValue());
                    orderService.updateOrder(order);
                    informationArea.appendText("\nPomyślnie edytowano zamowienie o id " + order.getId());
                } catch (Exception e) {
                    informationArea.appendText("\nNie udało się edytować zamowienia o id " + order.getId());
                }
            }
        });

        idColumn.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getId())));
        customerIdColumn.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getCustomer().getId())));
        dateColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDate()));
        valueColumn.setCellValueFactory(cellData -> {
            List<OrderItem> list = orderItemService.getOrderItem(cellData.getValue().getId());
            double price = 0.0;
            for (OrderItem el : list) price += el.getItemPrice() * el.getNumber();
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
            public TableCell<Order, Void> call(final TableColumn<Order, Void> param) {
                final TableCell<Order, Void> cell = new TableCell<>() {
                    final Image image = new Image(getClass().getResourceAsStream("/Images/invoiceBtn.png"), 25, 25, false, false);
                    private final Button btn = new Button();

                    {
                        btn.setOnAction((ActionEvent event) -> {
                            Order order = getTableView().getItems().get(getIndex());
                            AbstractReport report = new InvoiceReport(order);
                            try {
                                Stage stage = new Stage();
                                stage.initModality(Modality.APPLICATION_MODAL);
                                stage.setOpacity(1);
                                stage.setTitle("Generuj raport");
                                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/FXML/report-save-view.fxml"));
                                Parent root = fxmlLoader.load();
                                PDFController controller = fxmlLoader.getController();
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
            public TableCell<Order, Void> call(final TableColumn<Order, Void> param) {
                final TableCell<Order, Void> cell = new TableCell<>() {
                    final Image image = new Image(getClass().getResourceAsStream("/Images/deleteBtn.jpg"), 25, 25, false, false);
                    private final Button btn = new Button();

                    {
                        btn.setOnAction((ActionEvent event) -> {
                            Order z = getTableView().getItems().get(getIndex());
                            if (orderService.deleteOrder(z)) {
                                orders.remove(z);
                                informationArea.appendText("\nPomyślnie usunięto zamowienie o id " + z.getId());
                            } else
                                informationArea.appendText("\nBłąd przy próbie usunięcia zamowienia o id " + z.getId());
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
