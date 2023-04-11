package com.example.hurtownia.domain.orderitem;

import com.example.hurtownia.controllers.ReportController;
import com.example.hurtownia.domain.order.Order;
import com.example.hurtownia.domain.order.OrderService;
import com.example.hurtownia.domain.order.OrderTableViewDTO;
import com.example.hurtownia.domain.product.Product;
import com.example.hurtownia.domain.product.ProductService;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleLongProperty;
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
import javafx.util.converter.IntegerStringConverter;
import javafx.util.converter.NumberStringConverter;
import org.hibernate.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

@Controller
public class OrderItemController implements Initializable {

    public static ObservableList<OrderItemTableViewDTO> orderItems = FXCollections.observableArrayList();
    @Autowired
    private OrderItemService orderItemService;
    @FXML
    private TextArea informationArea;
    @FXML
    private TextField productIdTextField, orderIdTextField, numberTextField;
    @FXML
    private TableView<OrderItemTableViewDTO> orderItemTable;
    @FXML
    private TableColumn<OrderItemTableViewDTO, Number> itemPriceColumn, pricePerUnitColumn, idColumn, productIdColumn, orderIdColumn, amountColumn;
    @FXML
    private TableColumn<OrderItemTableViewDTO, Void> deleteColumn;
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
            Long orderId = Long.valueOf(orderIdTextField.getText());
            Long productId = Long.valueOf(productIdTextField.getText());
            Integer amount = Integer.valueOf(numberTextField.getText());
            OrderItemCreateDTO orderItemCreateDTO = OrderItemCreateDTO.builder()
                    .orderid(orderId)
                    .productId(productId)
                    .amount(amount)
                    .build();
            orderItemService.save(orderItemCreateDTO);
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
        idColumn.setCellValueFactory(cellData -> new SimpleLongProperty(cellData.getValue().getId()));
        orderIdColumn.setCellValueFactory(cellData -> new SimpleLongProperty(cellData.getValue().getOrderid()));
        productIdColumn.setCellValueFactory(cellData -> new SimpleLongProperty(cellData.getValue().getProductId()));
        itemPriceColumn.setCellValueFactory(cellData -> new SimpleDoubleProperty(cellData.getValue().getItemPrice()));
        pricePerUnitColumn.setCellValueFactory(cellData -> new SimpleDoubleProperty(cellData.getValue().getPricePerUnit()));
        amountColumn.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getAmount()));

        NumberStringConverter numberConverter = new NumberStringConverter();
        orderIdColumn.setCellFactory(cell -> new TextFieldTableCell<>(numberConverter) {
            @Override
            public void commitEdit(Number newValue) {
                if (!Objects.equals(newValue, getItem())) {
                    OrderItemTableViewDTO orderItemTableViewDTO = orderItemTable.getSelectionModel().getSelectedItem();
                    try {
                        orderItemTableViewDTO.setOrderid(newValue.longValue());
                        orderItemService.update(orderItemTableViewDTO);
                        informationArea.appendText("\nPomyślnie edytowano element zamówienia o id " + orderItemTableViewDTO.getId());
                    } catch (ObjectNotFoundException e) {
                        informationArea.appendText("\n" + e.getMessage());
                    } catch (Exception e) {
                        informationArea.appendText("\nNie udało się edytować elementu zamowienia o id " + orderItemTableViewDTO.getId());
                    }
                }
                super.commitEdit(newValue);
            }
        });
        productIdColumn.setCellFactory(cell -> new TextFieldTableCell<>(numberConverter) {
            @Override
            public void commitEdit(Number newValue) {
                if (!Objects.equals(newValue, getItem())) {
                    OrderItemTableViewDTO orderItemTableViewDTO = orderItemTable.getSelectionModel().getSelectedItem();
                    try {
                        orderItemTableViewDTO.setProductId(newValue.longValue());
                        orderItemService.update(orderItemTableViewDTO);
                        informationArea.appendText("\nPomyślnie edytowano element zamówienia o id " + orderItemTableViewDTO.getId());
                    } catch (ObjectNotFoundException e) {
                        informationArea.appendText("\n" + e.getMessage());
                    } catch (Exception e) {
                        informationArea.appendText("\nNie udało się edytować elementu zamowienia o id " + orderItemTableViewDTO.getId());
                    }
                }
                super.commitEdit(newValue);
            }
        });
        amountColumn.setCellFactory(cell -> new TextFieldTableCell<>(numberConverter) {
            @Override
            public void commitEdit(Number newValue) {
                if (!Objects.equals(newValue, getItem())) {
                    OrderItemTableViewDTO orderItemTableViewDTO = orderItemTable.getSelectionModel().getSelectedItem();
                    try {
                        orderItemTableViewDTO.setAmount(newValue.intValue());
                        orderItemService.update(orderItemTableViewDTO);
                        orderItemTable.getItems().clear();
                        orderItems.setAll(orderItemService.findAll());
                        informationArea.appendText("\nPomyślnie edytowano element zamówienia o id " + orderItemTableViewDTO.getId());
                    } catch (Exception e) {
                        informationArea.appendText("\nNie udało się edytować elementu zamowienia o id " + orderItemTableViewDTO.getId());
                    }
                }
                super.commitEdit(newValue);
            }
        });
        deleteColumn.setCellFactory(new Callback<>() {
            @Override
            public TableCell<OrderItemTableViewDTO, Void> call(final TableColumn<OrderItemTableViewDTO, Void> param) {
                final TableCell<OrderItemTableViewDTO, Void> cell = new TableCell<>() {
                    final Image image = new Image(getClass().getResourceAsStream("/Images/deleteBtn.jpg"), 25, 25, false, false);
                    private final Button btn = new Button();

                    {
                        btn.setOnAction((ActionEvent event) -> {
                            OrderItemTableViewDTO orderItemTableViewDTO = getTableView().getItems().get(getIndex());
                            if (orderItemService.delete(orderItemTableViewDTO)) {
                                orderItems.remove(orderItemTableViewDTO);
                                informationArea.appendText("\nPomyślnie usunięto element zamowienia o id " + orderItemTableViewDTO.getId());
                            } else
                                informationArea.appendText("\nBłąd przy próbie usunięcia elementu zamowienia o id " + orderItemTableViewDTO.getId());
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
