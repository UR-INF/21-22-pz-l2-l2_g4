package com.example.hurtownia.domain.supplier;

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

class SupplierControllerTest extends ApplicationTest {
    ObservableList<SupplierDTO> items = FXCollections.observableArrayList();

    @Start
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Start.class.getResource("/FXML/supplierTab.fxml"));
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

        SupplierDTO supplierDTO = SupplierDTO.builder()
                .id(1L)
                .email("email@email.com")
                .country("Polska")
                .place("Rzeszów")
                .street("Rejtana")
                .name("Dostawca X")
                .nip("83728932")
                .build();
        items.add(supplierDTO);
    }

    @Test
    void initialize() {
        TableView<SupplierDTO> tableView = lookup(".table-view").queryTableView();
        tableView.setItems(items);
        FxAssert.verifyThat(tableView, node -> node.getItems().size() == 1);
    }
}