package com.example.hurtownia.domain.orderitem;

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

class OrderItemControllerTest extends ApplicationTest {

    ObservableList<OrderItemDTO> items = FXCollections.observableArrayList();

    @Start
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Start.class.getResource("/FXML/orderItemTab.fxml"));
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

        OrderItemDTO orderItemDTO = OrderItemDTO.builder()
                .id(1L)
                .orderId(1L)
                .productId(1L)
                .amount(100)
                .pricePerUnit(1.00)
                .itemPrice(1.00 * 100)
                .build();
        items.add(orderItemDTO);
    }

    @Test
    void initialize() {
        TableView<OrderItemDTO> tableView = lookup(".table-view").queryTableView();
        tableView.setItems(items);
        FxAssert.verifyThat(tableView, node -> node.getItems().size() == 1);
    }
}