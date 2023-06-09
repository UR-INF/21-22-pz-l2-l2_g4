package com.example.hurtownia.domain.supplier;

import com.example.hurtownia.controllers.ReportController;
import com.example.hurtownia.domain.supplier.request.SupplierCreateRequest;
import com.example.hurtownia.domain.supplier.request.SupplierUpdateRequest;
import com.example.hurtownia.validation.TextFieldsValidators;
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
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.StringConverter;
import javafx.util.converter.DefaultStringConverter;
import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

/**
 * Kontroller zakładki 'Dostawcy'.
 */
@Controller
public class SupplierController implements Initializable {

    public static ObservableList<SupplierDTO> suppliers = FXCollections.observableArrayList();
    @FXML
    public TextArea informationArea;
    @FXML
    public TextField emailTextField, countryTextField, placeTextField, nameTextField, nipTextField, streetTextField;
    @FXML
    public TableView<SupplierDTO> suppliersTable;
    @FXML
    private TableColumn<SupplierDTO, Number> idColumn;
    @FXML
    private TableColumn<SupplierDTO, String> emailColumn, nipColumn, countryColumn, placeColumn, nameColumn, streetColumn;
    @FXML
    private TableColumn<SupplierDTO, Void> deleteColumn;
    @FXML
    public TextField idSearchField, nameSearchField, nipSearchField, emailSearchField, placeSearchField, streetSearchField, countrySearchField;
    @Autowired
    private SupplierService supplierService;
    @Autowired
    public SupplierReport supplierReport;
    @FXML
    private Button generateReportBtn;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        suppliersTable.setPlaceholder(new Label("Brak danych w tabeli"));
        informationArea.setEditable(false);
        informationArea.textProperty().addListener((ChangeListener<Object>) (observable, oldValue, newValue) -> informationArea.setScrollTop(Double.MAX_VALUE));
        setTable();
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
     * Obsługuje przycisk wyświetlenia wszystkich dostawców.
     *
     * @param event
     */
    @FXML
    public void suppliersBtnShowClicked(MouseEvent event) {
        suppliersTable.getItems().clear();
        suppliers.setAll(supplierService.findAll());
    }

    /**
     * Obsługuje przycisk dodawania nowego dostawcy.
     *
     * @param event
     */
    @FXML
    public void suppliersBtnAddClicked(MouseEvent event) {
        try {
            String name = nameTextField.getText();
            String email = emailTextField.getText();
            String country = countryTextField.getText();
            String place = placeTextField.getText();
            String street = streetTextField.getText();
            String nip = nipTextField.getText();
            if (!TextFieldsValidators.validateEmail(email)) {
                informationArea.appendText("\n Podaj email w poprawnym formacie");
                return;
            }
            if (!TextFieldsValidators.validateNip(nip)) {
                informationArea.appendText("\n Podaj nip w poprawnym formacie (10 cyfr)");
                return;
            }
            SupplierCreateRequest supplierCreateRequest = SupplierCreateRequest.builder()
                    .name(name)
                    .email(email)
                    .country(country)
                    .place(place)
                    .street(street)
                    .nip(nip)
                    .build();
            supplierService.create(supplierCreateRequest);
            informationArea.appendText("\nDodano nowego dostawcę");
        } catch (Exception e) {
            informationArea.appendText("\nNie udało się dodać nowego dostawcy");
        }
    }

    /**
     * Obsługuje przycisk wyszukiwania dostawców z uwzględnieniem filtrów.
     *
     * @param event
     */
    @FXML
    public void suppliersBtnSearchClicked(MouseEvent event) {
        String idFilter = idSearchField.getText().trim();
        String nameFilter = nameSearchField.getText().trim();
        String emailFilter = emailSearchField.getText().trim();
        String countryFilter = countrySearchField.getText().trim();
        String placeFilter = placeSearchField.getText().trim();
        String streetFilter = streetSearchField.getText().trim();
        String nipFilter = nipSearchField.getText().trim();
        List<SupplierDTO> filteredSupplierList = supplierService.findAll();

        if (!(idFilter.isEmpty() && nameFilter.isEmpty() && emailFilter.isEmpty() && emailFilter.isEmpty() &&
                countryFilter.isEmpty() && placeFilter.isEmpty() && streetFilter.isEmpty() && nipFilter.isEmpty())) {
            filteredSupplierList = filteredSupplierList.stream().filter(
                    supplierDTO -> supplierDTO.getId().toString().contains(idFilter)
                            && supplierDTO.getName().contains(nameFilter)
                            && supplierDTO.getEmail().contains(emailFilter)
                            && supplierDTO.getCountry().contains(countryFilter)
                            && supplierDTO.getPlace().contains(placeFilter)
                            && supplierDTO.getStreet().contains(streetFilter)
                            && supplierDTO.getNip().contains(nipFilter)
            ).collect(Collectors.toList());
        }
        suppliersTable.getItems().clear();
        suppliers.setAll(filteredSupplierList);
    }

    /**
     * Obsługuje przycisk generowania raportu z aktualnego widoku tabeli dostawców.
     *
     * @param event
     */
    @FXML
    public void suppliersBtnReportClicked(MouseEvent event) {
        supplierReport.setData(suppliersTable.getItems());
        try {
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setOpacity(1);
            stage.setTitle("Generuj raport");
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/FXML/report-save-view.fxml"));
            Parent root = fxmlLoader.load();
            ReportController controller = fxmlLoader.getController();
            controller.setReport(supplierReport);
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
        nameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getName()));
        nipColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNip()));
        emailColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getEmail()));
        placeColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getPlace()));
        streetColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getStreet()));
        countryColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCountry()));

        StringConverter<String> converter = new DefaultStringConverter();
        nameColumn.setCellFactory(cell -> new TextFieldTableCell<>(converter) {
            @Override
            public void commitEdit(String newValue) {
                if (!Objects.equals(newValue, getItem())) {
                    SupplierUpdateRequest supplierUpdateRequest = new SupplierUpdateRequest();
                    try {
                        BeanUtils.copyProperties(supplierUpdateRequest, suppliersTable.getSelectionModel().getSelectedItem());
                        supplierUpdateRequest.setName(newValue);
                        supplierService.update(supplierUpdateRequest);
                        informationArea.appendText("\nPomyślnie edytowano dostawcę o id " + supplierUpdateRequest.getId());
                    } catch (Exception e) {
                        informationArea.appendText("\nNie udało się edytować dostawcy o id " + supplierUpdateRequest.getId());
                    }
                }
                super.commitEdit(newValue);
            }
        });
        nipColumn.setCellFactory(cell -> new TextFieldTableCell<>(converter) {
            @Override
            public void commitEdit(String newValue) {
                if (!TextFieldsValidators.validateNip(newValue)) {
                    informationArea.appendText("\nPodaj nip w poprawnym formacie (10 cyfr)");
                    return;
                }
                if (!Objects.equals(newValue, getItem())) {
                    SupplierUpdateRequest supplierUpdateRequest = new SupplierUpdateRequest();
                    try {
                        BeanUtils.copyProperties(supplierUpdateRequest, suppliersTable.getSelectionModel().getSelectedItem());
                        supplierUpdateRequest.setNip(newValue);
                        supplierService.update(supplierUpdateRequest);
                        informationArea.appendText("\nPomyślnie edytowano dostawcę o id " + supplierUpdateRequest.getId());
                    } catch (Exception e) {
                        informationArea.appendText("\nNie udało się edytować dostawcy o id " + supplierUpdateRequest.getId());
                    }
                }
                super.commitEdit(newValue);
            }
        });
        emailColumn.setCellFactory(cell -> new TextFieldTableCell<>(converter) {
            @Override
            public void commitEdit(String newValue) {
                if (!TextFieldsValidators.validateEmail(newValue)) {
                    informationArea.appendText("\nPodaj email w poprawnym formacie");
                    return;
                }
                if (!Objects.equals(newValue, getItem())) {
                    SupplierUpdateRequest supplierUpdateRequest = new SupplierUpdateRequest();
                    try {
                        BeanUtils.copyProperties(supplierUpdateRequest, suppliersTable.getSelectionModel().getSelectedItem());
                        supplierUpdateRequest.setEmail(newValue);
                        supplierService.update(supplierUpdateRequest);
                        informationArea.appendText("\nPomyślnie edytowano dostawcę o id " + supplierUpdateRequest.getId());
                    } catch (Exception e) {
                        informationArea.appendText("\nNie udało się edytować dostawcy o id " + supplierUpdateRequest.getId());
                    }
                }
                super.commitEdit(newValue);
            }
        });
        placeColumn.setCellFactory(cell -> new TextFieldTableCell<>(converter) {
            @Override
            public void commitEdit(String newValue) {
                if (!Objects.equals(newValue, getItem())) {
                    SupplierUpdateRequest supplierUpdateRequest = new SupplierUpdateRequest();
                    try {
                        BeanUtils.copyProperties(supplierUpdateRequest, suppliersTable.getSelectionModel().getSelectedItem());
                        supplierUpdateRequest.setPlace(newValue);
                        supplierService.update(supplierUpdateRequest);
                        informationArea.appendText("\nPomyślnie edytowano dostawcę o id " + supplierUpdateRequest.getId());
                    } catch (Exception e) {
                        informationArea.appendText("\nNie udało się edytować dostawcy o id " + supplierUpdateRequest.getId());
                    }
                }
                super.commitEdit(newValue);
            }
        });
        streetColumn.setCellFactory(cell -> new TextFieldTableCell<>(converter) {
            @Override
            public void commitEdit(String newValue) {
                if (!Objects.equals(newValue, getItem())) {
                    SupplierUpdateRequest supplierUpdateRequest = new SupplierUpdateRequest();
                    try {
                        BeanUtils.copyProperties(supplierUpdateRequest, suppliersTable.getSelectionModel().getSelectedItem());
                        supplierUpdateRequest.setStreet(newValue);
                        supplierService.update(supplierUpdateRequest);
                        informationArea.appendText("\nPomyślnie edytowano dostawcę o id " + supplierUpdateRequest.getId());
                    } catch (Exception e) {
                        informationArea.appendText("\nNie udało się edytować dostawcy o id " + supplierUpdateRequest.getId());
                    }
                }
                super.commitEdit(newValue);
            }
        });
        countryColumn.setCellFactory(cell -> new TextFieldTableCell<>(converter) {
            @Override
            public void commitEdit(String newValue) {
                if (!Objects.equals(newValue, getItem())) {
                    SupplierUpdateRequest supplierUpdateRequest = new SupplierUpdateRequest();
                    try {
                        BeanUtils.copyProperties(supplierUpdateRequest, suppliersTable.getSelectionModel().getSelectedItem());
                        supplierUpdateRequest.setCountry(newValue);
                        supplierService.update(supplierUpdateRequest);
                        informationArea.appendText("\nPomyślnie edytowano dostawcę o id " + supplierUpdateRequest.getId());
                    } catch (Exception e) {
                        informationArea.appendText("\nNie udało się edytować dostawcy o id " + supplierUpdateRequest.getId());
                    }
                }
                super.commitEdit(newValue);
            }
        });
        deleteColumn.setCellFactory(new Callback<>() {
            @Override
            public TableCell<SupplierDTO, Void> call(final TableColumn<SupplierDTO, Void> param) {
                final TableCell<SupplierDTO, Void> cell = new TableCell<>() {
                    final Image image = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/Images/deleteBtn.jpg")), 25, 25, false, false);
                    private final Button btn = new Button();

                    {
                        btn.setOnAction((ActionEvent event) -> {
                            Long id = getTableView().getItems().get(getIndex()).getId();
                            if (supplierService.delete(id)) {
                                suppliers.remove(getTableView().getItems().get(getIndex()));
                                informationArea.appendText("\nPomyślnie usunięto dostawcę o id " + id);
                            } else
                                informationArea.appendText("\nBłąd przy próbie usunięcia dostawcy o id " + id);
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
        suppliersTable.setItems(suppliers);
    }
}
