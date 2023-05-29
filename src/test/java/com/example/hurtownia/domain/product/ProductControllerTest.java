package com.example.hurtownia.domain.product;

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

class ProductControllerTest extends ApplicationTest {

    ObservableList<ProductDTO> items = FXCollections.observableArrayList();

    @Start
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Start.class.getResource("/FXML/productTab.fxml"));
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

        ProductDTO productDTO = ProductDTO.builder()
                .id(1L)
                .supplierId(1L)
                .name("Panel podłogowy")
                .country("Polska")
                .code("9dn2nd2d")
                .color("Grey")
                .maxNumber(10000)
                .price(82.6)
                .unitOfMeasurement("m^2")
                .number(100)
                .build();
        items.add(productDTO);
    }

    @Test
    void initialize() {
        TableView<ProductDTO> tableView = lookup(".table-view").queryTableView();
        tableView.setItems(items);
        FxAssert.verifyThat(tableView, node -> node.getItems().size() == 1);
    }
}