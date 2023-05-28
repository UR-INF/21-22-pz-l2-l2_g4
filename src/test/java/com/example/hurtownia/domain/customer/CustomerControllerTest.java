package com.example.hurtownia.domain.customer;

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

class CustomerControllerTest extends ApplicationTest {

    ObservableList<CustomerDTO> items = FXCollections.observableArrayList();

    @Start
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Start.class.getResource("/FXML/customerTab.fxml"));
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

        CustomerDTO customerDTO = CustomerDTO.builder()
                .id(1L)
                .name("Customer")
                .surname("Surname")
                .pesel("21973812L")
                .email("email@com.pl")
                .place("Place")
                .zipCode("30-300")
                .phoneNumber("32873782")
                .apartmentNumber(1)
                .buildingNumber(1)
                .street("Street")
                .build();
        items.add(customerDTO);
    }

    @Test
    void initialize() {
        TableView<CustomerDTO> tableView = lookup(".table-view").queryTableView();
        tableView.setItems(items);
        FxAssert.verifyThat(tableView, node -> node.getItems().size() == 1);
    }

    @Test
    void disableGeneratingReports() {
    }

    @Test
    void enableGeneratingReports() {
    }

    @Test
    void customersBtnShowClicked() {
    }

    @Test
    void customersBtnAddClicked() {
    }

    @Test
    void customersBtnSearchClicked() {
    }

    @Test
    void customersBtnReportClicked() {
    }
}