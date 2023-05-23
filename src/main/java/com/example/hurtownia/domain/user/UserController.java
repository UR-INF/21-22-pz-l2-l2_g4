package com.example.hurtownia.domain.user;

import com.example.hurtownia.controllers.ReportController;
import com.example.hurtownia.domain.user.request.UserCreateRequest;
import com.example.hurtownia.domain.user.request.UserUpdateRequest;
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
import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

@Controller
public class UserController implements Initializable {

    public static ObservableList<UserDTO> users = FXCollections.observableArrayList();
    public Button generateReportBtn;
    public TextField isAdaminSearchField;
    @FXML
    private CheckBox isAdminCheckBox, grantingDiscountsCheckBox, generatingReportsCheckBox;
    @FXML
    private TextArea informationArea;
    @FXML
    private TextField emailTextField, passwordTextField, nameTextField, surnameTextField, phoneNumberTextField;
    @FXML
    private TableView<UserDTO> usersTable;
    @FXML
    private TableColumn<UserDTO, Number> idColumn;
    @FXML
    private TableColumn<UserDTO, String> emailColumn, passwordColumn, nameColumn, surnameColumn, phoneNumberColumn, isAdminColumn, generatingReportsColumn, grantingDiscountsColumn;
    @FXML
    private TableColumn<UserDTO, Void> deleteColumn;
    @FXML
    private TextField idSearchField, nameSearchField, surnameSearchField, phoneNumberSearchField, emailSearchField;
    @Autowired
    private UserService userService;
    @Autowired
    public UserReport userReport;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        usersTable.setPlaceholder(new Label("Brak danych w tabeli"));
        informationArea.textProperty().addListener((ChangeListener<Object>) (observable, oldValue, newValue) -> informationArea.setScrollTop(Double.MAX_VALUE));
        setTable();

        isAdminCheckBox.selectedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                generatingReportsCheckBox.setSelected(true);
                generatingReportsCheckBox.setDisable(true);
                grantingDiscountsCheckBox.setSelected(true);
                grantingDiscountsCheckBox.setDisable(true);
            } else {
                generatingReportsCheckBox.setSelected(false);
                generatingReportsCheckBox.setDisable(false);
                grantingDiscountsCheckBox.setSelected(false);
                grantingDiscountsCheckBox.setDisable(false);
            }
        });
    }

    /**
     * Obsługuje przycisk wyświetlenia wszystkich użytkowników.
     *
     * @param event
     */
    @FXML
    public void usersBtnShowClicked(MouseEvent event) {
        usersTable.getItems().clear();
        users.setAll(userService.findAll());
    }

    /**
     * Obsługuje przycisk dodawania nowego użytkownika.
     *
     * @param event
     */
    @FXML
    public void usersBtnAddClicked(MouseEvent event) {
        try {
            String name = nameTextField.getText();
            String surname = surnameTextField.getText();
            String email = emailTextField.getText();
            String password = passwordTextField.getText();
            String phoneNumber = phoneNumberTextField.getText();
            Boolean isAdmin = isAdminCheckBox.isSelected();
            Boolean generatingReports = generatingReportsCheckBox.isSelected();
            Boolean grantingDiscounts = grantingDiscountsCheckBox.isSelected();
            UserCreateRequest userCreateRequest = UserCreateRequest.builder()
                    .name(name)
                    .surname(surname)
                    .email(email)
                    .password(password)
                    .phoneNumber(phoneNumber)
                    .isAdmin(isAdmin)
                    .generatingReports(generatingReports)
                    .grantingDiscounts(grantingDiscounts)
                    .build();
            userService.create(userCreateRequest);
            informationArea.appendText("\nDodano nowego użytkownika");
        } catch (UnsupportedOperationException e) {
            informationArea.appendText("\nIstnieje już użytkownik o takim emailu.");
        } catch (Exception e) {
            informationArea.appendText("\nNie udało się dodać nowego użytkownika");
        }
    }

    /**
     * Obsługuje przycisk wyszukiwania użytkowników z uwzględnieniem filtrów.
     *
     * @param event
     */
    @FXML
    public void usersBtnSearchClicked(MouseEvent event) {
        String idFilter = idSearchField.getText().trim();
        String nameFilter = nameSearchField.getText().trim();
        String surnameFilter = surnameSearchField.getText().trim();
        String phoneNumberFilter = phoneNumberSearchField.getText().trim();
        String emailFilter = emailSearchField.getText().trim();
        List<UserDTO> filteredUsersList = userService.findAll();

        if (!(nameFilter.isEmpty() && surnameFilter.isEmpty() && phoneNumberFilter.isEmpty() && emailFilter.isEmpty() && idFilter.isEmpty())) {
            filteredUsersList = filteredUsersList.stream().filter(
                    userDTO -> userDTO.getName().contains(nameFilter)
                            && userDTO.getSurname().contains(surnameFilter)
                            && userDTO.getEmail().contains(emailFilter)
                            && userDTO.getPhoneNumber().contains(phoneNumberFilter)
                            && userDTO.getId().toString().contains(idFilter)
            ).collect(Collectors.toList());
        }
        usersTable.getItems().clear();
        users.setAll(filteredUsersList);
    }

    /**
     * Obsługuje przycisk generowania raportu z aktualnego widoku tabeli użytkowników.
     *
     * @param event
     */
    @FXML
    public void usersBtnReportClicked(MouseEvent event) {
        userReport.setData(usersTable.getItems());
        try {
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setOpacity(1);
            stage.setTitle("Generuj raport");
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/FXML/report-save-view.fxml"));
            Parent root = fxmlLoader.load();
            ReportController controller = fxmlLoader.getController();
            controller.setReport(userReport);
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
        phoneNumberColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getPhoneNumber()));
        emailColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getEmail()));
        passwordColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getPassword()));
        isAdminColumn.setCellValueFactory(cellData -> new SimpleStringProperty(BooleanConverter.convertToString(cellData.getValue().getIsAdmin())));
        generatingReportsColumn.setCellValueFactory(cellData -> new SimpleStringProperty(BooleanConverter.convertToString(cellData.getValue().getGeneratingReports())));
        grantingDiscountsColumn.setCellValueFactory(cellData -> new SimpleStringProperty(BooleanConverter.convertToString(cellData.getValue().getGrantingDiscounts())));

        StringConverter<String> converter = new DefaultStringConverter();
        nameColumn.setCellFactory(cell -> new TextFieldTableCell<>(converter) {
            @Override
            public void commitEdit(String newValue) {
                if (!Objects.equals(newValue, getItem())) {
                    UserUpdateRequest userUpdateRequest = new UserUpdateRequest();
                    try {
                        BeanUtils.copyProperties(userUpdateRequest, usersTable.getSelectionModel().getSelectedItem());
                        userUpdateRequest.setIsAdmin(usersTable.getSelectionModel().getSelectedItem().getIsAdmin());
                        userUpdateRequest.setName(newValue);
                        userService.update(userUpdateRequest);
                        informationArea.appendText("\nPomyślnie edytowano użytkownika o id " + userUpdateRequest.getId());
                    } catch (Exception e) {
                        informationArea.appendText("\nNie udało się edytować użytkownika o id " + userUpdateRequest.getId());
                    }
                }
                super.commitEdit(newValue);
            }
        });
        surnameColumn.setCellFactory(cell -> new TextFieldTableCell<>(converter) {
            @Override
            public void commitEdit(String newValue) {
                if (!Objects.equals(newValue, getItem())) {
                    UserUpdateRequest userUpdateRequest = new UserUpdateRequest();
                    try {
                        BeanUtils.copyProperties(userUpdateRequest, usersTable.getSelectionModel().getSelectedItem());
                        userUpdateRequest.setSurname(newValue);
                        userService.update(userUpdateRequest);
                        informationArea.appendText("\nPomyślnie edytowano użytkownika o id " + userUpdateRequest.getId());
                    } catch (Exception e) {
                        informationArea.appendText("\nNie udało się edytować użytkownika o id " + userUpdateRequest.getId());
                    }
                }
                super.commitEdit(newValue);
            }
        });
        phoneNumberColumn.setCellFactory(cell -> new TextFieldTableCell<>(converter) {
            @Override
            public void commitEdit(String newValue) {
                if (!Objects.equals(newValue, getItem())) {
                    UserUpdateRequest userUpdateRequest = new UserUpdateRequest();
                    try {
                        BeanUtils.copyProperties(userUpdateRequest, usersTable.getSelectionModel().getSelectedItem());
                        userUpdateRequest.setPhoneNumber(newValue);
                        userService.update(userUpdateRequest);
                        informationArea.appendText("\nPomyślnie edytowano użytkownika o id " + userUpdateRequest.getId());
                    } catch (Exception e) {
                        informationArea.appendText("\nNie udało się edytować użytkownika o id " + userUpdateRequest.getId());
                    }
                }
                super.commitEdit(newValue);
            }
        });
        emailColumn.setCellFactory(cell -> new TextFieldTableCell<>(converter) {
            @Override
            public void commitEdit(String newValue) {
                if (!Objects.equals(newValue, getItem())) {
                    UserUpdateRequest userUpdateRequest = new UserUpdateRequest();
                    try {
                        BeanUtils.copyProperties(userUpdateRequest, usersTable.getSelectionModel().getSelectedItem());
                        userUpdateRequest.setEmail(newValue);
                        userService.update(userUpdateRequest);
                        informationArea.appendText("\nPomyślnie edytowano użytkownika o id " + userUpdateRequest.getId());
                    } catch (UnsupportedOperationException e) {
                        informationArea.appendText("\nIstnieje już użytkownik o takim emailu.");
                        return;
                    } catch (Exception e) {
                        informationArea.appendText("\nNie udało się edytować użytkownika o id " + userUpdateRequest.getId());
                    }
                }
                super.commitEdit(newValue);
            }
        });
        passwordColumn.setCellFactory(cell -> new TextFieldTableCell<>(converter) {
            @Override
            public void commitEdit(String newValue) {
                if (!Objects.equals(newValue, getItem())) {
                    UserUpdateRequest userUpdateRequest = new UserUpdateRequest();
                    try {
                        BeanUtils.copyProperties(userUpdateRequest, usersTable.getSelectionModel().getSelectedItem());
                        userUpdateRequest.setPassword(newValue);
                        userService.update(userUpdateRequest);
                        informationArea.appendText("\nPomyślnie edytowano użytkownika o id " + userUpdateRequest.getId());
                    } catch (Exception e) {
                        informationArea.appendText("\nNie udało się edytować użytkownika o id " + userUpdateRequest.getId());
                    }
                }
                super.commitEdit(newValue);
            }
        });
        isAdminColumn.setCellFactory(ComboBoxTableCell.forTableColumn(new String[]{"Tak", "Nie"}));
        isAdminColumn.setOnEditCommit(t -> {
            UserUpdateRequest userUpdateRequest = new UserUpdateRequest();
            if (!Objects.equals(t.getNewValue(), t.getOldValue())) {
                try {
                    BeanUtils.copyProperties(userUpdateRequest, usersTable.getSelectionModel().getSelectedItem());
                    usersTable.getSelectionModel().getSelectedItem().setIsAdmin(BooleanConverter.convertToBoolean(t.getNewValue()));
                    userUpdateRequest.setIsAdmin(BooleanConverter.convertToBoolean(t.getNewValue()));
                    userService.update(userUpdateRequest);
                    informationArea.appendText("\nPomyślnie edytowano użytkownika o id " + userUpdateRequest.getId());
                } catch (Exception e) {
                    informationArea.appendText("\nNie udało się edytować użytkownika o id " + userUpdateRequest.getId());
                }
            }
        });
        generatingReportsColumn.setCellFactory(ComboBoxTableCell.forTableColumn(new String[]{"Tak", "Nie"}));
        generatingReportsColumn.setOnEditCommit(t -> {
            UserUpdateRequest userUpdateRequest = new UserUpdateRequest();
            if (!Objects.equals(t.getNewValue(), t.getOldValue())) {
                try {
                    BeanUtils.copyProperties(userUpdateRequest, usersTable.getSelectionModel().getSelectedItem());
                    usersTable.getSelectionModel().getSelectedItem().setGeneratingReports(BooleanConverter.convertToBoolean(t.getNewValue()));
                    userUpdateRequest.setGeneratingReports(BooleanConverter.convertToBoolean(t.getNewValue()));
                    userService.update(userUpdateRequest);
                    informationArea.appendText("\nPomyślnie edytowano użytkownika o id " + userUpdateRequest.getId());
                } catch (Exception e) {
                    informationArea.appendText("\nNie udało się edytować użytkownika o id " + userUpdateRequest.getId());
                }
            }
        });
        grantingDiscountsColumn.setCellFactory(ComboBoxTableCell.forTableColumn(new String[]{"Tak", "Nie"}));
        grantingDiscountsColumn.setOnEditCommit(t -> {
            UserUpdateRequest userUpdateRequest = new UserUpdateRequest();
            if (!Objects.equals(t.getNewValue(), t.getOldValue())) {
                try {
                    BeanUtils.copyProperties(userUpdateRequest, usersTable.getSelectionModel().getSelectedItem());
                    usersTable.getSelectionModel().getSelectedItem().setGrantingDiscounts(BooleanConverter.convertToBoolean(t.getNewValue()));
                    userUpdateRequest.setGrantingDiscounts(BooleanConverter.convertToBoolean(t.getNewValue()));
                    userService.update(userUpdateRequest);
                    informationArea.appendText("\nPomyślnie edytowano użytkownika o id " + userUpdateRequest.getId());
                } catch (Exception e) {
                    informationArea.appendText("\nNie udało się edytować użytkownika o id " + userUpdateRequest.getId());
                }
            }
        });
        deleteColumn.setCellFactory(new Callback<>() {
            @Override
            public TableCell<UserDTO, Void> call(final TableColumn<UserDTO, Void> param) {
                final TableCell<UserDTO, Void> cell = new TableCell<>() {
                    final Image image = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/Images/deleteBtn.jpg")), 25, 25, false, false);
                    private final Button btn = new Button();

                    {
                        btn.setOnAction((ActionEvent event) -> {
                            Long id = getTableView().getItems().get(getIndex()).getId();
                            if (userService.delete(id)) {
                                users.remove(getTableView().getItems().get(getIndex()));
                                informationArea.appendText("\nPomyślnie usunięto użytkownika o id " + id);
                            } else
                                informationArea.appendText("\nBłąd przy próbie usunięcia użytkownika o id " + id);
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
        usersTable.setItems(users);
    }
}
