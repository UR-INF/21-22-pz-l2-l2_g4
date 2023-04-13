package com.example.hurtownia.domain.supplier;

import com.example.hurtownia.controllers.ReportController;
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
import java.util.Objects;
import java.util.ResourceBundle;

@Controller
public class SupplierController implements Initializable {

    public static ObservableList<SupplierDTO> suppliers = FXCollections.observableArrayList();
    @FXML
    private TextArea informationArea;
    @FXML
    private TextField emailTextField, countryTextField, placeTextField, nameTextField, nipTextField, streetTextField;
    @FXML
    private TableView<SupplierDTO> suppliersTable;
    @FXML
    private TableColumn<SupplierDTO, Number> idColumn;
    @FXML
    private TableColumn<SupplierDTO, String> emailColumn, nipColumn, countryColumn, placeColumn, nameColumn, streetColumn;
    @FXML
    private TableColumn<SupplierDTO, Void> deleteColumn;
    @FXML
    private TextField idSearchField, nameSearchField, nipSearchField, emailSearchField, placeSearchField, streetSearchField, countrySearchField;
    @Autowired
    private SupplierService supplierService;
    @FXML
    private Button generateReportBtn;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        suppliersTable.setPlaceholder(new Label("Brak danych w tabeli"));
        informationArea.textProperty().addListener((ChangeListener<Object>) (observable, oldValue, newValue) -> informationArea.setScrollTop(Double.MAX_VALUE));
        setTable();
    }

    public void disableGeneratingReports() {
        generateReportBtn.setDisable(true);
    }

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
            SupplierCreateRequest supplierCreateRequest = SupplierCreateRequest.builder()
                    .name(name)
                    .email(email)
                    .country(country)
                    .place(place)
                    .street(street)
                    .nip(nip)
                    .build();
            supplierService.save(supplierCreateRequest);
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
        //TODO: obsłużyć przycisk wyszukiwania
    }

    /**
     * Obsługuje przycisk generowania raportu z aktualnego widoku tabeli dostawców.
     *
     * @param event
     */
    @FXML
    public void suppliersBtnReportClicked(MouseEvent event) {
        SupplierReport report = new SupplierReport(suppliersTable.getItems());
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
