package com.example.hurtownia.controllers;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;
import org.testfx.framework.junit5.Start;

import java.io.IOException;

class ReportControllerTest extends ApplicationTest {

    @Start
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Start.class.getResource("/FXML/report-save-view.fxml"));
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @Test
    void initialize() {
    }

    @Test
    void handleChangeDirectoryBtnClick() {
    }

    @Test
    void handleSaveBtnClick() {
    }

    @Test
    void setReport() {
    }
}