package com.example.hurtownia.domain.product;

import com.example.hurtownia.controllers.ReportController;
import com.example.hurtownia.domain.AbstractReport;
import com.example.hurtownia.domain.supplier.Supplier;
import com.example.hurtownia.domain.supplier.SupplierService;
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
public class ProductController implements Initializable {

    public static ObservableList<Product> products = FXCollections.observableArrayList();
    @FXML
    private TextArea informationArea;
    @FXML
    private TextField priceTextField, supplierIdTextField, numberTextField, unitTextField, codeTextField, colorTextField, countryTextField, maxNumberTextField, nameTextField;
    @FXML
    private TableView<Product> productsTable;
    @FXML
    private TableColumn<Product, String> priceColumn, idColumn, supplierIdColumn, numberColumn, unitColumn, codeColumn, colorColumn, countryColumn, maxNumberColumn, stateColumn;
    @FXML
    private TableColumn<Product, Boolean> deliveryReportColumn;
    @FXML
    private TableColumn<Product, Void> deleteColumn;
    @FXML
    private TextField idSearchField, supplierIdSearchField, codeSearchField, priceSearchField, numberSearchField, unitSearchField, countrySearchField, colorSearchField, maxNumberSearchField, stateSearchField;
    @Autowired
    private ProductService productService;
    @Autowired
    private SupplierService supplierService;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        productsTable.setPlaceholder(new Label("Brak danych w tabeli"));
        informationArea.textProperty().addListener((ChangeListener<Object>) (observable, oldValue, newValue) -> informationArea.setScrollTop(Double.MAX_VALUE));
        setTable();
    }

    /**
     * Oblicza stan produktu.
     *
     * @param ratio stosunek ilości produktu w magazynie i całkowitej pojemności magazynu na dany produkt
     * @return stan magazynowy produktu
     */
    public String calculateState(double ratio) {
        if (ratio < 30) return "niski";
        else if (ratio < 70) return "umiarkowany";
        else return "wysoki";
    }

    /**
     * Obsługuje przycisk wyświetlenia wszystkich produktów.
     *
     * @param event
     */
    @FXML
    public void productsBtnShowClicked(MouseEvent event) {
        productsTable.getItems().clear();
        products.setAll(productService.findAll());
    }

    /**
     * Obsługuje przycisk dodawania nowego produktu.
     *
     * @param event
     */
    @FXML
    public void productsBtnAddClicked(MouseEvent event) {
        try {
            Supplier supplier = supplierService.findById(Long.valueOf(supplierIdTextField.getText()));
            String name = nameTextField.getText();
            String unitOfMeasurement = unitTextField.getText();
            Double price = Double.parseDouble(priceTextField.getText());
            String country = countryTextField.getText();
            String code = codeTextField.getText();
            String color = colorTextField.getText();
            Integer number = Integer.parseInt(numberTextField.getText());
            Integer maxNumber = Integer.parseInt(maxNumberTextField.getText());
            Product product = Product.builder()
                    .supplier(supplier)
                    .name(name)
                    .unitOfMeasurement(unitOfMeasurement)
                    .price(price)
                    .country(country)
                    .code(code)
                    .color(color)
                    .number(number)
                    .maxNumber(maxNumber)
                    .build();
            productService.save(product);
            informationArea.appendText("\nDodano nowy produkt");
        } catch (Exception e) {
            informationArea.appendText("\nNie udało się dodać nowego produktu");
        }
    }

    /**
     * Obsługuje przycisk wyszukiwania produktów z uwzględnieniem filtrów.
     *
     * @param event
     */
    @FXML
    public void productsBtnSearchClicked(MouseEvent event) {
        //TODO: obsłużyć przycisk wyszukiwania
    }

    /**
     * Obsługuje przycisk generowania raportu z aktualnego widoku tabeli produktów.
     *
     * @param event
     */
    @FXML
    public void productsBtnReportClicked(MouseEvent event) {
        AbstractReport report = new ProductReport(productsTable.getItems());
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
     * Obsługuje przycisk generowania raportu dostaw.
     *
     * @param event
     */
    @FXML
    public void ProductsBtnSupplyReportClicked(MouseEvent event) {
        AbstractReport report = new SupplyReport(productsTable.getItems());
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

    private void setTable() {
        StringConverter<String> converter = new DefaultStringConverter();
        supplierIdColumn.setCellFactory(cell -> new TextFieldTableCell<>(converter) {
            @Override
            public void commitEdit(String newValue) {
                if (!Objects.equals(newValue, getItem())) {
                    Product product = productsTable.getSelectionModel().getSelectedItem();
                    try {
                        Supplier supplier = supplierService.findById(Long.valueOf(newValue));
                        product.setSupplier(supplier);
                        productService.update(product);
                        informationArea.appendText("\nPomyślnie edytowano produkt o id " + product.getId());
                    } catch (ObjectNotFoundException e) {
                        informationArea.appendText("\n" + e.getMessage());
                    } catch (Exception e) {
                        informationArea.appendText("\nNie udało się edytować produktu o id " + product.getId());
                    }
                }
                super.commitEdit(newValue);
            }
        });
        codeColumn.setCellFactory(cell -> new TextFieldTableCell<>(converter) {
            @Override
            public void commitEdit(String newValue) {
                if (!Objects.equals(newValue, getItem())) {
                    Product product = productsTable.getSelectionModel().getSelectedItem();
                    try {
                        product.setCode(newValue);
                        productService.update(product);
                        informationArea.appendText("\nPomyślnie edytowano produkt o id " + product.getId());
                    } catch (Exception e) {
                        informationArea.appendText("\nNie udało się edytować produktu o id " + product.getId());
                    }
                }
                super.commitEdit(newValue);
            }
        });
        priceColumn.setCellFactory(cell -> new TextFieldTableCell<>(converter) {
            @Override
            public void commitEdit(String newValue) {
                if (!Objects.equals(newValue, getItem())) {
                    Product product = productsTable.getSelectionModel().getSelectedItem();
                    try {
                        product.setPrice(Double.parseDouble(newValue));
                        productService.update(product);
                        informationArea.appendText("\nPomyślnie edytowano produkt o id " + product.getId());
                    } catch (Exception e) {
                        informationArea.appendText("\nNie udało się edytować produktu o id " + product.getId());
                    }
                }
                super.commitEdit(newValue);
            }
        });
        numberColumn.setCellFactory(cell -> new TextFieldTableCell<>(converter) {
            @Override
            public void commitEdit(String newValue) {
                if (!Objects.equals(newValue, getItem())) {
                    Product product = productsTable.getSelectionModel().getSelectedItem();
                    try {
                        product.setNumber(Integer.parseInt(newValue));
                        productService.update(product);
                        informationArea.appendText("\nPomyślnie edytowano produkt o id " + product.getId());
                    } catch (Exception e) {
                        informationArea.appendText("\nNie udało się edytować produktu o id " + product.getId());
                    }
                }
                super.commitEdit(newValue);
            }
        });
        unitColumn.setCellFactory(cell -> new TextFieldTableCell<>(converter) {
            @Override
            public void commitEdit(String newValue) {
                if (!Objects.equals(newValue, getItem())) {
                    Product product = productsTable.getSelectionModel().getSelectedItem();
                    try {
                        product.setUnitOfMeasurement(newValue);
                        productService.update(product);
                        informationArea.appendText("\nPomyślnie edytowano produkt o id " + product.getId());
                    } catch (Exception e) {
                        informationArea.appendText("\nNie udało się edytować produktu o id " + product.getId());
                    }
                }
                super.commitEdit(newValue);
            }
        });
        countryColumn.setCellFactory(cell -> new TextFieldTableCell<>(converter) {
            @Override
            public void commitEdit(String newValue) {
                if (!Objects.equals(newValue, getItem())) {
                    Product product = productsTable.getSelectionModel().getSelectedItem();
                    try {
                        product.setCountry(newValue);
                        productService.update(product);
                        informationArea.appendText("\nPomyślnie edytowano produkt o id " + product.getId());
                    } catch (Exception e) {
                        informationArea.appendText("\nNie udało się edytować produktu o id " + product.getId());
                    }
                }
                super.commitEdit(newValue);
            }
        });
        colorColumn.setCellFactory(cell -> new TextFieldTableCell<>(converter) {
            @Override
            public void commitEdit(String newValue) {
                if (!Objects.equals(newValue, getItem())) {
                    Product product = productsTable.getSelectionModel().getSelectedItem();
                    try {
                        product.setColor(newValue);
                        productService.update(product);
                        informationArea.appendText("\nPomyślnie edytowano produkt o id " + product.getId());
                    } catch (Exception e) {
                        informationArea.appendText("\nNie udało się edytować produktu o id " + product.getId());
                    }
                }
                super.commitEdit(newValue);
            }
        });
        maxNumberColumn.setCellFactory(cell -> new TextFieldTableCell<>(converter) {
            @Override
            public void commitEdit(String newValue) {
                if (!Objects.equals(newValue, getItem())) {
                    Product product = productsTable.getSelectionModel().getSelectedItem();
                    try {
                        product.setMaxNumber(Integer.parseInt(newValue));
                        productService.update(product);
                        informationArea.appendText("\nPomyślnie edytowano produkt o id " + product.getId());
                    } catch (Exception e) {
                        informationArea.appendText("\nNie udało się edytować produktu o id " + product.getId());
                    }
                }
                super.commitEdit(newValue);
            }
        });

        idColumn.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getId())));
        supplierIdColumn.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getSupplier().getId())));
        codeColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCode()));
        priceColumn.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getPrice())));
        numberColumn.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getNumber())));
        unitColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getUnitOfMeasurement()));
        countryColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCountry()));
        colorColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getColor()));
        maxNumberColumn.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getMaxNumber())));
        stateColumn.setCellValueFactory(cellData -> new SimpleStringProperty(calculateState(cellData.getValue().getNumber() / cellData.getValue().getMaxNumber())));
        deliveryReportColumn.setCellFactory(new Callback<>() {
            @Override
            public TableCell<Product, Boolean> call(final TableColumn<Product, Boolean> param) {
                final TableCell<Product, Boolean> cell = new TableCell<>() {
                    private final CheckBox check = new CheckBox();

                    {
                        check.setOnAction((ActionEvent event) -> products.get(getIndex()).setSupply(!products.get(getIndex()).isSupply()));
                        check.setOnMouseEntered((EventHandler) event -> getScene().setCursor(Cursor.HAND));
                        check.setOnMouseExited((EventHandler) event -> getScene().setCursor(Cursor.DEFAULT));
                    }

                    @Override
                    public void updateItem(Boolean item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) setGraphic(null);
                        else setGraphic(check);
                    }
                };
                cell.setAlignment(Pos.CENTER);
                return cell;
            }
        });
        deleteColumn.setCellFactory(new Callback<>() {
            @Override
            public TableCell<Product, Void> call(final TableColumn<Product, Void> param) {
                final TableCell<Product, Void> cell = new TableCell<>() {
                    final Image image = new Image(getClass().getResourceAsStream("/Images/deleteBtn.jpg"), 25, 25, false, false);
                    private final Button btn = new Button();

                    {
                        btn.setOnAction((ActionEvent event) -> {
                            Product product = getTableView().getItems().get(getIndex());
                            if (productService.delete(product)) {
                                products.remove(product);
                                informationArea.appendText("\nPomyślnie usunięto produktu o id " + product.getId());
                            } else
                                informationArea.appendText("\nBłąd przy próbie usunięcia produktu o id " + product.getId());
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
        productsTable.setItems(products);
    }
}