package com.example.hurtownia.domain.order;

import com.example.hurtownia.controllers.ReportController;
import com.example.hurtownia.domain.customer.CustomerDTO;
import com.example.hurtownia.domain.order.request.OrderCreateRequest;
import com.example.hurtownia.domain.order.request.OrderUpdateRequest;
import com.example.hurtownia.validation.TextFieldsValidators;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
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
import javafx.util.converter.NumberStringConverter;
import org.apache.commons.beanutils.BeanUtils;
import org.hibernate.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.io.IOException;
import java.net.URL;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

/**
 * Kontroller zakładki 'Zamówienia'.
 */
@Controller
public class OrderController implements Initializable {
    public static ObservableList<OrderDTO> orders = FXCollections.observableArrayList();
    public static ObservableList<CustomerDTO> customers = FXCollections.observableArrayList();
    private final String[] orderStates = {"w przygotowaniu", "gotowe", "odebrane"};
    @Autowired
    public OrderService orderService;
    @Autowired
    public OrderReport orderReport;
    @Autowired
    private InvoiceReport invoiceReport;
    @FXML
    public TextField discountTextField;
    @FXML
    public ComboBox<CustomerDTO> customerComboBox;
    @FXML
    public DatePicker dateTextField;
    @FXML
    public TextArea informationArea;
    @FXML
    public TableView<OrderDTO> ordersTable;
    @FXML
    private TableColumn<OrderDTO, Number> valueColumn, customerIdColumn, idColumn;
    @FXML
    private TableColumn<OrderDTO, String> dateColumn, discountColumn, stateColumn;
    @FXML
    private TableColumn<OrderDTO, Void> invoiceColumn, deleteColumn;
    @FXML
    public TextField idSearchField, orderIdSearchField, dateSearchField, valueSearchField, discountSearchField, stateSearchField;
    @FXML
    private Button generateReportBtn;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ordersTable.setPlaceholder(new Label("Brak danych w tabeli"));
        informationArea.setEditable(false);
        informationArea.textProperty().addListener((ChangeListener<Object>) (observable, oldValue, newValue) -> informationArea.setScrollTop(Double.MAX_VALUE));
        setTable();
        setComboBox();
    }


    /**
     * Dezaktywuje możliwość nadawania rabatów.
     */
    public void disableGrantingDiscounds() {
        ordersTable.getColumns().get(4).setEditable(false);
        discountTextField.setDisable(true);
    }

    /**
     * Aktywuje możliwość nadawania rabatów.
     */
    public void enableGrantingDiscounds() {
        ordersTable.getColumns().get(4).setEditable(true);
        discountTextField.setDisable(false);
    }

    /**
     * Dezaktywuje możliwość generowania raportów.
     */
    public void disableGeneratingReports() {
        generateReportBtn.setDisable(true);
        invoiceColumn.setCellFactory(new Callback<>() {
            @Override
            public TableCell<OrderDTO, Void> call(final TableColumn<OrderDTO, Void> param) {
                final TableCell<OrderDTO, Void> cell = new TableCell<>() {
                    final Image image = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/Images/invoiceBtn.png")), 25, 25, false, false);
                    private final Button btn = new Button();

                    {
                        btn.setDisable(true);
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
    }

    /**
     * Ustawia listę klientów na podstawie danych pobranych z serwisu zamówień.
     * Klienci są sortowani alfabetycznie po nazwie przed ustawieniem listy.
     */
    public void setCustomers() {
        customers.setAll(orderService.getCustomersForComboBox());
        List<CustomerDTO> sortedCustomers = customers.stream().sorted(Comparator.comparing(CustomerDTO::getName)).collect(Collectors.toList());
        customers.clear();
        customers.setAll(sortedCustomers);
    }

    /**
     * Konfiguruje ComboBox dla klientów.
     */
    public void setComboBox() {
        customerComboBox.setPrefWidth(150);
        customerComboBox.setItems(customers);
        customerComboBox.setCellFactory(new Callback<ListView<CustomerDTO>, ListCell<CustomerDTO>>() {
            @Override
            public ListCell<CustomerDTO> call(ListView<CustomerDTO> param) {
                return new ListCell<CustomerDTO>() {
                    @Override
                    protected void updateItem(CustomerDTO item, boolean empty) {
                        super.updateItem(item, empty);
                        if (item != null) {
                            setText(item.getName() + " " + item.getSurname() + " (ID: " + item.getId() + ")");
                        } else {
                            setText(null);
                        }
                    }
                };
            }
        });
        customerComboBox.setConverter(new StringConverter<CustomerDTO>() {
            @Override
            public String toString(CustomerDTO customer) {
                if (customer != null) {
                    return customer.getName() + " " + customer.getSurname() + " (ID: " + customer.getId() + ")";
                } else {
                    return null;
                }
            }

            @Override
            public CustomerDTO fromString(String string) {
                // Nie jest używane w tym przykładzie
                return null;
            }
        });
        customerComboBox.setOnMouseClicked(event -> {
            setCustomers();
            customerComboBox.setItems(customers);
        });
    }

    /**
     * Aktywuje możliwość generowania raportów.
     */
    public void enableGeneratingReports() {
        generateReportBtn.setDisable(false);
        invoiceColumn.setCellFactory(new Callback<>() {
            @Override
            public TableCell<OrderDTO, Void> call(final TableColumn<OrderDTO, Void> param) {
                final TableCell<OrderDTO, Void> cell = new TableCell<>() {
                    final Image image = new Image(getClass().getResourceAsStream("/Images/invoiceBtn.png"), 25, 25, false, false);
                    private final Button btn = new Button();

                    {
                        btn.setOnAction((ActionEvent event) -> {
                            Long id = getTableView().getItems().get(getIndex()).getId();
                            invoiceReport.setData(orderService.getInvoiceData(id));
                            try {
                                Stage stage = new Stage();
                                stage.initModality(Modality.APPLICATION_MODAL);
                                stage.setOpacity(1);
                                stage.setTitle("Generuj raport");
                                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/FXML/report-save-view.fxml"));
                                Parent root = fxmlLoader.load();
                                ReportController controller = fxmlLoader.getController();
                                controller.setReport(invoiceReport);
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
                        btn.setOnMouseEntered((EventHandler<Event>) event -> getScene().setCursor(Cursor.HAND));
                        btn.setOnMouseExited((EventHandler<Event>) event -> getScene().setCursor(Cursor.DEFAULT));
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
        if (!TextFieldsValidators.validateDate(dateTextField.getValue())) {
            informationArea.appendText("\nPodaj datę późniejszą niż rok 2000, a wcześniejszą, lub równą dacie dzisiejszej");
            return;
        }
        try {
            Double discount;
            if (discountTextField.getText() == null) discount = 0.0;
            else discount = DiscountConverter.fromCodeToNumeric(discountTextField.getText());
            Long customerId = customerComboBox.getValue().getId();
            String date = dateTextField.getValue().toString();
            String state = orderStates[0];
            OrderCreateRequest orderCreateRequest = OrderCreateRequest.builder()
                    .customerId(customerId)
                    .date(date)
                    .state(state)
                    .discount(discount)
                    .build();
            orderService.create(orderCreateRequest);
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
        String idFilter = idSearchField.getText().trim();
        String customerIdFilter = orderIdSearchField.getText().trim();
        String dateFilter = dateSearchField.getText().trim();
        String valueFilter = valueSearchField.getText().trim();
        String discountFilter = discountSearchField.getText().trim();
        String stateFilter = stateSearchField.getText().trim();
        List<OrderDTO> filteredOrdersList = orderService.findAll();

        if (!(idFilter.isEmpty() && customerIdFilter.isEmpty() && dateFilter.isEmpty() && valueFilter.isEmpty() &&
                discountFilter.isEmpty() && stateFilter.isEmpty())) {
            filteredOrdersList = filteredOrdersList.stream().filter(
                    orderDTO -> orderDTO.getId().toString().contains(idFilter)
                            && orderDTO.getCustomerId().toString().contains(customerIdFilter)
                            && orderDTO.getDate().contains(dateFilter)
                            && orderDTO.getValue().toString().contains(valueFilter)
                            && orderDTO.getDiscount().toString().contains(discountFilter)
                            && orderDTO.getState().contains(stateFilter)
            ).collect(Collectors.toList());
            ordersTable.getItems().clear();
            orders.setAll(filteredOrdersList);
        }
    }

    /**
     * Obsługuje przycisk generowania raportu z aktualnego widoku tabeli zamówień.
     *
     * @param event
     */
    @FXML
    public void ordersBtnReportClicked(MouseEvent event) {
        orderReport.setData(ordersTable.getItems());
        try {
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setOpacity(1);
            stage.setTitle("Generuj raport");
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/FXML/report-save-view.fxml"));
            Parent root = fxmlLoader.load();
            ReportController controller = fxmlLoader.getController();
            controller.setReport(orderReport);
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
        idColumn.setCellValueFactory(cellData -> new SimpleLongProperty(cellData.getValue().getId()));
        customerIdColumn.setCellValueFactory(cellData -> new SimpleLongProperty(cellData.getValue().getCustomerId()));
        dateColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDate()));
        valueColumn.setCellValueFactory(cellData -> new SimpleDoubleProperty(cellData.getValue().getValue()));
        discountColumn.setCellValueFactory(cellData -> new SimpleStringProperty(DiscountConverter.fromNumericToPercentage(cellData.getValue().getDiscount())));
        stateColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getState()));

        StringConverter<String> stringConverter = new DefaultStringConverter();
        NumberStringConverter numberConverter = new NumberStringConverter();
        customerIdColumn.setCellFactory(cell -> new TextFieldTableCell<>(numberConverter) {
            @Override
            public void commitEdit(Number newValue) {
                if (!Objects.equals(newValue, getItem())) {
                    OrderUpdateRequest orderUpdateRequest = new OrderUpdateRequest();
                    try {
                        BeanUtils.copyProperties(orderUpdateRequest, ordersTable.getSelectionModel().getSelectedItem());
                        orderUpdateRequest.setCustomerId(newValue.longValue());
                        orderService.update(orderUpdateRequest);
                        informationArea.appendText("\nPomyślnie edytowano zamowienie o id " + orderUpdateRequest.getId());
                    } catch (ObjectNotFoundException e) {
                        informationArea.appendText("\n" + e.getMessage());
                    } catch (Exception e) {
                        informationArea.appendText("\nNie udało się edytować zamowienia o id " + orderUpdateRequest.getId());
                    }
                }
                super.commitEdit(newValue);
            }
        });
        dateColumn.setCellFactory(cell -> new TextFieldTableCell<>(stringConverter) {
            @Override
            public void commitEdit(String newValue) {
                if (!Objects.equals(newValue, getItem())) {
                    OrderUpdateRequest orderUpdateRequest = new OrderUpdateRequest();
                    try {
                        BeanUtils.copyProperties(orderUpdateRequest, ordersTable.getSelectionModel().getSelectedItem());
                        orderUpdateRequest.setDate(newValue);
                        orderService.update(orderUpdateRequest);
                        informationArea.appendText("\nPomyślnie edytowano zamowienie o id " + orderUpdateRequest.getId());
                    } catch (Exception e) {
                        informationArea.appendText("\nNie udało się edytować zamowienia o id " + orderUpdateRequest.getId());
                    }
                }
                super.commitEdit(newValue);
            }
        });
        discountColumn.setCellFactory(cell -> new TextFieldTableCell<>(stringConverter) {
            @Override
            public void commitEdit(String newValue) {
                if (!Objects.equals(newValue, getItem())) {
                    OrderUpdateRequest orderUpdateRequest = new OrderUpdateRequest();
                    try {
                        BeanUtils.copyProperties(orderUpdateRequest, ordersTable.getSelectionModel().getSelectedItem());
                        orderUpdateRequest.setDiscount(DiscountConverter.fromCodeToNumeric(newValue));
                        orderService.update(orderUpdateRequest);
                        informationArea.appendText("\nPomyślnie edytowano zamowienie o id " + orderUpdateRequest.getId());
                    } catch (Exception e) {
                        informationArea.appendText("\nNie udało się edytować zamowienia o id " + orderUpdateRequest.getId());
                    }
                }
                super.commitEdit(DiscountConverter.fromCodeToPercentage(newValue));
            }
        });
        stateColumn.setCellFactory(ComboBoxTableCell.forTableColumn(orderStates));
        stateColumn.setOnEditCommit(t -> {
            OrderUpdateRequest orderUpdateRequest = new OrderUpdateRequest();
            if (!Objects.equals(t.getNewValue(), t.getOldValue())) {
                try {
                    BeanUtils.copyProperties(orderUpdateRequest, ordersTable.getSelectionModel().getSelectedItem());
                    orderUpdateRequest.setState(t.getNewValue());
                    orderService.update(orderUpdateRequest);
                    informationArea.appendText("\nPomyślnie edytowano zamowienie o id " + orderUpdateRequest.getId());
                } catch (Exception e) {
                    informationArea.appendText("\nNie udało się edytować zamowienia o id " + orderUpdateRequest.getId());
                }
            }
        });
        invoiceColumn.setCellFactory(new Callback<>() {
            @Override
            public TableCell<OrderDTO, Void> call(final TableColumn<OrderDTO, Void> param) {
                final TableCell<OrderDTO, Void> cell = new TableCell<>() {
                    final Image image = new Image(getClass().getResourceAsStream("/Images/invoiceBtn.png"), 25, 25, false, false);
                    private final Button btn = new Button();

                    {
                        btn.setOnAction((ActionEvent event) -> {
                            Long id = getTableView().getItems().get(getIndex()).getId();
                            invoiceReport.setData(orderService.getInvoiceData(id));
                            try {
                                Stage stage = new Stage();
                                stage.initModality(Modality.APPLICATION_MODAL);
                                stage.setOpacity(1);
                                stage.setTitle("Generuj raport");
                                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/FXML/report-save-view.fxml"));
                                Parent root = fxmlLoader.load();
                                ReportController controller = fxmlLoader.getController();
                                controller.setReport(invoiceReport);
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
                        btn.setOnMouseEntered((EventHandler<Event>) event -> getScene().setCursor(Cursor.HAND));
                        btn.setOnMouseExited((EventHandler<Event>) event -> getScene().setCursor(Cursor.DEFAULT));
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
                    final Image image = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/Images/deleteBtn.jpg")), 25, 25, false, false);
                    private final Button btn = new Button();

                    {
                        btn.setOnAction((ActionEvent event) -> {
                            Long id = getTableView().getItems().get(getIndex()).getId();
                            if (orderService.delete(id)) {
                                orders.remove(getTableView().getItems().get(getIndex()));
                                informationArea.appendText("\nPomyślnie usunięto zamowienie o id " + id);
                            } else
                                informationArea.appendText("\nBłąd przy próbie usunięcia zamowienia o id " + id + "\nUsuń rekordy zawierające to zamówienie w innych tabelach");
                        });
                        btn.setOnMouseEntered((EventHandler<Event>) event -> getScene().setCursor(Cursor.HAND));
                        btn.setOnMouseExited((EventHandler<Event>) event -> getScene().setCursor(Cursor.DEFAULT));
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
