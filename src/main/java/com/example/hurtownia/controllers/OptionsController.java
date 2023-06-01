package com.example.hurtownia.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class OptionsController implements Initializable {
    public Button saveOptionsBtn;

    public AnchorPane ap2;
    public Button darkModeButton;
    private Scene scene;

    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }

    private MainController mainController;

    private boolean darkModeEnabled = false;

    public void setScene(Scene scene) {
        this.scene = scene;
    }

    public void handleSaveBtnClick(ActionEvent actionEvent) {
        Stage currentStage = (Stage) saveOptionsBtn.getScene().getWindow();
        currentStage.close();
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        
    }


        /**
         * Odpowiedzialna za tryb ciemny poprzez guzik
         */
        @FXML
        private void changeCSS(ActionEvent event) {
            darkModeEnabled = !darkModeEnabled;

            if (darkModeEnabled) {

                ap2.getStylesheets().remove(getClass().getResource("/CSS/mainCSS.css").toExternalForm());
                ap2.getStylesheets().add(getClass().getResource("/CSS/dark-theme.css").toExternalForm());

//                ap1.getStylesheets().remove(getClass().getResource("/CSS/mainCSS.css").toExternalForm());
//                ap1.getStylesheets().add(getClass().getResource("/CSS/dark-theme.css").toExternalForm());

                mainController.customerTabContentController.informationArea.getScene().getStylesheets().remove(getClass().getResource("/CSS/mainCSS.css").toExternalForm());
                mainController.customerTabContentController.informationArea.getScene().getStylesheets().add(getClass().getResource("/CSS/dark-theme.css").toExternalForm());

                darkModeButton.setText("Wyłącz");

                mainController.loginPane.setVisible(false);
            } else {

                ap2.getStylesheets().remove(getClass().getResource("/CSS/dark-theme.css").toExternalForm());
                ap2.getStylesheets().add(getClass().getResource("/CSS/mainCSS.css").toExternalForm());
                mainController.customerTabContentController.informationArea.getScene().getStylesheets().remove(getClass().getResource("/CSS/dark-theme.css").toExternalForm());

                mainController.customerTabContentController.informationArea.getScene().getStylesheets().add(getClass().getResource("/CSS/mainCSS.css").toExternalForm());

                darkModeButton.setText("Włącz");

                mainController.loginPane.setVisible(false);
            }
        }

}
