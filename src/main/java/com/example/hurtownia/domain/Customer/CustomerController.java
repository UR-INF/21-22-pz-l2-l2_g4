package com.example.hurtownia.domain.customer;

import com.example.hurtownia.controllers.ReportController;
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
public class CustomerController implements Initializable {

    public static ObservableList<Customer> customers = FXCollections.observableArrayList();
    @Autowired
    public CustomerService customerService;
    @FXML
    private TextArea informationArea;
    @FXML
    private TextField emailTextField, nameTextField, placeTextField, surnameTextField, buildingNumberTextField, apartmentNumberTextField, phoneNumberTextField, peselTextField, streetTextField;
    @FXML
    private TableView<Customer> customersTable;
    @FXML
    private TableColumn<Customer, String> emailColumn, idColumn, nameColumn, placeColumn, surnameColumn, buildingNumberColumn, apartmentNumberColumn, phoneNumberColumn, peselColumn, streetColumn;
    @FXML
    private TableColumn<Customer, Void> deleteColumn;
    @FXML
    private TextField idSearchField, nameSearchField, surnameSearchField, placeSearchField, streetSearchField, buildingNumberSearchField, apartmentNumberSearchField, emailSearchField, phoneNumberSearchField, peselSearchField;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        customersTable.setPlaceholder(new Label("Brak danych w tabeli"));
        informationArea.textProperty().addListener((ChangeListener<Object>) (observable, oldValue, newValue) -> informationArea.setScrollTop(Double.MAX_VALUE));
        setTable();
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
        try {
            Customer customer = Customer.builder()
                    .name(nameTextField.getText())
                    .surname(surnameTextField.getText())
                    .pesel(peselTextField.getText())
                    .phoneNumber(phoneNumberTextField.getText())
                    .email(emailTextField.getText())
                    .place(placeTextField.getText())
                    .street(streetTextField.getText())
                    .buildingNumber(Integer.valueOf(buildingNumberTextField.getText()))
                    .apartmentNumber(Integer.valueOf(apartmentNumberTextField.getText()))
                    .build();
            customerService.save(customer);
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
        //TODO: obsłużyć przycisk wyszukiwania
    }

    /**
     * Obsługuje przycisk generowania raportu z aktualnego widoku tabeli klientów.
     *
     * @param event
     */
    @FXML
    public void customersBtnReportClicked(MouseEvent event) {
        CustomerReport report = new CustomerReport(customersTable.getItems());
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
        nameColumn.setCellFactory(cell -> new TextFieldTableCell<>(converter) {
            @Override
            public void commitEdit(String newValue) {
                if (!Objects.equals(newValue, getItem())) {
                    Customer customer = customersTable.getSelectionModel().getSelectedItem();
                    try {
                        customer.setName(newValue);
                        customerService.update(customer);
                        informationArea.appendText("\nPomyślnie edytowano klienta o id " + customer.getId());
                    } catch (Exception e) {
                        informationArea.appendText("\nNie udało się edytować klienta o id " + customer.getId());
                    }
                }
                super.commitEdit(newValue);
            }
        });
        surnameColumn.setCellFactory(cell -> new TextFieldTableCell<>(converter) {
            @Override
            public void commitEdit(String newValue) {
                if (!Objects.equals(newValue, getItem())) {
                    Customer customer = customersTable.getSelectionModel().getSelectedItem();
                    try {
                        customer.setSurname(newValue);
                        customerService.update(customer);
                        informationArea.appendText("\nPomyślnie edytowano klienta o id " + customer.getId());
                    } catch (Exception e) {
                        informationArea.appendText("\nNie udało się edytować klienta o id " + customer.getId());
                    }
                }
                super.commitEdit(newValue);
            }
        });
        placeColumn.setCellFactory(cell -> new TextFieldTableCell<>(converter) {
            @Override
            public void commitEdit(String newValue) {
                if (!Objects.equals(newValue, getItem())) {
                    Customer customer = customersTable.getSelectionModel().getSelectedItem();
                    try {
                        customer.setPlace(newValue);
                        customerService.update(customer);
                        informationArea.appendText("\nPomyślnie edytowano klienta o id " + customer.getId());
                    } catch (Exception e) {
                        informationArea.appendText("\nNie udało się edytować klienta o id " + customer.getId());
                    }
                }
                super.commitEdit(newValue);
            }
        });
        streetColumn.setCellFactory(cell -> new TextFieldTableCell<>(converter) {
            @Override
            public void commitEdit(String newValue) {
                if (!Objects.equals(newValue, getItem())) {
                    Customer customer = customersTable.getSelectionModel().getSelectedItem();
                    try {
                        customer.setStreet(newValue);
                        customerService.update(customer);
                        informationArea.appendText("\nPomyślnie edytowano klienta o id " + customer.getId());
                    } catch (Exception e) {
                        informationArea.appendText("\nNie udało się edytować klienta o id " + customer.getId());
                    }
                }
                super.commitEdit(newValue);
            }
        });
        buildingNumberColumn.setCellFactory(cell -> new TextFieldTableCell<>(converter) {
            @Override
            public void commitEdit(String newValue) {
                if (!Objects.equals(newValue, getItem())) {
                    Customer customer = customersTable.getSelectionModel().getSelectedItem();
                    try {
                        customer.setBuildingNumber(Integer.parseInt(newValue));
                        customerService.update(customer);
                        informationArea.appendText("\nPomyślnie edytowano klienta o id " + customer.getId());
                    } catch (Exception e) {
                        informationArea.appendText("\nNie udało się edytować klienta o id " + customer.getId());
                    }
                }
                super.commitEdit(newValue);
            }
        });
        apartmentNumberColumn.setCellFactory(cell -> new TextFieldTableCell<>(converter) {
            @Override
            public void commitEdit(String newValue) {
                if (!Objects.equals(newValue, getItem())) {
                    Customer customer = customersTable.getSelectionModel().getSelectedItem();
                    try {
                        customer.setApartmentNumber(Integer.parseInt(newValue));
                        customerService.update(customer);
                        informationArea.appendText("\nPomyślnie edytowano klienta o id " + customer.getId());
                    } catch (Exception e) {
                        informationArea.appendText("\nNie udało się edytować klienta o id " + customer.getId());
                    }
                }
                super.commitEdit(newValue);
            }
        });
        emailColumn.setCellFactory(cell -> new TextFieldTableCell<>(converter) {
            @Override
            public void commitEdit(String newValue) {
                if (!Objects.equals(newValue, getItem())) {
                    Customer customer = customersTable.getSelectionModel().getSelectedItem();
                    try {
                        customer.setEmail(newValue);
                        customerService.update(customer);
                        informationArea.appendText("\nPomyślnie edytowano klienta o id " + customer.getId());
                    } catch (Exception e) {
                        informationArea.appendText("\nNie udało się edytować klienta o id " + customer.getId());
                    }
                }
                super.commitEdit(newValue);
            }
        });
        phoneNumberColumn.setCellFactory(cell -> new TextFieldTableCell<>(converter) {
            @Override
            public void commitEdit(String newValue) {
                if (!Objects.equals(newValue, getItem())) {
                    Customer customer = customersTable.getSelectionModel().getSelectedItem();
                    try {
                        customer.setPhoneNumber(newValue);
                        customerService.update(customer);
                        informationArea.appendText("\nPomyślnie edytowano klienta o id " + customer.getId());
                    } catch (Exception e) {
                        informationArea.appendText("\nNie udało się edytować klienta o id " + customer.getId());
                    }
                }
                super.commitEdit(newValue);
            }
        });
        peselColumn.setCellFactory(cell -> new TextFieldTableCell<>(converter) {
            @Override
            public void commitEdit(String newValue) {
                if (!Objects.equals(newValue, getItem())) {
                    Customer customer = customersTable.getSelectionModel().getSelectedItem();
                    try {
                        customer.setPesel(newValue);
                        customerService.update(customer);
                        informationArea.appendText("\nPomyślnie edytowano klienta o id " + customer.getId());
                    } catch (Exception e) {
                        informationArea.appendText("\nNie udało się edytować klienta o id " + customer.getId());
                    }
                }
                super.commitEdit(newValue);
            }
        });

        idColumn.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getId())));
        nameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getName()));
        surnameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getSurname()));
        placeColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getPlace()));
        streetColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getStreet()));
        buildingNumberColumn.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getBuildingNumber())));
        apartmentNumberColumn.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getApartmentNumber())));
        emailColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getEmail()));
        phoneNumberColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getPhoneNumber()));
        peselColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getPesel()));
        deleteColumn.setCellFactory(new Callback<>() {
            @Override
            public TableCell<Customer, Void> call(final TableColumn<Customer, Void> param) {
                final TableCell<Customer, Void> cell = new TableCell<>() {
                    final Image image = new Image(getClass().getResourceAsStream("/Images/deleteBtn.jpg"), 25, 25, false, false);
                    private final Button btn = new Button();

                    {
                        btn.setOnAction((ActionEvent event) -> {
                            Customer customer = getTableView().getItems().get(getIndex());
                            if (customerService.delete(customer)) {
                                customers.remove(customer);
                                informationArea.appendText("\nPomyślnie usunięto klienta o id " + customer.getId());
                            } else
                                informationArea.appendText("\nBłąd przy próbie usunięcia klienta o id " + customer.getId());
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
        customersTable.setItems(customers);
    }
}
