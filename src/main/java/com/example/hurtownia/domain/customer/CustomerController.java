package com.example.hurtownia.domain.customer;

import com.example.hurtownia.controllers.ReportController;
import com.example.hurtownia.domain.customer.request.CustomerCreateRequest;
import com.example.hurtownia.domain.customer.request.CustomerUpdateRequest;
import com.example.hurtownia.validation.TextFieldsValidators;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

/**
 * Kontroller zakładki 'Zarządzanie użytkownikami'.
 */
@Controller
public class CustomerController implements Initializable {
    public static ObservableList<CustomerDTO> customers = FXCollections.observableArrayList();
    @Autowired
    public CustomerService customerService;
    @Autowired
    public CustomerReport customerReport;
    @FXML
    public TextArea informationArea;
    @FXML
    public TextField emailTextField, nameTextField, placeTextField, surnameTextField, buildingNumberTextField, apartmentNumberTextField, phoneNumberTextField, peselTextField, streetTextField, zipCodeTextField;
    @FXML
    public TableView<CustomerDTO> customersTable;
    @FXML
    private TableColumn<CustomerDTO, Number> idColumn, buildingNumberColumn, apartmentNumberColumn;
    @FXML
    private TableColumn<CustomerDTO, String> emailColumn, nameColumn, placeColumn, surnameColumn, phoneNumberColumn, peselColumn, streetColumn, zipCodeColumn;
    @FXML
    private TableColumn<CustomerDTO, Void> deleteColumn;
    @FXML
    public TextField idSearchField, nameSearchField, surnameSearchField, placeSearchField, streetSearchField, buildingNumberSearchField, apartmentNumberSearchField, emailSearchField, phoneNumberSearchField, peselSearchField, zipCodeSearchField;
    @FXML
    private Button generateReportBtn;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        customersTable.setPlaceholder(new Label("Brak danych w tabeli"));
        informationArea.setEditable(false);
        informationArea.textProperty().addListener((ChangeListener<Object>) (observable, oldValue, newValue) -> informationArea.setScrollTop(Double.MAX_VALUE));
        setTable();
    }

    /**
     * Dezaktywuje przycisk generowania raportów.
     */
    public void disableGeneratingReports() {
        generateReportBtn.setDisable(true);
    }

    /**
     * Aktywuje przycisk generowania raportów.
     */
    public void enableGeneratingReports() {
        generateReportBtn.setDisable(false);
    }

    /**
     * Obsługuje przycisk wyświetlenia wszystkich klientów.
     *
     * @param event
     */
    @FXML
    public void customersBtnShowClicked(MouseEvent event) {
        customersTable.getItems().clear();
        customers.setAll(customerService.findAll());
    }

    /**
     * Obsługuje przycisk dodawania nowego klienta.
     *
     * @param event
     */
    @FXML
    public void customersBtnAddClicked(MouseEvent event) {
        if (!TextFieldsValidators.validateInteger(buildingNumberTextField.getText())) {
            informationArea.appendText("\n Podaj poprawny numer budynku");
            return;
        }
        if (!TextFieldsValidators.validateInteger(apartmentNumberTextField.getText())) {
            informationArea.appendText("\n Podaj poprawny numer mieszkania");
            return;
        }
        try {
            String name = nameTextField.getText();
            String surname = surnameTextField.getText();
            String pesel = peselTextField.getText();
            String phoneNumber = phoneNumberTextField.getText();
            String email = emailTextField.getText();
            String place = placeTextField.getText();
            String street = streetTextField.getText();
            String zipCode = zipCodeTextField.getText();
            Integer buildingNumber = Integer.valueOf(buildingNumberTextField.getText());
            Integer apartmentNumber = Integer.valueOf(apartmentNumberTextField.getText());

            if (name.isEmpty()
                    || surname.isEmpty()
                    || pesel.isEmpty()
                    || phoneNumber.isEmpty()
                    || email.isEmpty()
                    || place.isEmpty()
                    || street.isEmpty()
                    || zipCode.isEmpty()
            ) {
                informationArea.appendText("\n Podaj wszystkie informacje");
                return;
            }
            if (!TextFieldsValidators.validateEmail(email)) {
                informationArea.appendText("\n Podaj email w poprawnym formacie");
                return;
            }
            if (!TextFieldsValidators.validatePhoneNumber(phoneNumber)) {
                informationArea.appendText("\n Podaj numer telefonu w poprawnym formacie (9 cyfr)");
                return;
            }
            if (!TextFieldsValidators.validatePesel(pesel)) {
                informationArea.appendText("\n Podaj pesel w poprawnym formacie (11 cyfr)");
                return;
            }
            if (!TextFieldsValidators.validatePostalCode(zipCode)) {
                informationArea.appendText("\n Podaj kod pocztowy w poprawnym formacie (xx-xxx)");
                return;
            }
            CustomerCreateRequest customerCreateRequest = CustomerCreateRequest.builder()
                    .name(name)
                    .surname(surname)
                    .pesel(pesel)
                    .phoneNumber(phoneNumber)
                    .email(email)
                    .place(place)
                    .street(street)
                    .zipCode(zipCode)
                    .buildingNumber(buildingNumber)
                    .apartmentNumber(apartmentNumber)
                    .build();
            customerService.create(customerCreateRequest);
            informationArea.appendText("\nDodano nowego klienta");
        } catch (Exception e) {
            informationArea.appendText("\nNie udało się dodać nowego klienta");
        }
    }

    /**
     * Obsługuje przycisk wyszukiwania klientów z uwzględnieniem filtrów.
     *
     * @param event
     */
    @FXML
    public void customersBtnSearchClicked(MouseEvent event) {
        String idFilter = idSearchField.getText().trim();
        String nameFilter = nameSearchField.getText().trim();
        String surnameFilter = surnameSearchField.getText().trim();
        String placeFilter = placeSearchField.getText().trim();
        String streetFilter = streetSearchField.getText().trim();
        String buildingNumberFilter = buildingNumberSearchField.getText().trim();
        String apartmentNumberFilter = apartmentNumberSearchField.getText().trim();
        String emailFilter = emailSearchField.getText().trim();
        String phoneNumberFilter = phoneNumberSearchField.getText().trim();
        String peselFilter = peselSearchField.getText().trim();
        String zipCodeFilter = zipCodeSearchField.getText().trim();
        List<CustomerDTO> filteredCustomersList = customerService.findAll();

        if (!(idFilter.isEmpty() && nameFilter.isEmpty() && surnameFilter.isEmpty() && placeFilter.isEmpty() &&
                streetFilter.isEmpty() && buildingNumberFilter.isEmpty() && apartmentNumberFilter.isEmpty() &&
                emailFilter.isEmpty() && phoneNumberFilter.isEmpty() && peselFilter.isEmpty() && zipCodeFilter.isEmpty())) {
            filteredCustomersList = filteredCustomersList.stream().filter(
                    customerDTO -> customerDTO.getId().toString().contains(idFilter)
                            && customerDTO.getName().contains(nameFilter)
                            && customerDTO.getSurname().contains(surnameFilter)
                            && customerDTO.getPlace().contains(placeFilter)
                            && customerDTO.getStreet().contains(streetFilter)
                            && customerDTO.getBuildingNumber().toString().contains(buildingNumberFilter)
                            && customerDTO.getApartmentNumber().toString().contains(apartmentNumberFilter)
                            && customerDTO.getEmail().contains(emailFilter)
                            && customerDTO.getPhoneNumber().contains(phoneNumberFilter)
                            && customerDTO.getPesel().contains(peselFilter)
                            && customerDTO.getZipCode().contains(zipCodeFilter)
            ).collect(Collectors.toList());
        }
        customersTable.getItems().clear();
        customers.setAll(filteredCustomersList);
    }

    /**
     * Obsługuje przycisk generowania raportu z aktualnego widoku tabeli klientów.
     *
     * @param event
     */
    @FXML
    public void customersBtnReportClicked(MouseEvent event) {
        customerReport.setData(customersTable.getItems());
        try {
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setOpacity(1);
            stage.setTitle("Generuj raport");
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/FXML/report-save-view.fxml"));
            Parent root = fxmlLoader.load();
            ReportController controller = fxmlLoader.getController();
            controller.setReport(customerReport);
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
        surnameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getSurname()));
        placeColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getPlace()));
        zipCodeColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getZipCode()));
        streetColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getStreet()));
        buildingNumberColumn.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getBuildingNumber()));
        apartmentNumberColumn.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getApartmentNumber()));
        emailColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getEmail()));
        phoneNumberColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getPhoneNumber()));
        peselColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getPesel()));

        StringConverter<String> stringConverter = new DefaultStringConverter();
        NumberStringConverter numberConverter = new NumberStringConverter();
        nameColumn.setCellFactory(cell -> new TextFieldTableCell<>(stringConverter) {
            @Override
            public void commitEdit(String newValue) {
                if (!Objects.equals(newValue, getItem())) {
                    CustomerUpdateRequest customerUpdateRequest = new CustomerUpdateRequest();
                    try {
                        BeanUtils.copyProperties(customerUpdateRequest, customersTable.getSelectionModel().getSelectedItem());
                        customerUpdateRequest.setName(newValue);
                        customerService.update(customerUpdateRequest);
                        informationArea.appendText("\nPomyślnie edytowano klienta o id " + customerUpdateRequest.getId());
                    } catch (Exception e) {
                        informationArea.appendText("\nNie udało się edytować klienta o id " + customerUpdateRequest.getId());
                    }
                }
                super.commitEdit(newValue);
            }
        });
        surnameColumn.setCellFactory(cell -> new TextFieldTableCell<>(stringConverter) {
            @Override
            public void commitEdit(String newValue) {
                if (!Objects.equals(newValue, getItem())) {
                    CustomerUpdateRequest customerUpdateRequest = new CustomerUpdateRequest();
                    try {
                        BeanUtils.copyProperties(customerUpdateRequest, customersTable.getSelectionModel().getSelectedItem());
                        customerUpdateRequest.setSurname(newValue);
                        customerService.update(customerUpdateRequest);
                        informationArea.appendText("\nPomyślnie edytowano klienta o id " + customerUpdateRequest.getId());
                    } catch (Exception e) {
                        informationArea.appendText("\nNie udało się edytować klienta o id " + customerUpdateRequest.getId());
                    }
                }
                super.commitEdit(newValue);
            }
        });
        placeColumn.setCellFactory(cell -> new TextFieldTableCell<>(stringConverter) {
            @Override
            public void commitEdit(String newValue) {
                if (!Objects.equals(newValue, getItem())) {
                    CustomerUpdateRequest customerUpdateRequest = new CustomerUpdateRequest();
                    try {
                        BeanUtils.copyProperties(customerUpdateRequest, customersTable.getSelectionModel().getSelectedItem());
                        customerUpdateRequest.setPlace(newValue);
                        customerService.update(customerUpdateRequest);
                        informationArea.appendText("\nPomyślnie edytowano klienta o id " + customerUpdateRequest.getId());
                    } catch (Exception e) {
                        informationArea.appendText("\nNie udało się edytować klienta o id " + customerUpdateRequest.getId());
                    }
                }
                super.commitEdit(newValue);
            }
        });
        zipCodeColumn.setCellFactory(cell -> new TextFieldTableCell<>(stringConverter) {
            @Override
            public void commitEdit(String newValue) {
                if (!TextFieldsValidators.validatePostalCode(newValue)) {
                    informationArea.appendText("\nPodaj kod pocztowy w poprawnym formacie (xx-xxx)");
                    return;
                }
                if (!Objects.equals(newValue, getItem())) {
                    CustomerUpdateRequest customerUpdateRequest = new CustomerUpdateRequest();
                    try {
                        BeanUtils.copyProperties(customerUpdateRequest, customersTable.getSelectionModel().getSelectedItem());
                        customerUpdateRequest.setZipCode(newValue);
                        customerService.update(customerUpdateRequest);
                        informationArea.appendText("\nPomyślnie edytowano klienta o id " + customerUpdateRequest.getId());
                    } catch (Exception e) {
                        informationArea.appendText("\nNie udało się edytować klienta o id " + customerUpdateRequest.getId());
                    }
                }
                super.commitEdit(newValue);
            }
        });
        streetColumn.setCellFactory(cell -> new TextFieldTableCell<>(stringConverter) {
            @Override
            public void commitEdit(String newValue) {
                if (!Objects.equals(newValue, getItem())) {
                    CustomerUpdateRequest customerUpdateRequest = new CustomerUpdateRequest();
                    try {
                        BeanUtils.copyProperties(customerUpdateRequest, customersTable.getSelectionModel().getSelectedItem());
                        customerUpdateRequest.setStreet(newValue);
                        customerService.update(customerUpdateRequest);
                        informationArea.appendText("\nPomyślnie edytowano klienta o id " + customerUpdateRequest.getId());
                    } catch (Exception e) {
                        informationArea.appendText("\nNie udało się edytować klienta o id " + customerUpdateRequest.getId());
                    }
                }
                super.commitEdit(newValue);
            }
        });
        buildingNumberColumn.setCellFactory(cell -> new TextFieldTableCell<>(numberConverter) {
            @Override
            public void commitEdit(Number newValue) {
                if (newValue.intValue() < 0) {
                    informationArea.appendText("\nPodaj nieujemną liczbę");
                    return;
                }
                if (!Objects.equals(newValue, getItem())) {
                    CustomerUpdateRequest customerUpdateRequest = new CustomerUpdateRequest();
                    try {
                        BeanUtils.copyProperties(customerUpdateRequest, customersTable.getSelectionModel().getSelectedItem());
                        customerUpdateRequest.setBuildingNumber(newValue.intValue());
                        customerService.update(customerUpdateRequest);
                        informationArea.appendText("\nPomyślnie edytowano klienta o id " + customerUpdateRequest.getId());
                    } catch (Exception e) {
                        informationArea.appendText("\nNie udało się edytować klienta o id " + customerUpdateRequest.getId());
                    }
                }
                super.commitEdit(newValue);
            }
        });
        apartmentNumberColumn.setCellFactory(cell -> new TextFieldTableCell<>(numberConverter) {
            @Override
            public void commitEdit(Number newValue) {
                if (newValue.intValue() < 0) {
                    informationArea.appendText("\nPodaj nieujemną liczbę");
                    return;
                }
                if (!Objects.equals(newValue, getItem())) {
                    CustomerUpdateRequest customerUpdateRequest = new CustomerUpdateRequest();
                    try {
                        BeanUtils.copyProperties(customerUpdateRequest, customersTable.getSelectionModel().getSelectedItem());
                        customerUpdateRequest.setApartmentNumber(newValue.intValue());
                        customerService.update(customerUpdateRequest);
                        informationArea.appendText("\nPomyślnie edytowano klienta o id " + customerUpdateRequest.getId());
                    } catch (Exception e) {
                        informationArea.appendText("\nNie udało się edytować klienta o id " + customerUpdateRequest.getId());
                    }
                }
                super.commitEdit(newValue);
            }
        });
        emailColumn.setCellFactory(cell -> new TextFieldTableCell<>(stringConverter) {
            @Override
            public void commitEdit(String newValue) {
                if (!TextFieldsValidators.validateEmail(newValue)) {
                    informationArea.appendText("\nPodaj email w poprawnym formacie");
                    return;
                }
                if (!Objects.equals(newValue, getItem())) {
                    CustomerUpdateRequest customerUpdateRequest = new CustomerUpdateRequest();
                    try {
                        BeanUtils.copyProperties(customerUpdateRequest, customersTable.getSelectionModel().getSelectedItem());
                        customerUpdateRequest.setEmail(newValue);
                        customerService.update(customerUpdateRequest);
                        informationArea.appendText("\nPomyślnie edytowano klienta o id " + customerUpdateRequest.getId());
                    } catch (Exception e) {
                        informationArea.appendText("\nNie udało się edytować klienta o id " + customerUpdateRequest.getId());
                    }
                }
                super.commitEdit(newValue);
            }
        });
        phoneNumberColumn.setCellFactory(cell -> new TextFieldTableCell<>(stringConverter) {
            @Override
            public void commitEdit(String newValue) {
                if (!TextFieldsValidators.validatePhoneNumber(newValue)) {
                    informationArea.appendText("\nPodaj numer telefonu w poprawnym formacie (9 cyfr)");
                    return;
                }
                if (!Objects.equals(newValue, getItem())) {
                    CustomerUpdateRequest customerUpdateRequest = new CustomerUpdateRequest();
                    try {
                        BeanUtils.copyProperties(customerUpdateRequest, customersTable.getSelectionModel().getSelectedItem());
                        customerUpdateRequest.setPhoneNumber(newValue);
                        customerService.update(customerUpdateRequest);
                        informationArea.appendText("\nPomyślnie edytowano klienta o id " + customerUpdateRequest.getId());
                    } catch (Exception e) {
                        informationArea.appendText("\nNie udało się edytować klienta o id " + customerUpdateRequest.getId());
                    }
                }
                super.commitEdit(newValue);
            }
        });
        peselColumn.setCellFactory(cell -> new TextFieldTableCell<>(stringConverter) {
            @Override
            public void commitEdit(String newValue) {
                if (!TextFieldsValidators.validatePesel(newValue)) {
                    informationArea.appendText("\nPodaj pesel w poprawnym formacie (11 cyfr)");
                    return;
                }
                if (!Objects.equals(newValue, getItem())) {
                    CustomerUpdateRequest customerUpdateRequest = new CustomerUpdateRequest();
                    try {
                        BeanUtils.copyProperties(customerUpdateRequest, customersTable.getSelectionModel().getSelectedItem());
                        customerUpdateRequest.setPesel(newValue);
                        customerService.update(customerUpdateRequest);
                        informationArea.appendText("\nPomyślnie edytowano klienta o id " + customerUpdateRequest.getId());
                    } catch (Exception e) {
                        informationArea.appendText("\nNie udało się edytować klienta o id " + customerUpdateRequest.getId());
                    }
                }
                super.commitEdit(newValue);
            }
        });
        deleteColumn.setCellFactory(new Callback<>() {
            @Override
            public TableCell<CustomerDTO, Void> call(final TableColumn<CustomerDTO, Void> param) {
                final TableCell<CustomerDTO, Void> cell = new TableCell<>() {
                    final Image image = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/Images/deleteBtn.jpg")), 25, 25, false, false);
                    private final Button btn = new Button();

                    {
                        btn.setOnAction((ActionEvent event) -> {
                            Long id = getTableView().getItems().get(getIndex()).getId();
                            if (customerService.delete(id)) {
                                customers.remove(getTableView().getItems().get(getIndex()));
                                informationArea.appendText("\nPomyślnie usunięto klienta o id " + id);
                            } else
                                informationArea.appendText("\nBłąd przy próbie usunięcia klienta o id " + id);
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
        customersTable.setItems(customers);
    }
}
