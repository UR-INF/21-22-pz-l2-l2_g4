package com.example.hurtownia.domain.orderitem;

import com.example.hurtownia.controllers.ReportController;
import com.example.hurtownia.domain.customer.CustomerService;
import com.example.hurtownia.domain.order.OrderDTO;
import com.example.hurtownia.domain.order.OrderService;
import com.example.hurtownia.domain.orderitem.request.OrderItemCreateRequest;
import com.example.hurtownia.domain.orderitem.request.OrderItemUpdateRequest;
import com.example.hurtownia.domain.product.Product;
import com.example.hurtownia.domain.product.ProductDTO;
import com.example.hurtownia.domain.product.ProductService;
import com.example.hurtownia.validation.TextFieldsValidators;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleLongProperty;
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
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.StringConverter;
import javafx.util.converter.NumberStringConverter;
import org.apache.commons.beanutils.BeanUtils;
import org.hibernate.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

/**
 * Kontroller zakładki 'Elementy zamówienia'.
 */
@Controller
public class OrderItemController implements Initializable {
    public static ObservableList<OrderItemDTO> orderItems = FXCollections.observableArrayList();
    public static ObservableList<OrderDTO> orders = FXCollections.observableArrayList();
    public static ObservableList<ProductDTO> products = FXCollections.observableArrayList();
    @Autowired
    private OrderService orderService;
    @Autowired
    private OrderItemService orderItemService;
    @Autowired
    public OrderItemReport orderItemReport;
    @Autowired
    public CustomerService customerService;
    @Autowired
    public ProductService productService;

    @FXML
    public TextArea informationArea;
    @FXML
    public TextField numberTextField;
    @FXML
    public ComboBox<OrderDTO> orderComboBox;
    @FXML
    public ComboBox<ProductDTO> productComboBox;
    @FXML
    public TableView<OrderItemDTO> orderItemTable;
    @FXML
    private TableColumn<OrderItemDTO, Number> itemPriceColumn, pricePerUnitColumn, idColumn, productIdColumn, orderIdColumn, amountColumn;
    @FXML
    private TableColumn<OrderItemDTO, Void> deleteColumn;
    @FXML
    public TextField idSearchField, orderIdSearchField, productIdSearchField, itemPriceSearchField, pricePerUnitSearchField, numberSearchField;
    @FXML
    private Button generateReportBtn;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setCustomerComboBox();
        setProductComboBox();
        orderItemTable.setPlaceholder(new Label("Brak danych w tabeli"));
        informationArea.setEditable(false);
        setTable();
    }

    /**
     * Konfiguruje ComboBox dla produktów.
     */
    public void setProductComboBox() {
        productComboBox.setPrefWidth(150);
        productComboBox.setItems(FXCollections.observableArrayList(products));
        productComboBox.setCellFactory(new Callback<ListView<ProductDTO>, ListCell<ProductDTO>>() {
            @Override
            public ListCell<ProductDTO> call(ListView<ProductDTO> param) {
                return new ListCell<ProductDTO>() {
                    @Override
                    protected void updateItem(ProductDTO item, boolean empty) {
                        super.updateItem(item, empty);
                        if (item != null) {
                            setText(item.getName() + " " + item.getNumber() + item.getUnitOfMeasurement() + " (" + item.getPrice() + "zł)");
                        } else {
                            setText(null);
                        }
                    }
                };
            }
        });
        productComboBox.setConverter(new StringConverter<ProductDTO>() {
            @Override
            public String toString(ProductDTO product) {
                if (product != null) {
                    return product.getName() + " " + product.getNumber() + product.getUnitOfMeasurement() + " (" + product.getPrice() + "zł)";
                } else {
                    return null;
                }
            }

            @Override
            public ProductDTO fromString(String string) {
                return null;
            }
        });
        productComboBox.setOnMouseClicked(event -> {
            products.setAll(productService.findAll().stream().filter(
                    p -> p.getNumber() > 0
            ).collect(Collectors.toList()));
            productComboBox.setItems(products);
        });
    }

    /**
     * Konfiguruje ComboBox dla zamówień.
     */
    public void setCustomerComboBox() {
        orderComboBox.setPrefWidth(150);
        orderComboBox.setItems(FXCollections.observableArrayList(orders));
        orderComboBox.setCellFactory(new Callback<ListView<OrderDTO>, ListCell<OrderDTO>>() {
            @Override
            public ListCell<OrderDTO> call(ListView<OrderDTO> param) {
                return new ListCell<OrderDTO>() {
                    @Override
                    protected void updateItem(OrderDTO item, boolean empty) {
                        super.updateItem(item, empty);
                        if (item != null) {
                            setText(customerService.findById(item.getCustomerId()).getEmail() + " " + item.getDate() + " (ID: " + item.getId() + ")");
                        } else {
                            setText(null);
                        }
                    }
                };
            }
        });
        orderComboBox.setConverter(new StringConverter<OrderDTO>() {
            @Override
            public String toString(OrderDTO customer) {
                if (customer != null) {
                    return customerService.findById(customer.getCustomerId()).getEmail() + " " + customer.getDate() + " (ID: " + customer.getId() + ")";
                } else {
                    return null;
                }
            }

            @Override
            public OrderDTO fromString(String string) {
                // Nie jest używane w tym przykładzie
                return null;
            }
        });
        orderComboBox.setOnMouseClicked(event -> {
            orders.setAll(orderService.findAll());
            orderComboBox.setItems(orders);
        });
    }

    /**
     * Dezaktywuje przycisk generowania raportu.
     */
    public void disableGeneratingReports() {
        generateReportBtn.setDisable(true);
    }

    /**
     * Aktywuje przycisk generowania raportu.
     */
    public void enableGeneratingReports() {
        generateReportBtn.setDisable(false);
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
        if (!TextFieldsValidators.validateInteger(numberTextField.getText())) {
            informationArea.appendText("\n Podaj liczbę sztuk w poprawnym formacie");
            return;
        }
        try {
            Long orderId = orderComboBox.getValue().getId();
            Long productId = productComboBox.getValue().getId();
            Integer amount = Integer.valueOf(numberTextField.getText());

            Integer availableAmount = productService.findById(productId).getNumber();
            if (availableAmount < amount) {
                if (availableAmount == 0) {
                    informationArea.appendText("\nProduktu nie ma w magazynie");
                    return;
                }
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Potwierdzenie");
                alert.setHeaderText("Nie ma wystarczającej ilości sztuk w magazynie.");
                alert.setContentText("Czy chcesz dodać pozostałe sztuki do zamówienia?");
                Optional<ButtonType> result = alert.showAndWait();
                if (result.isPresent() && result.get() == ButtonType.OK) {
                    informationArea.appendText("\nNie ma wystarczającej ilości sztuk w magazynie. Dodano pozostałe sztuki do zamówienia.");
                } else {
                    informationArea.appendText("\nAnulowano zamówienie");
                    return;
                }
            }
            OrderItemCreateRequest orderItemCreateRequest = OrderItemCreateRequest.builder()
                    .orderId(orderId)
                    .productId(productId)
                    .amount(amount)
                    .build();
            orderItemService.create(orderItemCreateRequest);
            informationArea.appendText("\nDodano nowy element zamówienia");
        } catch (Exception e) {
            e.printStackTrace();
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
        String idFilter = idSearchField.getText().trim();
        String orderIdFilter = orderIdSearchField.getText().trim();
        String productIdFilter = productIdSearchField.getText().trim();
        String itemPriceFilter = itemPriceSearchField.getText().trim();
        String pricePerUnitFilter = pricePerUnitSearchField.getText().trim();
        String numberFilter = numberSearchField.getText().trim();
        List<OrderItemDTO> filteredOrderItemsList = orderItemService.findAll();

        if (!(idFilter.isEmpty() && orderIdFilter.isEmpty() && productIdFilter.isEmpty() && itemPriceFilter.isEmpty() &&
                pricePerUnitFilter.isEmpty() && numberFilter.isEmpty())) {
            filteredOrderItemsList = filteredOrderItemsList.stream().filter(
                    orderItemDTO -> orderItemDTO.getId().toString().contains(idFilter)
                            && orderItemDTO.getOrderId().toString().contains(orderIdFilter)
                            && orderItemDTO.getProductId().toString().contains(productIdFilter)
                            && orderItemDTO.getItemPrice().toString().contains(itemPriceFilter)
                            && orderItemDTO.getPricePerUnit().toString().contains(pricePerUnitFilter)
                            && orderItemDTO.getAmount().toString().contains(numberFilter)
            ).collect(Collectors.toList());
        }
        orderItemTable.getItems().clear();
        orderItems.setAll(filteredOrderItemsList);
    }

    /**
     * Obsługuje przycisk generowania raportu z aktualnego widoku tabeli elementów zamówienia.
     *
     * @param event
     */
    @FXML
    public void orderItemsBtnReportClicked(MouseEvent event) {
        orderItemReport.setData(orderItemTable.getItems());
        try {
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setOpacity(1);
            stage.setTitle("Generuj raport");
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/FXML/report-save-view.fxml"));
            Parent root = fxmlLoader.load();
            ReportController controller = fxmlLoader.getController();
            controller.setReport(orderItemReport);
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
        orderIdColumn.setCellValueFactory(cellData -> new SimpleLongProperty(cellData.getValue().getOrderId()));
        productIdColumn.setCellValueFactory(cellData -> new SimpleLongProperty(cellData.getValue().getProductId()));
        itemPriceColumn.setCellValueFactory(cellData -> new SimpleDoubleProperty(cellData.getValue().getItemPrice()));
        pricePerUnitColumn.setCellValueFactory(cellData -> new SimpleDoubleProperty(cellData.getValue().getPricePerUnit()));
        amountColumn.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getAmount()));

        NumberStringConverter numberConverter = new NumberStringConverter();
        orderIdColumn.setCellFactory(cell -> new TextFieldTableCell<>(numberConverter) {
            @Override
            public void commitEdit(Number newValue) {
                if (!Objects.equals(newValue, getItem())) {
                    OrderItemUpdateRequest orderItemUpdateRequest = new OrderItemUpdateRequest();
                    try {
                        BeanUtils.copyProperties(orderItemUpdateRequest, orderItemTable.getSelectionModel().getSelectedItem());
                        orderItemUpdateRequest.setOrderId(newValue.longValue());
                        orderItemService.update(orderItemUpdateRequest);
                        informationArea.appendText("\nPomyślnie edytowano element zamówienia o id " + orderItemUpdateRequest.getId());
                    } catch (ObjectNotFoundException e) {
                        informationArea.appendText("\n" + e.getMessage());
                    } catch (Exception e) {
                        informationArea.appendText("\nNie udało się edytować elementu zamowienia o id " + orderItemUpdateRequest.getId());
                    }
                }
                super.commitEdit(newValue);
            }
        });
        amountColumn.setCellFactory(cell -> new TextFieldTableCell<>(numberConverter) {
            @Override
            public void commitEdit(Number newValue) {
                if (newValue.intValue() < 0) {
                    informationArea.appendText("\nPodaj nieujemną liczbę");
                    return;
                }
                Product product = productService.findById(orderItemTable.getSelectionModel().getSelectedItem().getProductId());
                if(newValue.intValue()> product.getNumber()){
                    informationArea.appendText("\nProduktu nie ma w magazynie, lub jest go mniej niż podano.\nDodano pozostałe sztuki do zamówienia.");
                }
                if(product.getNumber()+(orderItemTable.getSelectionModel().getSelectedItem().getAmount()-newValue.intValue())>product.getMaxNumber()){
                    informationArea.appendText("\nZwrócone produkty przekroczyłyby pojemność magazynu.\nZwrócono maksymalną ilość");
                }
                if (!Objects.equals(newValue, getItem())) {
                    OrderItemUpdateRequest orderItemUpdateRequest = new OrderItemUpdateRequest();
                    try {
                        BeanUtils.copyProperties(orderItemUpdateRequest, orderItemTable.getSelectionModel().getSelectedItem());
                        orderItemUpdateRequest.setAmount(newValue.intValue());
                        orderItemService.update(orderItemUpdateRequest);
                        orderItemTable.getItems().clear();
                        orderItems.setAll(orderItemService.findAll());
                        informationArea.appendText("\nPomyślnie edytowano element zamówienia o id " + orderItemUpdateRequest.getId());
                    } catch (Exception e) {
                        e.printStackTrace();
                        informationArea.appendText("\nNie udało się edytować elementu zamowienia o id " + orderItemUpdateRequest.getId());
                    }
                }
                super.commitEdit(newValue);
            }
        });
        deleteColumn.setCellFactory(new Callback<>() {
            @Override
            public TableCell<OrderItemDTO, Void> call(final TableColumn<OrderItemDTO, Void> param) {
                final TableCell<OrderItemDTO, Void> cell = new TableCell<>() {
                    final Image image = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/Images/deleteBtn.jpg")), 25, 25, false, false);
                    private final Button btn = new Button();

                    {
                        btn.setOnAction((ActionEvent event) -> {
                            Long id = getTableView().getItems().get(getIndex()).getId();
                            if (orderItemService.delete(id)) {
                                orderItems.remove(getTableView().getItems().get(getIndex()));
                                informationArea.appendText("\nPomyślnie usunięto element zamowienia o id " + id);
                            } else
                                informationArea.appendText("\nBłąd przy próbie usunięcia elementu zamowienia o id " + id+"\nsprawdz czy po usunięciu stan produktów nie przekraczałby pojemności magazynu");
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
        orderItemTable.setItems(orderItems);
    }
}
