package com.example.hurtownia.domain.orderitem;

import com.example.hurtownia.controllers.ReportController;
import com.example.hurtownia.domain.order.Order;
import com.example.hurtownia.domain.order.OrderService;
import com.example.hurtownia.domain.product.Product;
import com.example.hurtownia.domain.product.ProductService;
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
import java.util.Objects;
import java.util.ResourceBundle;

@Controller
public class OrderItemController implements Initializable {

    public static ObservableList<OrderItem> orderItems = FXCollections.observableArrayList();
    @Autowired
    public OrderService orderService;
    @Autowired
    public ProductService productService;
    @Autowired
    private OrderItemService orderItemService;
    @FXML
    private TextArea informationArea;
    @FXML
    private TextField productIdTextField, orderIdTextField, numberTextField;
    @FXML
    private TableView<OrderItem> orderItemTable;
    @FXML
    private TableColumn<OrderItem, String> itemPriceColumn, pricePerUnitColumn, idColumn, productIdColumn, orderIdColumn, numberColumn;
    @FXML
    private TableColumn<OrderItem, Void> deleteColumn;
    @FXML
    private TextField idSearchField, orderIdSearchField, productIdSearchField, itemPriceSearchField, pricePerUnitSearchField, numberSearchField;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        orderItemTable.setPlaceholder(new Label("Brak danych w tabeli"));
        productIdTextField.textProperty().addListener((ChangeListener<Object>) (observable, oldValue, newValue) -> informationArea.setScrollTop(Double.MAX_VALUE));
        setTable();
    }

    /**
     * Obsługuje przycisk wyświetlenia wszystkich elementów zamówień.
     *
     * @param event
     */
    @FXML
    public void orderItemsBtnShowClicked(MouseEvent event) {
        orderItemTable.getItems().clear();
        orderItems.setAll(orderItemService.findAll());
    }

    /**
     * Obsługuje przycisk dodawania nowego elementu zamówienia.
     *
     * @param event
     */
    @FXML
    public void orderItemsBtnAddClicked(MouseEvent event) {
        try {
            Order order = orderService.findById(Long.valueOf(orderIdTextField.getText()));
            Product product = productService.findById(Long.valueOf(productIdTextField.getText()));
            Integer amount = Integer.parseInt(numberTextField.getText());
            Double itemPrice = Math.round(product.getPrice() * Integer.parseInt(numberTextField.getText()) * 100.0) / 100.0;
            Double pricePerUnit = product.getPrice();
            OrderItem orderItem = OrderItem.builder()
                    .order(order)
                    .product(product)
                    .amount(amount)
                    .itemPrice(itemPrice)
                    .pricePerUnit(pricePerUnit)
                    .build();
            orderItemService.save(orderItem);
            informationArea.appendText("\nDodano nowy element zamówienia");
        } catch (Exception e) {
            informationArea.appendText("\nNie udało się dodać nowego elementu zamówienia");
        }
    }

    /**
     * Obsługuje przycisk wyszukiwania elementów zamówień z uwzględnieniem filtrów.
     *
     * @param event
     */
    @FXML
    public void orderItemsBtnSearchClicked(MouseEvent event) {
        //TODO: obsłużyć przycisk wyszukiwania
    }

    /**
     * Obsługuje przycisk generowania raportu z aktualnego widoku tabeli elementów zamówienia.
     *
     * @param event
     */
    @FXML
    public void orderItemsBtnReportClicked(MouseEvent event) {
        OrderItemReport report = new OrderItemReport(orderItemTable.getItems());
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
        StringConverter<String> converter = new DefaultStringConverter();
        orderIdColumn.setCellFactory(cell -> new TextFieldTableCell<>(converter) {
            @Override
            public void commitEdit(String newValue) {
                if (!Objects.equals(newValue, getItem())) {
                    OrderItem orderItem = orderItemTable.getSelectionModel().getSelectedItem();
                    try {
                        Order order = orderService.findById(Long.valueOf(newValue));
                        orderItem.setOrder(order);
                        orderItemService.update(orderItem);
                        informationArea.appendText("\nPomyślnie edytowano element zamówienia o id " + orderItem.getId());
                    } catch (ObjectNotFoundException e) {
                        informationArea.appendText("\n" + e.getMessage());
                    } catch (Exception e) {
                        informationArea.appendText("\nNie udało się edytować elementu zamowienia o id " + orderItem.getId());
                    }
                }
                super.commitEdit(newValue);
            }
        });
        productIdColumn.setCellFactory(cell -> new TextFieldTableCell<>(converter) {
            @Override
            public void commitEdit(String newValue) {
                if (!Objects.equals(newValue, getItem())) {
                    OrderItem orderItem = orderItemTable.getSelectionModel().getSelectedItem();
                    try {
                        Product product = productService.findById(Long.valueOf(newValue));
                        orderItem.setProduct(product);
                        orderItemService.update(orderItem);
                        informationArea.appendText("\nPomyślnie edytowano element zamówienia o id " + orderItem.getId());
                    } catch (ObjectNotFoundException e) {
                        informationArea.appendText("\n" + e.getMessage());
                    } catch (Exception e) {
                        informationArea.appendText("\nNie udało się edytować elementu zamowienia o id " + orderItem.getId());
                    }
                }
                super.commitEdit(newValue);
            }
        });
        numberColumn.setCellFactory(cell -> new TextFieldTableCell<>(converter) {
            @Override
            public void commitEdit(String newValue) {
                if (!Objects.equals(newValue, getItem())) {
                    OrderItem orderItem = orderItemTable.getSelectionModel().getSelectedItem();
                    try {
                        orderItem.setAmount(Integer.parseInt(newValue));
                        orderItem.setItemPrice(orderItem.getAmount() * orderItem.getPricePerUnit());
                        orderItemService.update(orderItem);
                        informationArea.appendText("\nPomyślnie edytowano element zamówienia o id " + orderItem.getId());
                    } catch (Exception e) {
                        informationArea.appendText("\nNie udało się edytować elementu zamowienia o id " + orderItem.getId());
                    }
                }
                super.commitEdit(newValue);
            }
        });

        idColumn.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getId())));
        orderIdColumn.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getOrder().getId())));
        productIdColumn.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getProduct().getId())));
        itemPriceColumn.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getItemPrice())));
        pricePerUnitColumn.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getPricePerUnit())));
        numberColumn.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getAmount())));
        deleteColumn.setCellFactory(new Callback<>() {
            @Override
            public TableCell<OrderItem, Void> call(final TableColumn<OrderItem, Void> param) {
                final TableCell<OrderItem, Void> cell = new TableCell<>() {
                    final Image image = new Image(getClass().getResourceAsStream("/Images/deleteBtn.jpg"), 25, 25, false, false);
                    private final Button btn = new Button();

                    {
                        btn.setOnAction((ActionEvent event) -> {
                            OrderItem orderItem = getTableView().getItems().get(getIndex());
                            if (orderItemService.delete(orderItem)) {
                                orderItems.remove(orderItem);
                                informationArea.appendText("\nPomyślnie usunięto element zamowienia o id " + orderItem.getId());
                            } else
                                informationArea.appendText("\nBłąd przy próbie usunięcia elementu zamowienia o id " + orderItem.getId());
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
        orderItemTable.setItems(orderItems);
    }
}
