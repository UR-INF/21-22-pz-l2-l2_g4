package com.example.hurtownia.controllers;

import com.example.hurtownia.domain.customer.request.CustomerCreateRequest;
import com.example.hurtownia.domain.order.request.OrderCreateRequest;
import com.example.hurtownia.domain.orderitem.request.OrderItemCreateRequest;
import com.example.hurtownia.domain.product.request.ProductCreateRequest;
import com.example.hurtownia.domain.supplier.request.SupplierCreateRequest;
import com.example.hurtownia.domain.user.request.UserCreateRequest;
import com.opencsv.CSVReader;
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
import org.hibernate.ObjectNotFoundException;
import org.springframework.stereotype.Controller;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Kontroler okna importu danych z plików CSV.
 */
@Controller
public class ImportCSVController implements Initializable {
    public File selectedFile;
    @FXML
    private AnchorPane ap;
    @FXML
    private TextField fileTextField;
    @FXML
    private ComboBox<String> tableComboBox;
    private Stage stage;
    private MainController mainController;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        tableComboBox.getItems().addAll("dostawcy", "produkty", "użytkownicy", "elementy zamówienia", "klienci", "zamówienia");
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
        if (selectedFile != null) {
            try {
                switch (tableComboBox.getSelectionModel().getSelectedItem()) {
                    case "dostawcy" -> importSuppliers();
                    case "produkty" -> importProducts();
                    case "użytkownicy" -> importUsers();
                    case "elementy zamówienia" -> importOrderItems();
                    case "klienci" -> importCustomers();
                    case "zamówienia" -> importOrders();
                }

                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("SUCCESS");
                alert.setHeaderText("SUCCESS");
                alert.setContentText("Pomyślnie zaimportowano plik o nazwie " + selectedFile.getName());
                alert.showAndWait();

                stage.close();
            } catch (IOException
                     | CsvValidationException
                     | IndexOutOfBoundsException
                     | UnsupportedOperationException
                     | ObjectNotFoundException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("ERROR");
                alert.setHeaderText("ERROR");
                alert.setContentText(e.getMessage());
                alert.showAndWait();
            }
        }
    }

    /**
     * Parsuje plik csv z danymi dostawców.
     */
    public void importSuppliers() throws CsvValidationException, IOException {
        try {
            CSVReader reader = new CSVReader(new FileReader(selectedFile));
            String[] record = null;
            reader.readNext();

            while ((record = reader.readNext()) != null) {
                SupplierCreateRequest supplierCreateRequest = SupplierCreateRequest.builder()
                        .name(record[1])
                        .email(record[2])
                        .country(record[3])
                        .place(record[4])
                        .street(record[5])
                        .nip(record[6]).build();

                mainController.supplierService.create(supplierCreateRequest);
            }
            reader.close();
        } catch (CsvValidationException e) {
            throw new CsvValidationException("Błąd podczas parsowania pliku z danymi o dostawcach.");
        } catch (IOException e) {
            throw new IOException("Błąd podczas odczytu pliku.");
        } catch (IndexOutOfBoundsException e) {
            throw new IndexOutOfBoundsException("Zła struktura pliku.");
        }
    }

    /**
     * Parsuje plik csv z danymi klientów.
     */
    public void importCustomers() throws CsvValidationException, IOException {
        try {
            CSVReader reader = new CSVReader(new FileReader(selectedFile));
            String[] record = null;
            reader.readNext();

            while ((record = reader.readNext()) != null) {
                CustomerCreateRequest customerCreateRequest = CustomerCreateRequest.builder()
                        .email(record[1])
                        .name(record[2])
                        .surname(record[3])
                        .zipCode(record[4])
                        .place(record[5])
                        .apartmentNumber(Integer.parseInt(record[6]))
                        .buildingNumber(Integer.parseInt(record[7]))
                        .phoneNumber(record[8])
                        .street(record[9])
                        .pesel(record[10]).build();

                mainController.customerService.create(customerCreateRequest);
            }
            reader.close();
        } catch (CsvValidationException e) {
            throw new CsvValidationException("Błąd podczas parsowania pliku z danymi o klientach.");
        } catch (IOException e) {
            throw new IOException("Błąd podczas odczytu pliku.");
        } catch (IndexOutOfBoundsException e) {
            throw new IndexOutOfBoundsException("Zła struktura pliku.");
        }
    }

    /**
     * Parsuje plik csv z danymi produktów.
     */
    public void importProducts() throws CsvValidationException, IOException {
        try {
            if (mainController.supplierService.findAll().isEmpty()) {
                throw new UnsupportedOperationException("Zaimportuj dane o dostawcach przed zaimportowaniem danych o produktach.");
            }

            CSVReader reader = new CSVReader(new FileReader(selectedFile));
            String[] record = null;
            reader.readNext();

            while ((record = reader.readNext()) != null) {
                ProductCreateRequest productCreateRequest = ProductCreateRequest.builder()
                        .code(record[1])
                        .color(record[2])
                        .number(Integer.parseInt(record[3]))
                        .maxNumber(Integer.parseInt(record[4]))
                        .price(Double.parseDouble(record[5]))
                        .unitOfMeasurement(record[6])
                        .country(record[7])
                        .name(record[8])
                        .supplierId(Long.parseLong(record[9])).build();

                mainController.productService.create(productCreateRequest);
            }
            reader.close();
        } catch (CsvValidationException e) {
            throw new CsvValidationException("Błąd podczas parsowania pliku z danymi o produktach.");
        } catch (IOException e) {
            throw new IOException("Błąd podczas odczytu pliku.");
        } catch (IndexOutOfBoundsException e) {
            throw new IndexOutOfBoundsException("Zła struktura pliku.");
        } catch (ObjectNotFoundException e) {
            throw new ObjectNotFoundException(e.getIdentifier().toString(), "Nie znaleziono dostawcy o danym id.");
        }
    }

    /**
     * Parsuje plik csv z danymi użytkowników.
     */
    public void importUsers() throws CsvValidationException, IOException, IndexOutOfBoundsException {
        try {
            CSVReader reader = new CSVReader(new FileReader(selectedFile));
            String[] record = null;
            reader.readNext();

            while ((record = reader.readNext()) != null) {
                UserCreateRequest userCreateRequest = UserCreateRequest.builder()
                        .name(record[1])
                        .surname(record[2])
                        .email(record[3])
                        .password(record[4])
                        .isAdmin(Boolean.parseBoolean(record[5]))
                        .phoneNumber(record[6])
                        .generatingReports(Boolean.parseBoolean(record[7]))
                        .grantingDiscounts(Boolean.parseBoolean(record[8])).build();

                mainController.userService.create(userCreateRequest);
            }
            reader.close();
        } catch (UnsupportedOperationException e) {
            throw new UnsupportedOperationException("Operacja niedozwolona.");
        } catch (CsvValidationException e) {
            throw new CsvValidationException("Błąd podczas parsowania pliku z danymi o klientach.");
        } catch (IOException e) {
            throw new IOException("Błąd podczas odczytu pliku.");
        } catch (IndexOutOfBoundsException e) {
            throw new IndexOutOfBoundsException("Zła struktura pliku.");
        }
    }

    /**
     * Parsuje plik csv z danymi elementów zamówień.
     */
    public void importOrderItems() throws CsvValidationException, IOException {
        try {
            if (mainController.orderService.findAll().isEmpty()) {
                throw new UnsupportedOperationException("Zaimportuj dane o zamówieniach przed zaimportowaniem danych o elementach zamówień.");
            }
            if (mainController.productService.findAll().isEmpty()) {
                throw new UnsupportedOperationException("Zaimportuj dane o produktach przed zaimportowaniem danych o elementach zamówień.");
            }

            CSVReader reader = new CSVReader(new FileReader(selectedFile));
            String[] record = null;
            reader.readNext();

            while ((record = reader.readNext()) != null) {
                OrderItemCreateRequest orderItemCreateRequest = OrderItemCreateRequest.builder()
                        .amount(Integer.parseInt(record[1]))
                        .productId(Long.parseLong(record[2]))
                        .orderId(Long.parseLong(record[3])).build();

                mainController.orderItemService.create(orderItemCreateRequest);
            }
            reader.close();
        } catch (CsvValidationException e) {
            throw new CsvValidationException("Błąd podczas parsowania pliku z danymi o klientach.");
        } catch (IOException e) {
            throw new IOException("Błąd podczas odczytu pliku.");
        } catch (IndexOutOfBoundsException e) {
            throw new IndexOutOfBoundsException("Zła struktura pliku.");
        } catch (ObjectNotFoundException e) {
            throw new ObjectNotFoundException(e.getIdentifier().toString(), "Nie znaleziono obiektu o danym id.");
        }
    }

    /**
     * Parsuje plik csv z danymi zamówień.
     */
    public void importOrders() throws CsvValidationException, IOException {
        try {
            if (mainController.customerService.findAll().isEmpty()) {
                throw new UnsupportedOperationException("Zaimportuj dane o klientach przed zaimportowaniem danych o zamówieniach.");
            }

            CSVReader reader = new CSVReader(new FileReader(selectedFile));
            String[] record = null;
            reader.readNext();

            while ((record = reader.readNext()) != null) {
                OrderCreateRequest orderCreateRequest = OrderCreateRequest.builder()
                        .date(record[1])
                        .customerId(Long.parseLong(record[2]))
                        .discount(Double.parseDouble(record[3]))
                        .state(record[4]).build();

                mainController.orderService.create(orderCreateRequest);
            }
            reader.close();
        } catch (CsvValidationException e) {
            throw new CsvValidationException("Błąd podczas parsowania pliku z danymi o klientach.");
        } catch (IOException e) {
            throw new IOException("Błąd podczas odczytu pliku.");
        } catch (IndexOutOfBoundsException e) {
            throw new IndexOutOfBoundsException("Zła struktura pliku.");
        } catch (ObjectNotFoundException e) {
            throw new ObjectNotFoundException(e.getIdentifier().toString(), "Nie znaleziono klienta o danym id.");
        }
    }

    public void setController(MainController mainController) {
        this.mainController = mainController;
    }

    public MainController getMainController() {
        return mainController;
    }
}

