package com.example.hurtownia.domain.user;

import com.example.hurtownia.controllers.ReportController;
import javafx.beans.property.SimpleBooleanProperty;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

@Controller
public class UserController implements Initializable {

    public static ObservableList<UserTableViewDTO> users = FXCollections.observableArrayList();
    @FXML
    private CheckBox isAdminCheckBox, grantingDiscountsCheckBox, generatingReportsCheckBox;
    @FXML
    private TextArea informationArea;
    @FXML
    private TextField emailTextField, passwordTextField, nameTextField, surnameTextField, phoneNumberTextField;
    @FXML
    private TableView<UserTableViewDTO> usersTable;
    @FXML
    private TableColumn<UserTableViewDTO, Number> idColumn;
    @FXML
    private TableColumn<UserTableViewDTO, String> emailColumn, passwordColumn, nameColumn, surnameColumn, phoneNumberColumn, isAdminColumn, generatingReportsColumn, grantingDiscountsColumn;
    @FXML
    private TableColumn<UserTableViewDTO, Void> deleteColumn;
    @FXML
    private TextField idSearchField, nameSearchField, surnameSearchField, phoneNumberSearchField, emailSearchField, passwordSearchField, isAdminSearchField, generatingReportsSearchField, grantingDiscountsSearchField;
    @Autowired
    private UserService userService;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        usersTable.setPlaceholder(new Label("Brak danych w tabeli"));
        informationArea.textProperty().addListener((ChangeListener<Object>) (observable, oldValue, newValue) -> informationArea.setScrollTop(Double.MAX_VALUE));
        setTable();
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
            UserCreateDTO userCreateDTO = UserCreateDTO.builder()
                    .name(name)
                    .surname(surname)
                    .email(email)
                    .password(password)
                    .phoneNumber(phoneNumber)
                    .isAdmin(isAdmin)
                    .generatingReports(generatingReports)
                    .grantingDiscounts(grantingDiscounts)
                    .build();
            userService.save(userCreateDTO);
            informationArea.appendText("\nDodano nowego użytkownika");
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
        //TODO: obsłużyć przycisk wyszukiwania
    }

    /**
     * Obsługuje przycisk generowania raportu z aktualnego widoku tabeli użytkowników.
     *
     * @param event
     */
    @FXML
    public void usersBtnReportClicked(MouseEvent event) {
        UserReport report = new UserReport(usersTable.getItems());
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
                    UserTableViewDTO userTableViewDTO = usersTable.getSelectionModel().getSelectedItem();
                    try {
                        userTableViewDTO.setName(newValue);
                        userService.update(userTableViewDTO);
                        informationArea.appendText("\nPomyślnie edytowano użytkownika o id " + userTableViewDTO.getId());
                    } catch (Exception e) {
                        informationArea.appendText("\nNie udało się edytować użytkownika o id " + userTableViewDTO.getId());
                    }
                }
                super.commitEdit(newValue);
            }
        });
        surnameColumn.setCellFactory(cell -> new TextFieldTableCell<>(converter) {
            @Override
            public void commitEdit(String newValue) {
                if (!Objects.equals(newValue, getItem())) {
                    UserTableViewDTO userTableViewDTO = usersTable.getSelectionModel().getSelectedItem();
                    try {
                        userTableViewDTO.setSurname(newValue);
                        userService.update(userTableViewDTO);
                        informationArea.appendText("\nPomyślnie edytowano użytkownika o id " + userTableViewDTO.getId());
                    } catch (Exception e) {
                        informationArea.appendText("\nNie udało się edytować użytkownika o id " + userTableViewDTO.getId());
                    }
                }
                super.commitEdit(newValue);
            }
        });
        phoneNumberColumn.setCellFactory(cell -> new TextFieldTableCell<>(converter) {
            @Override
            public void commitEdit(String newValue) {
                if (!Objects.equals(newValue, getItem())) {
                    UserTableViewDTO userTableViewDTO = usersTable.getSelectionModel().getSelectedItem();
                    try {
                        userTableViewDTO.setPhoneNumber(newValue);
                        userService.update(userTableViewDTO);
                        informationArea.appendText("\nPomyślnie edytowano użytkownika o id " + userTableViewDTO.getId());
                    } catch (Exception e) {
                        informationArea.appendText("\nNie udało się edytować użytkownika o id " + userTableViewDTO.getId());
                    }
                }
                super.commitEdit(newValue);
            }
        });
        emailColumn.setCellFactory(cell -> new TextFieldTableCell<>(converter) {
            @Override
            public void commitEdit(String newValue) {
                if (!Objects.equals(newValue, getItem())) {
                    UserTableViewDTO userTableViewDTO = usersTable.getSelectionModel().getSelectedItem();
                    try {
                        userTableViewDTO.setEmail(newValue);
                        userService.update(userTableViewDTO);
                        informationArea.appendText("\nPomyślnie edytowano użytkownika o id " + userTableViewDTO.getId());
                    } catch (Exception e) {
                        informationArea.appendText("\nNie udało się edytować użytkownika o id " + userTableViewDTO.getId());
                    }
                }
                super.commitEdit(newValue);
            }
        });
        passwordColumn.setCellFactory(cell -> new TextFieldTableCell<>(converter) {
            @Override
            public void commitEdit(String newValue) {
                if (!Objects.equals(newValue, getItem())) {
                    UserTableViewDTO userTableViewDTO = usersTable.getSelectionModel().getSelectedItem();
                    try {
                        userTableViewDTO.setPassword(newValue);
                        userService.update(userTableViewDTO);
                        informationArea.appendText("\nPomyślnie edytowano użytkownika o id " + userTableViewDTO.getId());
                    } catch (Exception e) {
                        informationArea.appendText("\nNie udało się edytować użytkownika o id " + userTableViewDTO.getId());
                    }
                }
                super.commitEdit(newValue);
            }
        });
        isAdminColumn.setCellFactory(ComboBoxTableCell.forTableColumn(new String[]{"Tak", "Nie"}));
        isAdminColumn.setOnEditCommit(t -> {
            UserTableViewDTO userTableViewDTO = usersTable.getSelectionModel().getSelectedItem();
            if (!Objects.equals(t.getNewValue(), t.getOldValue())) {
                try {
                    userTableViewDTO.setIsAdmin(BooleanConverter.convertToBoolean(t.getNewValue()));
                    userService.update(userTableViewDTO);
                    informationArea.appendText("\nPomyślnie edytowano użytkownika o id " + userTableViewDTO.getId());
                } catch (Exception e) {
                    informationArea.appendText("\nNie udało się edytować użytkownika o id " + userTableViewDTO.getId());
                }
            }
        });
        generatingReportsColumn.setCellFactory(ComboBoxTableCell.forTableColumn(new String[]{"Tak", "Nie"}));
        generatingReportsColumn.setOnEditCommit(t -> {
            UserTableViewDTO userTableViewDTO = usersTable.getSelectionModel().getSelectedItem();
            if (!Objects.equals(t.getNewValue(), t.getOldValue())) {
                try {
                    userTableViewDTO.setGeneratingReports(BooleanConverter.convertToBoolean(t.getNewValue()));
                    userService.update(userTableViewDTO);
                    informationArea.appendText("\nPomyślnie edytowano użytkownika o id " + userTableViewDTO.getId());
                } catch (Exception e) {
                    informationArea.appendText("\nNie udało się edytować użytkownika o id " + userTableViewDTO.getId());
                }
            }
        });
        grantingDiscountsColumn.setCellFactory(ComboBoxTableCell.forTableColumn(new String[]{"Tak", "Nie"}));
        grantingDiscountsColumn.setOnEditCommit(t -> {
            UserTableViewDTO userTableViewDTO = usersTable.getSelectionModel().getSelectedItem();
            if (!Objects.equals(t.getNewValue(), t.getOldValue())) {
                try {
                    userTableViewDTO.setGrantingDiscounts(BooleanConverter.convertToBoolean(t.getNewValue()));
                    userService.update(userTableViewDTO);
                    informationArea.appendText("\nPomyślnie edytowano użytkownika o id " + userTableViewDTO.getId());
                } catch (Exception e) {
                    informationArea.appendText("\nNie udało się edytować użytkownika o id " + userTableViewDTO.getId());
                }
            }
        });
        deleteColumn.setCellFactory(new Callback<>() {
            @Override
            public TableCell<UserTableViewDTO, Void> call(final TableColumn<UserTableViewDTO, Void> param) {
                final TableCell<UserTableViewDTO, Void> cell = new TableCell<>() {
                    final Image image = new Image(getClass().getResourceAsStream("/Images/deleteBtn.jpg"), 25, 25, false, false);
                    private final Button btn = new Button();

                    {
                        btn.setOnAction((ActionEvent event) -> {
                            UserTableViewDTO userTableViewDTO = getTableView().getItems().get(getIndex());
                            if (userService.delete(userTableViewDTO)) {
                                users.remove(userTableViewDTO);
                                informationArea.appendText("\nPomyślnie usunięto użytkownika o id " + userTableViewDTO.getId());
                            } else
                                informationArea.appendText("\nBłąd przy próbie usunięcia użytkownika o id " + usersTable.getId());
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
        usersTable.setItems(users);
    }
}
