package com.example.hurtownia.controllers;

import com.example.hurtownia.domain.supplier.SupplierService;
import com.opencsv.exceptions.CsvValidationException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.opencsv.CSVReader;
import com.example.hurtownia.domain.supplier.request.SupplierCreateRequest;
import com.example.hurtownia.domain.supplier.SupplierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class ImportCSVController implements Initializable {
    @Autowired
    SupplierService supplierService;
    @FXML
    private AnchorPane ap;
    @FXML
    private TextField fileTextField;
    @FXML
    private ComboBox<String> tableComboBox;
    private Stage stage;
    private File selectedFile;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        tableComboBox.getItems().addAll("dostawcy", "produkty", "zamówienia", "elementy zamówienia", "klienci");
    }

    /**
     * Obsługuje przycisk wyboru pliku do importu.
     */


    public void handleChangeFileBtnClick(ActionEvent actionEvent) {
        FileChooser fileChooser = new FileChooser();
        selectedFile = fileChooser.showOpenDialog(null);
        if (selectedFile != null) fileTextField.setText(selectedFile.getAbsolutePath());
    }

    /**
     * Obsługuje przycisk importu.
     */

    public void handleImportBtnClick(ActionEvent actionEvent) {
        stage = (Stage) ap.getScene().getWindow();
        System.out.println(selectedFile != null);
        if (selectedFile != null) {
            switch (tableComboBox.getSelectionModel().getSelectedItem()) {
                case "dostawcy" -> importSuppliers();
//                case "produkty" -> importProducts();
//                case "zamówienia" -> importOrders();
//                case "elementy zamówienia" -> importOrdersItems();
//                case "klienci" -> importCustomers();
            }
            stage.close();
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("SUCCESS");
            alert.setHeaderText("SUCCESS");
            alert.setContentText("Pomyślnie zaimportowano plik o nazwie " + selectedFile.getName());
            alert.showAndWait();
        }
    }

    /**
     * Parsuje plik csv z danymi dostawców.
     */
    private void importSuppliers() {
        try {
            CSVReader reader = new CSVReader(new FileReader(selectedFile));
            String[] record = null;

            while ((record = reader.readNext()) != null) {
                SupplierCreateRequest supplierCreateRequest = SupplierCreateRequest.builder()
                        .name(record[1])
                        .email(record[2])
                        .country(record[3])
                        .place(record[4])
                        .street(record[5])
                        .nip(record[6]).build();

                supplierService.create(supplierCreateRequest);

                System.out.println(record[1] + " " +
                        record[2] + " " +
                        record[3] + " " +
                        record[4] + " " +
                        record[5] + " " +
                        record[6]);
            }
            reader.close();
        } catch (IOException | CsvValidationException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERROR");
            alert.setHeaderText("ERROR");
            alert.setContentText("Błąd.");
            alert.showAndWait();
        }
    }


}

