package com.example.hurtownia.domain.user;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.testfx.api.FxAssert;
import org.testfx.framework.junit5.ApplicationTest;
import org.testfx.framework.junit5.Start;

import java.io.IOException;

class UserControllerTest extends ApplicationTest {

    ObservableList<UserDTO> items = FXCollections.observableArrayList();

    @Start
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Start.class.getResource("/FXML/userTab.fxml"));
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

        UserDTO userDTO = UserDTO.builder()
                .id(1L)
                .name("name")
                .surname("surname")
                .email("admin")
                .password("admin")
                .phoneNumber("phoneNumber")
                .isAdmin(Boolean.TRUE)
                .generatingReports(Boolean.TRUE)
                .grantingDiscounts(Boolean.TRUE)
                .build();
        items.add(userDTO);
    }

    @Test
    void initialize() {
        TableView<UserDTO> tableView = lookup(".table-view").queryTableView();
        tableView.setItems(items);
        FxAssert.verifyThat(tableView, node -> node.getItems().size() == 1);
    }

    @Test
    void usersBtnShowClicked() {
    }

    @Test
    void usersBtnAddClicked() {
    }

    @Test
    void usersBtnSearchClicked() {
    }

    @Test
    void usersBtnReportClicked() {
    }
}