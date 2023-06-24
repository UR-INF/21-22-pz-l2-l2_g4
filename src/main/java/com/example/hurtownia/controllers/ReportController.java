package com.example.hurtownia.controllers;

import com.example.hurtownia.domain.AbstractReport;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import org.springframework.stereotype.Controller;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ResourceBundle;

/**
 * Kontroler okna do zapisu raportu PDF.
 */
@Controller
public class ReportController implements Initializable {
    @FXML
    private AnchorPane ap;
    @FXML
    private Button saveBtn;
    @FXML
    private TextField fileNameTextField, directoryTextField, titleTextField;
    private AbstractReport report;
    private Stage stage;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        saveBtn.disableProperty().bind(fileNameTextField.textProperty().isEmpty().or(directoryTextField.textProperty().isEmpty()));
    }

    /**
     * Obsługuje przycisk wyboru lokalizacji zapisu dokumentu.
     */
    @FXML
    public void handleChangeDirectoryBtnClick() {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        File selectedDirectory = directoryChooser.showDialog(null);
        if (selectedDirectory != null) directoryTextField.setText(selectedDirectory.getAbsolutePath());
    }

    /**
     * Obsługuje przycisk zapisu dokumentu.
     */
    @FXML
    public void handleSaveBtnClick() {
        stage = (Stage) ap.getScene().getWindow();
        Path file = Paths.get(directoryTextField.getText(), fileNameTextField.getText().trim() + ".pdf");
        if (!Files.exists(file)) {
            try {
                report.generateReport(file.toAbsolutePath().toString(), titleTextField.getText());
                stage.close();
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("SUCCESS");
                alert.setHeaderText("SUCCESS");
                alert.setContentText("Pomyślnie zapisano plik o nazwie " + fileNameTextField.getText().trim() + " w lokalizacji " + file.toAbsolutePath());
                alert.showAndWait();
            } catch (IOException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("ERROR");
                alert.setHeaderText("ERROR");
                alert.setContentText("Błąd zapisu.");
                alert.showAndWait();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERROR");
            alert.setHeaderText("ERROR");
            alert.setContentText("Taki plik już istnieje w podanej lokalizacji.");
            alert.showAndWait();
        }
    }

    public void setReport(AbstractReport report) {
        this.report = report;
    }
}