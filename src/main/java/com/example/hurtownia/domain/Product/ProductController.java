package com.example.hurtownia.domain.product;

import com.example.hurtownia.controllers.ReportController;
import com.example.hurtownia.domain.AbstractReport;
import com.example.hurtownia.domain.supplier.SupplierService;
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
import javafx.util.converter.NumberStringConverter;
import org.hibernate.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

@Controller
public class ProductController implements Initializable {

    public static ObservableList<ProductTableViewDTO> products = FXCollections.observableArrayList();
    @FXML
    private TextArea informationArea;
    @FXML
    private TextField priceTextField, supplierIdTextField, numberTextField, unitTextField, codeTextField, colorTextField, countryTextField, maxNumberTextField, nameTextField;
    @FXML
    private TableView<ProductTableViewDTO> productsTable;
    @FXML
    private TableColumn<ProductTableViewDTO, Number> priceColumn, idColumn, supplierIdColumn, numberColumn, maxNumberColumn;
    @FXML
    private TableColumn<ProductTableViewDTO, String> unitColumn, codeColumn, colorColumn, countryColumn, stateColumn;
    @FXML
    private TableColumn<ProductTableViewDTO, Boolean> supplyReportColumn;
    @FXML
    private TableColumn<ProductTableViewDTO, Void> deleteColumn;
    @FXML
    private TextField idSearchField, supplierIdSearchField, codeSearchField, priceSearchField, numberSearchField, unitSearchField, countrySearchField, colorSearchField, maxNumberSearchField, stateSearchField;
    @Autowired
    private ProductService productService;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        productsTable.setPlaceholder(new Label("Brak danych w tabeli"));
        informationArea.textProperty().addListener((ChangeListener<Object>) (observable, oldValue, newValue) -> informationArea.setScrollTop(Double.MAX_VALUE));
        setTable();
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
            Long supplierId = Long.valueOf(supplierIdTextField.getText());
            String name = nameTextField.getText();
            String unitOfMeasurement = unitTextField.getText();
            Double price = Double.valueOf(priceTextField.getText());
            String country = countryTextField.getText();
            String code = codeTextField.getText();
            String color = colorTextField.getText();
            Integer number = Integer.valueOf(numberTextField.getText());
            Integer maxNumber = Integer.valueOf(maxNumberTextField.getText());
            ProductCreateDTO productCreateDTO = ProductCreateDTO.builder()
                    .supplierId(supplierId)
                    .name(name)
                    .unitOfMeasurement(unitOfMeasurement)
                    .price(price)
                    .country(country)
                    .code(code)
                    .color(color)
                    .number(number)
                    .maxNumber(maxNumber)
                    .build();
            productService.save(productCreateDTO);
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
        AbstractReport report = new SupplyReport(productService.getSupplyData(products));
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
        idColumn.setCellValueFactory(cellData -> new SimpleLongProperty(cellData.getValue().getId()));
        supplierIdColumn.setCellValueFactory(cellData -> new SimpleLongProperty(cellData.getValue().getSupplierId()));
        codeColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCode()));
        priceColumn.setCellValueFactory(cellData -> new SimpleDoubleProperty(cellData.getValue().getPrice()));
        numberColumn.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getNumber()));
        unitColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getUnitOfMeasurement()));
        countryColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCountry()));
        colorColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getColor()));
        maxNumberColumn.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getMaxNumber()));
        stateColumn.setCellValueFactory(cellData -> new SimpleStringProperty(ProductState.calculateState(cellData.getValue().getNumber(), cellData.getValue().getMaxNumber())));

        NumberStringConverter numberConverter = new NumberStringConverter();
        StringConverter<String> stringConverter = new DefaultStringConverter();
        supplierIdColumn.setCellFactory(cell -> new TextFieldTableCell<>(numberConverter) {
            @Override
            public void commitEdit(Number newValue) {
                if (!Objects.equals(newValue, getItem())) {
                    ProductTableViewDTO productTableViewDTO = productsTable.getSelectionModel().getSelectedItem();
                    try {
                        productTableViewDTO.setSupplierId(newValue.longValue());
                        productService.update(productTableViewDTO);
                        informationArea.appendText("\nPomyślnie edytowano produkt o id " + productTableViewDTO.getId());
                    } catch (ObjectNotFoundException e) {
                        informationArea.appendText("\n" + e.getMessage());
                    } catch (Exception e) {
                        informationArea.appendText("\nNie udało się edytować produktu o id " + productTableViewDTO.getId());
                    }
                }
                super.commitEdit(newValue);
            }
        });
        codeColumn.setCellFactory(cell -> new TextFieldTableCell<>(stringConverter) {
            @Override
            public void commitEdit(String newValue) {
                if (!Objects.equals(newValue, getItem())) {
                    ProductTableViewDTO productTableViewDTO = productsTable.getSelectionModel().getSelectedItem();
                    try {
                        productTableViewDTO.setCode(newValue);
                        productService.update(productTableViewDTO);
                        informationArea.appendText("\nPomyślnie edytowano produkt o id " + productTableViewDTO.getId());
                    } catch (Exception e) {
                        informationArea.appendText("\nNie udało się edytować produktu o id " + productTableViewDTO.getId());
                    }
                }
                super.commitEdit(newValue);
            }
        });
        priceColumn.setCellFactory(cell -> new TextFieldTableCell<>(numberConverter) {
            @Override
            public void commitEdit(Number newValue) {
                if (!Objects.equals(newValue, getItem())) {
                    ProductTableViewDTO productTableViewDTO = productsTable.getSelectionModel().getSelectedItem();
                    try {
                        productTableViewDTO.setPrice(newValue.doubleValue());
                        productService.update(productTableViewDTO);
                        informationArea.appendText("\nPomyślnie edytowano produkt o id " + productTableViewDTO.getId());
                    } catch (Exception e) {
                        informationArea.appendText("\nNie udało się edytować produktu o id " + productTableViewDTO.getId());
                    }
                }
                super.commitEdit(newValue);
            }
        });
        numberColumn.setCellFactory(cell -> new TextFieldTableCell<>(numberConverter) {
            @Override
            public void commitEdit(Number newValue) {
                if (!Objects.equals(newValue, getItem())) {
                    ProductTableViewDTO productTableViewDTO = productsTable.getSelectionModel().getSelectedItem();
                    try {
                        productTableViewDTO.setNumber(newValue.intValue());
                        productService.update(productTableViewDTO);
                        informationArea.appendText("\nPomyślnie edytowano produkt o id " + productTableViewDTO.getId());
                    } catch (Exception e) {
                        informationArea.appendText("\nNie udało się edytować produktu o id " + productTableViewDTO.getId());
                    }
                }
                super.commitEdit(newValue);
            }
        });
        unitColumn.setCellFactory(cell -> new TextFieldTableCell<>(stringConverter) {
            @Override
            public void commitEdit(String newValue) {
                if (!Objects.equals(newValue, getItem())) {
                    ProductTableViewDTO productTableViewDTO = productsTable.getSelectionModel().getSelectedItem();
                    try {
                        productTableViewDTO.setUnitOfMeasurement(newValue);
                        productService.update(productTableViewDTO);
                        informationArea.appendText("\nPomyślnie edytowano produkt o id " + productTableViewDTO.getId());
                    } catch (Exception e) {
                        informationArea.appendText("\nNie udało się edytować produktu o id " + productTableViewDTO.getId());
                    }
                }
                super.commitEdit(newValue);
            }
        });
        countryColumn.setCellFactory(cell -> new TextFieldTableCell<>(stringConverter) {
            @Override
            public void commitEdit(String newValue) {
                if (!Objects.equals(newValue, getItem())) {
                    ProductTableViewDTO productTableViewDTO = productsTable.getSelectionModel().getSelectedItem();
                    try {
                        productTableViewDTO.setCountry(newValue);
                        productService.update(productTableViewDTO);
                        informationArea.appendText("\nPomyślnie edytowano produkt o id " + productTableViewDTO.getId());
                    } catch (Exception e) {
                        informationArea.appendText("\nNie udało się edytować produktu o id " + productTableViewDTO.getId());
                    }
                }
                super.commitEdit(newValue);
            }
        });
        colorColumn.setCellFactory(cell -> new TextFieldTableCell<>(stringConverter) {
            @Override
            public void commitEdit(String newValue) {
                if (!Objects.equals(newValue, getItem())) {
                    ProductTableViewDTO productTableViewDTO = productsTable.getSelectionModel().getSelectedItem();
                    try {
                        productTableViewDTO.setColor(newValue);
                        productService.update(productTableViewDTO);
                        informationArea.appendText("\nPomyślnie edytowano produkt o id " + productTableViewDTO.getId());
                    } catch (Exception e) {
                        informationArea.appendText("\nNie udało się edytować produktu o id " + productTableViewDTO.getId());
                    }
                }
                super.commitEdit(newValue);
            }
        });
        maxNumberColumn.setCellFactory(cell -> new TextFieldTableCell<>(numberConverter) {
            @Override
            public void commitEdit(Number newValue) {
                if (!Objects.equals(newValue, getItem())) {
                    ProductTableViewDTO productTableViewDTO = productsTable.getSelectionModel().getSelectedItem();
                    try {
                        productTableViewDTO.setMaxNumber(newValue.intValue());
                        productService.update(productTableViewDTO);
                        informationArea.appendText("\nPomyślnie edytowano produkt o id " + productTableViewDTO.getId());
                    } catch (Exception e) {
                        informationArea.appendText("\nNie udało się edytować produktu o id " + productTableViewDTO.getId());
                    }
                }
                super.commitEdit(newValue);
            }
        });
        supplyReportColumn.setCellFactory(new Callback<>() {
            @Override
            public TableCell<ProductTableViewDTO, Boolean> call(final TableColumn<ProductTableViewDTO, Boolean> param) {
                final TableCell<ProductTableViewDTO, Boolean> cell = new TableCell<>() {
                    private final CheckBox check = new CheckBox();

                    {
                        check.setOnAction((ActionEvent event) -> products.get(getIndex()).setSupply(!products.get(getIndex()).getSupply()));
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
            public TableCell<ProductTableViewDTO, Void> call(final TableColumn<ProductTableViewDTO, Void> param) {
                final TableCell<ProductTableViewDTO, Void> cell = new TableCell<>() {
                    final Image image = new Image(getClass().getResourceAsStream("/Images/deleteBtn.jpg"), 25, 25, false, false);
                    private final Button btn = new Button();

                    {
                        btn.setOnAction((ActionEvent event) -> {
                            ProductTableViewDTO productTableViewDTO = getTableView().getItems().get(getIndex());
                            if (productService.delete(productTableViewDTO)) {
                                products.remove(productTableViewDTO);
                                informationArea.appendText("\nPomyślnie usunięto produktu o id " + productTableViewDTO.getId());
                            } else
                                informationArea.appendText("\nBłąd przy próbie usunięcia produktu o id " + productTableViewDTO.getId());
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
