package com.example.hurtownia.domain.product;

import com.example.hurtownia.controllers.ReportController;
import com.example.hurtownia.domain.product.request.ProductCreateRequest;
import com.example.hurtownia.domain.product.request.ProductUpdateRequest;
import com.example.hurtownia.domain.supplier.SupplierDTO;
import com.example.hurtownia.domain.supplier.SupplierService;
import com.example.hurtownia.validation.TextFieldsValidators;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
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
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

/**
 * Kontroller zakładki 'Produkty'.
 */
@Controller
public class ProductController implements Initializable {
    public static ObservableList<ProductDTO> products = FXCollections.observableArrayList();
    public static ObservableList<SupplierDTO> suppliers = FXCollections.observableArrayList();
    private final String[] units = {"m^2", "m^3", "m", "kg", "szt", "l"};
    @Autowired
    private SupplyReport supplyReport;
    @Autowired
    public ProductReport productReport;
    @FXML
    public TextArea informationArea;
    @FXML
    private TextField priceTextField, numberTextField, codeTextField, colorTextField, countryTextField, maxNumberTextField, nameTextField;
    @FXML
    private ComboBox<SupplierDTO> supplierComboBox;
    @FXML
    private ComboBox<String> unitComboBox;
    @FXML
    public TableView<ProductDTO> productsTable;
    @FXML
    private TableColumn<ProductDTO, Number> priceColumn, idColumn, supplierIdColumn, numberColumn, maxNumberColumn;
    @FXML
    private TableColumn<ProductDTO, String> unitColumn, codeColumn, colorColumn, countryColumn, stateColumn, nameColumn;
    @FXML
    private TableColumn<ProductDTO, Boolean> supplyReportColumn;
    @FXML
    private TableColumn<ProductDTO, Void> deleteColumn;
    @FXML
    public TextField idSearchField, nameSearchField, supplierIdSearchField, codeSearchField, priceSearchField, numberSearchField, unitSearchField, countrySearchField, colorSearchField, maxNumberSearchField, stateSearchField;
    @Autowired
    private ProductService productService;
    @Autowired
    private SupplierService supplierService;
    @FXML
    private Button generateReportBtn;
    @FXML
    private Button generateSupplyReportBtn;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setComboBox();
        productsTable.setPlaceholder(new Label("Brak danych w tabeli"));
        informationArea.setEditable(false);
        informationArea.textProperty().addListener((ChangeListener<Object>) (observable, oldValue, newValue) -> informationArea.setScrollTop(Double.MAX_VALUE));
        setTable();
        unitComboBox.setItems(FXCollections.observableArrayList(units
        ));
    }

    public void setComboBox() {
        supplierComboBox.setPrefWidth(150);
        suppliers.setAll(supplierService.findAll());
        supplierComboBox.setItems(FXCollections.observableArrayList(suppliers));
        supplierComboBox.setCellFactory(new Callback<ListView<SupplierDTO>, ListCell<SupplierDTO>>() {
            @Override
            public ListCell<SupplierDTO> call(ListView<SupplierDTO> param) {
                return new ListCell<SupplierDTO>() {
                    @Override
                    protected void updateItem(SupplierDTO item, boolean empty) {
                        super.updateItem(item, empty);
                        if (item != null) {
                            setText(item.getName() + " (ID: " + item.getId() + ")");
                        } else {
                            setText(null);
                        }
                    }
                };
            }
        });
        supplierComboBox.setConverter(new StringConverter<SupplierDTO>() {
            @Override
            public String toString(SupplierDTO customer) {
                if (customer != null) {
                    return customer.getName() + " (ID: " + customer.getId() + ")";
                } else {
                    return null;
                }
            }

            @Override
            public SupplierDTO fromString(String string) {
                // Nie jest używane w tym przykładzie
                return null;
            }
        });
        supplierComboBox.setOnMouseClicked(event -> {
            suppliers.setAll(supplierService.findAll());
            supplierComboBox.setItems(suppliers);
        });
    }

    /**
     * Dezaktywuje możliwość generowania raportów.
     */
    public void disableGeneratingReports() {
        generateReportBtn.setDisable(true);
        generateSupplyReportBtn.setDisable(true);
        supplyReportColumn.setCellFactory(new Callback<>() {
            @Override
            public TableCell<ProductDTO, Boolean> call(final TableColumn<ProductDTO, Boolean> param) {
                final TableCell<ProductDTO, Boolean> cell = new TableCell<>() {
                    private final CheckBox checkBox = new CheckBox();

                    {
                        checkBox.setDisable(true);
                    }

                    @Override
                    public void updateItem(Boolean item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) setGraphic(null);
                        else setGraphic(checkBox);

                    }
                };
                cell.setAlignment(Pos.CENTER);
                return cell;
            }
        });
    }

    /**
     * Aktywuje możliwość generowania raportów.
     */
    public void enableGeneratingReports() {
        generateReportBtn.setDisable(false);
        generateSupplyReportBtn.setDisable(false);
        supplyReportColumn.setCellFactory(new Callback<>() {
            @Override
            public TableCell<ProductDTO, Boolean> call(final TableColumn<ProductDTO, Boolean> param) {
                final TableCell<ProductDTO, Boolean> cell = new TableCell<>() {
                    private final CheckBox checkBox = new CheckBox();

                    {
                        checkBox.setOnAction((ActionEvent event) -> products.get(getIndex()).setSupply(!products.get(getIndex()).getSupply()));
                        checkBox.setOnMouseEntered((EventHandler<javafx.event.Event>) event -> getScene().setCursor(Cursor.HAND));
                        checkBox.setOnMouseExited((EventHandler<javafx.event.Event>) event -> getScene().setCursor(Cursor.DEFAULT));
                    }

                    @Override
                    public void updateItem(Boolean item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) setGraphic(null);
                        else setGraphic(checkBox);

                    }
                };
                cell.setAlignment(Pos.CENTER);
                return cell;
            }
        });
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
        if (!TextFieldsValidators.validateDouble(priceTextField.getText())) {
            informationArea.appendText("\n Podaj cenę w poprawnym formacie");
            return;
        }
        if (!TextFieldsValidators.validateInteger(numberTextField.getText())) {
            informationArea.appendText("\n Podaj liczbę sztuk w poprawnym formacie");
            return;
        }
        if (!TextFieldsValidators.validateInteger(maxNumberTextField.getText())) {
            informationArea.appendText("\n Podaj maksymalną liczbę sztuk w poprawnym formacie");
            return;
        }
        try {
            Long supplierId = supplierComboBox.getValue().getId();
            String name = nameTextField.getText();
            String unitOfMeasurement = unitComboBox.getValue();
            Double price = Double.valueOf(priceTextField.getText());
            String country = countryTextField.getText();
            String code = codeTextField.getText();
            String color = colorTextField.getText();
            Integer number = Integer.valueOf(numberTextField.getText());
            Integer maxNumber = Integer.valueOf(maxNumberTextField.getText());
            ProductCreateRequest productCreateRequest = ProductCreateRequest.builder()
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
            productService.create(productCreateRequest);
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
        String idFilter = idSearchField.getText().trim();
        String nameFilter = nameSearchField.getText().trim();
        String supplierIdFilter = supplierIdSearchField.getText().trim();
        String codeFilter = codeSearchField.getText().trim();
        String priceFilter = priceSearchField.getText().trim();
        String numberFilter = numberSearchField.getText().trim();
        String unitFilter = unitSearchField.getText().trim();
        String countryFilter = countrySearchField.getText().trim();
        String colorFilter = colorSearchField.getText().trim();
        String maxNumberFilter = maxNumberSearchField.getText().trim();
        String stateFilter = stateSearchField.getText().trim();
        List<ProductDTO> filteredProductsList = productService.findAll();

        if (!(idFilter.isEmpty() && nameFilter.isEmpty() && supplierIdFilter.isEmpty() && codeFilter.isEmpty() &&
                priceFilter.isEmpty() && numberFilter.isEmpty() && unitFilter.isEmpty() && countryFilter.isEmpty() &&
                colorFilter.isEmpty() && maxNumberFilter.isEmpty() && stateFilter.isEmpty())) {
            filteredProductsList = filteredProductsList.stream().filter(
                    productDTO -> {
                        String state = ProductState.calculateState(productDTO.getNumber(), productDTO.getMaxNumber());
                        return productDTO.getId().toString().contains(idFilter)
                                && productDTO.getName().contains(nameFilter)
                                && productDTO.getSupplierId().toString().contains(supplierIdFilter)
                                && productDTO.getCode().contains(codeFilter)
                                && productDTO.getPrice().toString().contains(priceFilter)
                                && productDTO.getNumber().toString().contains(numberFilter)
                                && productDTO.getUnitOfMeasurement().contains(unitFilter)
                                && productDTO.getCountry().contains(countryFilter)
                                && productDTO.getMaxNumber().toString().contains(maxNumberFilter)
                                && state.contains(stateFilter);
                    }).collect(Collectors.toList());
        }
        productsTable.getItems().clear();
        products.setAll(filteredProductsList);
    }

    /**
     * Obsługuje przycisk generowania raportu z aktualnego widoku tabeli produktów.
     *
     * @param event
     */
    @FXML
    public void productsBtnReportClicked(MouseEvent event) {
        productReport.setData(productsTable.getItems());
        try {
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setOpacity(1);
            stage.setTitle("Generuj raport");
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/FXML/report-save-view.fxml"));
            Parent root = fxmlLoader.load();
            ReportController controller = fxmlLoader.getController();
            controller.setReport(productReport);
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
        List<Long> ids = products.stream()
                .filter(tableRow -> tableRow.getSupply())
                .map(tableRow -> tableRow.getId())
                .collect(Collectors.toList());
        supplyReport.setData(productService.getSupplyData(ids));
        try {
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setOpacity(1);
            stage.setTitle("Generuj raport");
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/FXML/report-save-view.fxml"));
            Parent root = fxmlLoader.load();
            ReportController controller = fxmlLoader.getController();
            controller.setReport(supplyReport);
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
        supplierIdColumn.setCellValueFactory(cellData -> new SimpleLongProperty(cellData.getValue().getSupplierId()));
        nameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getName()));
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
                    ProductUpdateRequest productUpdateRequest = new ProductUpdateRequest();
                    try {
                        BeanUtils.copyProperties(productUpdateRequest, productsTable.getSelectionModel().getSelectedItem());
                        productUpdateRequest.setSupplierId(newValue.longValue());
                        productService.update(productUpdateRequest);
                        informationArea.appendText("\nPomyślnie edytowano produkt o id " + productUpdateRequest.getId());
                    } catch (ObjectNotFoundException e) {
                        informationArea.appendText("\n" + e.getMessage());
                    } catch (Exception e) {
                        informationArea.appendText("\nNie udało się edytować produktu o id " + productUpdateRequest.getId());
                    }
                }
                super.commitEdit(newValue);
            }
        });
        nameColumn.setCellFactory(cell -> new TextFieldTableCell<>(stringConverter) {
            @Override
            public void commitEdit(String newValue) {
                if (!Objects.equals(newValue, getItem())) {
                    ProductUpdateRequest productUpdateRequest = new ProductUpdateRequest();
                    try {
                        BeanUtils.copyProperties(productUpdateRequest, productsTable.getSelectionModel().getSelectedItem());
                        productUpdateRequest.setName(newValue);
                        productService.update(productUpdateRequest);
                        informationArea.appendText("\nPomyślnie edytowano produkt o id " + productUpdateRequest.getId());
                    } catch (Exception e) {
                        informationArea.appendText("\nNie udało się edytować produktu o id " + productUpdateRequest.getId());
                    }
                }
                super.commitEdit(newValue);
            }
        });
        codeColumn.setCellFactory(cell -> new TextFieldTableCell<>(stringConverter) {
            @Override
            public void commitEdit(String newValue) {
                if (!Objects.equals(newValue, getItem())) {
                    ProductUpdateRequest productUpdateRequest = new ProductUpdateRequest();
                    try {
                        BeanUtils.copyProperties(productUpdateRequest, productsTable.getSelectionModel().getSelectedItem());
                        productUpdateRequest.setCode(newValue);
                        productService.update(productUpdateRequest);
                        informationArea.appendText("\nPomyślnie edytowano produkt o id " + productUpdateRequest.getId());
                    } catch (Exception e) {
                        informationArea.appendText("\nNie udało się edytować produktu o id " + productUpdateRequest.getId());
                    }
                }
                super.commitEdit(newValue);
            }
        });
        priceColumn.setCellFactory(cell -> new TextFieldTableCell<>(numberConverter) {
            @Override
            public void commitEdit(Number newValue) {
                if (newValue.doubleValue() < 0.00) {
                    informationArea.appendText("\nPodaj nieujemną liczbę");
                    return;
                }
                if (!Objects.equals(newValue, getItem())) {
                    ProductUpdateRequest productUpdateRequest = new ProductUpdateRequest();
                    try {
                        BeanUtils.copyProperties(productUpdateRequest, productsTable.getSelectionModel().getSelectedItem());
                        productUpdateRequest.setPrice(newValue.doubleValue());
                        productService.update(productUpdateRequest);
                        informationArea.appendText("\nPomyślnie edytowano produkt o id " + productUpdateRequest.getId());
                    } catch (Exception e) {
                        informationArea.appendText("\nNie udało się edytować produktu o id " + productUpdateRequest.getId());
                    }
                }
                super.commitEdit(newValue);
            }
        });
        numberColumn.setCellFactory(cell -> new TextFieldTableCell<>(numberConverter) {
            @Override
            public void commitEdit(Number newValue) {
                if (newValue.intValue() < 0) {
                    informationArea.appendText("\nPodaj nieujemną liczbę");
                    return;
                }
                if (newValue.intValue() > productsTable.getSelectionModel().getSelectedItem().getMaxNumber()) {
                    informationArea.appendText("\nPodaj ilosc mniejsza niz maksymalna");
                    return;
                }
                if (!Objects.equals(newValue, getItem())) {
                    ProductUpdateRequest productUpdateRequest = new ProductUpdateRequest();
                    try {
                        BeanUtils.copyProperties(productUpdateRequest, productsTable.getSelectionModel().getSelectedItem());
                        productUpdateRequest.setNumber(newValue.intValue());
                        productService.update(productUpdateRequest);
                        productsTable.getItems().clear();
                        products.setAll(productService.findAll());
                        informationArea.appendText("\nPomyślnie edytowano produkt o id " + productUpdateRequest.getId());
                    } catch (Exception e) {
                        informationArea.appendText("\nNie udało się edytować produktu o id " + productUpdateRequest.getId());
                    }
                }
                super.commitEdit(newValue);
            }
        });
        unitColumn.setCellFactory(ComboBoxTableCell.forTableColumn(units));
        unitColumn.setOnEditCommit(t -> {
                    ProductUpdateRequest productUpdateRequest = new ProductUpdateRequest();
                    if (!Objects.equals(t.getNewValue(), t.getOldValue())) {
                        try {
                            BeanUtils.copyProperties(productUpdateRequest, productsTable.getSelectionModel().getSelectedItem());
                            productUpdateRequest.setUnitOfMeasurement(t.getNewValue());
                            productService.update(productUpdateRequest);
                            informationArea.appendText("\nPomyślnie edytowano produkt o id " + productUpdateRequest.getId());
                        } catch (Exception e) {
                            informationArea.appendText("\nNie udało się edytować produktu o id " + productUpdateRequest.getId());
                        }
                    }
                }

        );
        countryColumn.setCellFactory(cell -> new TextFieldTableCell<>(stringConverter) {
            @Override
            public void commitEdit(String newValue) {
                if (!Objects.equals(newValue, getItem())) {
                    ProductUpdateRequest productUpdateRequest = new ProductUpdateRequest();
                    try {
                        BeanUtils.copyProperties(productUpdateRequest, productsTable.getSelectionModel().getSelectedItem());
                        productUpdateRequest.setCountry(newValue);
                        productService.update(productUpdateRequest);
                        informationArea.appendText("\nPomyślnie edytowano produkt o id " + productUpdateRequest.getId());
                    } catch (Exception e) {
                        informationArea.appendText("\nNie udało się edytować produktu o id " + productUpdateRequest.getId());
                    }
                }
                super.commitEdit(newValue);
            }
        });
        colorColumn.setCellFactory(cell -> new TextFieldTableCell<>(stringConverter) {
            @Override
            public void commitEdit(String newValue) {
                if (!Objects.equals(newValue, getItem())) {
                    ProductUpdateRequest productUpdateRequest = new ProductUpdateRequest();
                    try {
                        BeanUtils.copyProperties(productUpdateRequest, productsTable.getSelectionModel().getSelectedItem());
                        productUpdateRequest.setColor(newValue);
                        productService.update(productUpdateRequest);
                        informationArea.appendText("\nPomyślnie edytowano produkt o id " + productUpdateRequest.getId());
                    } catch (Exception e) {
                        informationArea.appendText("\nNie udało się edytować produktu o id " + productUpdateRequest.getId());
                    }
                }
                super.commitEdit(newValue);
            }
        });
        maxNumberColumn.setCellFactory(cell -> new TextFieldTableCell<>(numberConverter) {
            @Override
            public void commitEdit(Number newValue) {
                if (newValue.intValue() < 0) {
                    informationArea.appendText("\nPodaj nieujemną liczbę");
                    return;
                }
                if (newValue.intValue() < productsTable.getSelectionModel().getSelectedItem().getNumber()) {
                    informationArea.appendText("\nPodaj maksymalna liczbe wieksza niz obecny stan");
                    return;
                }
                if (!Objects.equals(newValue, getItem())) {
                    ProductUpdateRequest productUpdateRequest = new ProductUpdateRequest();
                    try {
                        BeanUtils.copyProperties(productUpdateRequest, productsTable.getSelectionModel().getSelectedItem());
                        productUpdateRequest.setMaxNumber(newValue.intValue());
                        productService.update(productUpdateRequest);
                        productsTable.getItems().clear();
                        products.setAll(productService.findAll());
                        informationArea.appendText("\nPomyślnie edytowano produkt o id " + productUpdateRequest.getId());
                    } catch (Exception e) {
                        informationArea.appendText("\nNie udało się edytować produktu o id " + productUpdateRequest.getId());
                    }
                }
                super.commitEdit(newValue);
            }
        });
        supplyReportColumn.setCellFactory(new Callback<>() {
            @Override
            public TableCell<ProductDTO, Boolean> call(final TableColumn<ProductDTO, Boolean> param) {
                final TableCell<ProductDTO, Boolean> cell = new TableCell<>() {
                    private final CheckBox checkBox = new CheckBox();

                    {
                        checkBox.setOnAction((ActionEvent event) -> products.get(getIndex()).setSupply(!products.get(getIndex()).getSupply()));
                        checkBox.setOnMouseEntered((EventHandler<javafx.event.Event>) event -> getScene().setCursor(Cursor.HAND));
                        checkBox.setOnMouseExited((EventHandler<javafx.event.Event>) event -> getScene().setCursor(Cursor.DEFAULT));
                    }

                    @Override
                    public void updateItem(Boolean item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) setGraphic(null);
                        else setGraphic(checkBox);

                    }
                };
                cell.setAlignment(Pos.CENTER);
                return cell;
            }
        });
        deleteColumn.setCellFactory(new Callback<>() {
            @Override
            public TableCell<ProductDTO, Void> call(final TableColumn<ProductDTO, Void> param) {
                final TableCell<ProductDTO, Void> cell = new TableCell<>() {
                    final Image image = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/Images/deleteBtn.jpg")), 25, 25, false, false);
                    private final Button btn = new Button();

                    {
                        btn.setOnAction((ActionEvent event) -> {
                            Long id = getTableView().getItems().get(getIndex()).getId();
                            if (productService.delete(id)) {
                                products.remove(getTableView().getItems().get(getIndex()));
                                informationArea.appendText("\nPomyślnie usunięto produktu o id " + id);
                            } else
                                informationArea.appendText("\nBłąd przy próbie usunięcia produktu o id " + id);
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
        productsTable.setItems(products);
    }
}
