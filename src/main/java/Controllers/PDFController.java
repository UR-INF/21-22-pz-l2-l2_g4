package Controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.DirectoryChooser;

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
public class PDFController implements Initializable {

    @FXML
    private Button saveBtn;
    @FXML
    private TextField fileNameTextField, directoryTextField, titleTextField;
    @FXML
    private ComboBox<String> fileExtensionComboBox;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        fileExtensionComboBox.getItems().addAll(".pdf");
        saveBtn.disableProperty().bind(fileNameTextField.textProperty().isEmpty().or(fileExtensionComboBox.getSelectionModel().selectedItemProperty().isNull().or(directoryTextField.textProperty().isEmpty())));
    }

    /**
     * Obsługuje przycisk wyboru lokalizacji.
     */
    @FXML
    public void handleChangeDirectoryBtnClick() {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        File selectedDirectory = directoryChooser.showDialog(null);
        if (selectedDirectory != null) directoryTextField.setText(selectedDirectory.getAbsolutePath());
    }

    /**
     * Obsługuje przycisk zapisu.
     */
    @FXML
    public void handleSaveBtnClick() {
        Path file = Paths.get(directoryTextField.getText(), fileNameTextField.getText().trim() + fileExtensionComboBox.getSelectionModel().getSelectedItem());
        if (!Files.exists(file)) {
            try {
                Files.createFile(file.toAbsolutePath());
                //...
            } catch (IOException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("ERROR");
                alert.setHeaderText("ERROR");
                alert.setContentText("Taki plik już istnieje w podanej lokalizacji.");
                alert.showAndWait();
            }
        }
    }
}