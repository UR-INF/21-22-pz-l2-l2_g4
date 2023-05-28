package com.example.hurtownia.domain.order;

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

class OrderControllerTest extends ApplicationTest {

    ObservableList<OrderDTO> items = FXCollections.observableArrayList();

    @Start
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Start.class.getResource("/FXML/orderTab.fxml"));
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

        OrderDTO orderDTO = OrderDTO.builder()
                .id(1L)
                .customerId(1L)
                .date("2023-02-02")
                .discount(0.2)
                .state("w przygotowaniu")
                .value(120.0)
                .build();
        items.add(orderDTO);
    }

    @Test
    void initialize() {
        TableView<OrderDTO> tableView = lookup(".table-view").queryTableView();
        tableView.setItems(items);
        FxAssert.verifyThat(tableView, node -> node.getItems().size() == 1);
    }
}