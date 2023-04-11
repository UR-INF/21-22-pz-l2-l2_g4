package com.example.hurtownia.domain.order;

import com.example.hurtownia.controllers.ReportController;
import com.example.hurtownia.domain.AbstractReport;
import com.example.hurtownia.domain.customer.CustomerService;
import com.example.hurtownia.domain.orderitem.OrderItem;
import com.example.hurtownia.domain.orderitem.OrderItemService;
import javafx.beans.property.SimpleDoubleProperty;
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

    public static ObservableList<OrderTableViewDTO> orders = FXCollections.observableArrayList();
    private final String[] orderStates = {"w przygotowaniu", "gotowe", "odebrane"};
    @Autowired
    public OrderService orderService;
    @FXML
    private TextField customerIdTextField, dateTextField, discountTextField;
    @FXML
    private TextArea informationArea;
    @FXML
    private TableView<OrderTableViewDTO> ordersTable;
    @FXML
    private TableColumn<OrderTableViewDTO, Number> valueColumn;
    @FXML
    private TableColumn<OrderTableViewDTO, String> dateColumn, idColumn, customerIdColumn, discountColumn, stateColumn;
    @FXML
    private TableColumn<OrderTableViewDTO, Void> invoiceColumn, deleteColumn;
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
        orders.setAll(orderService.findAll());
    }

    /**
     * Obsługuje przycisk dodawania nowego zamówienia.
     *
     * @param event
     */
    @FXML
    public void ordersBtnAddClicked(MouseEvent event) {
        try {
            Double discount = DiscountConverter.fromCodeToNumeric(discountTextField.getText());
            Long customerId = Long.parseLong(customerIdTextField.getText());
            String date = dateTextField.getText();
            String state = orderStates[0];
            OrderCreateDTO orderCreateDTO = OrderCreateDTO.builder()
                    .customerId(customerId)
                    .date(date)
                    .state(state)
                    .discount(discount)
                    .build();
            orderService.save(orderCreateDTO);
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
    }

    /**
     * Inicjalizuje tabelę.
     */
    private void setTable() {
        idColumn.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getId())));
        customerIdColumn.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getCustomerId())));
        dateColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDate()));
        valueColumn.setCellValueFactory(cellData -> new SimpleDoubleProperty(cellData.getValue().getValue()));
        discountColumn.setCellValueFactory(cellData -> new SimpleStringProperty(DiscountConverter.fromNumericToPercentage(cellData.getValue().getDiscount())));
        stateColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getState()));

        StringConverter<String> converter = new DefaultStringConverter();
        customerIdColumn.setCellFactory(cell -> new TextFieldTableCell<>(converter) {
            @Override
            public void commitEdit(String newValue) {
                if (!Objects.equals(newValue, getItem())) {
                    OrderTableViewDTO orderTableViewDTO = ordersTable.getSelectionModel().getSelectedItem();
                    try {
                        orderTableViewDTO.setCustomerId(Long.valueOf(newValue));
                        orderService.update(orderTableViewDTO);
                        informationArea.appendText("\nPomyślnie edytowano zamowienie o id " + orderTableViewDTO.getId());
                    } catch (ObjectNotFoundException e) {
                        informationArea.appendText("\n" + e.getMessage());
                    } catch (Exception e) {
                        informationArea.appendText("\nNie udało się edytować zamowienia o id " + orderTableViewDTO.getId());
                    }
                }
                super.commitEdit(newValue);
            }
        });
        dateColumn.setCellFactory(cell -> new TextFieldTableCell<>(converter) {
            @Override
            public void commitEdit(String newValue) {
                if (!Objects.equals(newValue, getItem())) {
                    OrderTableViewDTO orderTableViewDTO = ordersTable.getSelectionModel().getSelectedItem();
                    try {
                        orderTableViewDTO.setDate(newValue);
                        orderService.update(orderTableViewDTO);
                        informationArea.appendText("\nPomyślnie edytowano zamowienie o id " + orderTableViewDTO.getId());
                    } catch (Exception e) {
                        informationArea.appendText("\nNie udało się edytować zamowienia o id " + orderTableViewDTO.getId());
                    }
                }
                super.commitEdit(newValue);
            }
        });
        discountColumn.setCellFactory(cell -> new TextFieldTableCell<>(converter) {
            @Override
            public void commitEdit(String newValue) {
                if (!Objects.equals(newValue, getItem())) {
                    OrderTableViewDTO orderTableViewDTO = ordersTable.getSelectionModel().getSelectedItem();
                    try {
                        orderTableViewDTO.setDiscount(DiscountConverter.fromCodeToNumeric(newValue));
                        orderService.update(orderTableViewDTO);
                        informationArea.appendText("\nPomyślnie edytowano zamowienie o id " + orderTableViewDTO.getId());
                    } catch (Exception e) {
                        informationArea.appendText("\nNie udało się edytować zamowienia o id " + orderTableViewDTO.getId());
                    }
                }
                super.commitEdit(DiscountConverter.fromCodeToPercentage(newValue));
            }
        });
        stateColumn.setCellFactory(ComboBoxTableCell.forTableColumn(orderStates));
        stateColumn.setOnEditCommit(t -> {
            OrderTableViewDTO orderTableViewDTO = ordersTable.getSelectionModel().getSelectedItem();
            if (!Objects.equals(t.getNewValue(), t.getOldValue())) {
                try {
                    orderTableViewDTO.setState(t.getNewValue());
                    orderService.update(orderTableViewDTO);
                    informationArea.appendText("\nPomyślnie edytowano zamowienie o id " + orderTableViewDTO.getId());
                } catch (Exception e) {
                    informationArea.appendText("\nNie udało się edytować zamowienia o id " + orderTableViewDTO.getId());
                }
            }
        });
        invoiceColumn.setCellFactory(new Callback<>() {
            @Override
            public TableCell<OrderTableViewDTO, Void> call(final TableColumn<OrderTableViewDTO, Void> param) {
                final TableCell<OrderTableViewDTO, Void> cell = new TableCell<>() {
                    final Image image = new Image(getClass().getResourceAsStream("/Images/invoiceBtn.png"), 25, 25, false, false);
                    private final Button btn = new Button();

                    {
                        btn.setOnAction((ActionEvent event) -> {
                            OrderTableViewDTO orderTableViewDTO = getTableView().getItems().get(getIndex());
                            AbstractReport report = new InvoiceReport(orderService.getInvoiceData(orderTableViewDTO));
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
            public TableCell<OrderTableViewDTO, Void> call(final TableColumn<OrderTableViewDTO, Void> param) {
                final TableCell<OrderTableViewDTO, Void> cell = new TableCell<>() {
                    final Image image = new Image(getClass().getResourceAsStream("/Images/deleteBtn.jpg"), 25, 25, false, false);
                    private final Button btn = new Button();

                    {
                        btn.setOnAction((ActionEvent event) -> {
                            OrderTableViewDTO orderTableViewDTO = getTableView().getItems().get(getIndex());

                            if (orderService.delete(orderTableViewDTO)) {
                                orders.remove(orderTableViewDTO);
                                informationArea.appendText("\nPomyślnie usunięto zamowienie o id " + orderTableViewDTO.getId());
                            } else
                                informationArea.appendText("\nBłąd przy próbie usunięcia zamowienia o id " + orderTableViewDTO.getId());
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
