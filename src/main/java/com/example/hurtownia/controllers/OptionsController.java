package com.example.hurtownia.controllers;

import com.example.hurtownia.Start;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.springframework.stereotype.Controller;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Klasa odpowiedzialna obsługę konfiguracji połączenia z bazą danych.
 */
@Controller
public class OptionsController implements Initializable {
    private final AtomicInteger taskExecution = new AtomicInteger(0);
    @FXML
    public Button changeDatabaseBtn;
    @FXML
    private AnchorPane ap;
    @FXML
    private TextField hostTextField;
    @FXML
    private TextField passwordTextField;
    @FXML
    private TextField portTextField;
    @FXML
    private TextField databaseNameTextField;
    @FXML
    private TextField userTextField;
    private Stage stage;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        String url = Start.applicationContext.getEnvironment().getProperty("spring.datasource.url");

        String cut1 = url.substring(url.indexOf("/") + 2);
        String hostName = cut1.substring(0, cut1.indexOf(":"));

        String cut2 = cut1.substring(cut1.indexOf(":") + 1);
        String port = cut2.substring(0, cut2.indexOf("/"));

        String cut3 = cut2.substring(cut2.indexOf("/") + 1);
        String databaseName;
        try {
            databaseName = cut3.substring(0, cut3.indexOf("?"));
        } catch (StringIndexOutOfBoundsException e) {
            databaseName = cut3;
        }

        hostTextField.setText(hostName);
        portTextField.setText(port);
        databaseNameTextField.setText(databaseName);
        userTextField.setText(Start.applicationContext.getEnvironment().getProperty("spring.datasource.username"));
        passwordTextField.setText(Start.applicationContext.getEnvironment().getProperty("spring.datasource.password"));
    }

    /**
     * Obsługuje przycisk zapisu nowych parametrów połączenia z baza danych.
     *
     * @param event
     */
    public void dbConnConfSaveClicked(MouseEvent event) throws ExecutionException, InterruptedException {
        stage = (Stage) ap.getScene().getWindow();

        Alert alert = new Alert(
                Alert.AlertType.INFORMATION,
                "Operation in progress"
        );
        alert.setTitle("Running Operation");
        alert.setHeaderText("Please wait... ");
        ProgressIndicator progressIndicator = new ProgressIndicator();
        alert.setGraphic(progressIndicator);

        Stage alertStage = (Stage) alert.getDialogPane().getScene().getWindow();
        alertStage.setAlwaysOnTop(true);

        Task<Void> task = new Task<>() {
            final int N_ITERATIONS = 100;

            {
                setOnSucceeded(a -> {
                    alert.setHeaderText("Done");
                    alert.setContentText("Operation completed");
                });
            }

            @Override
            protected Void call() {
                int i;
                for (i = 0; i < N_ITERATIONS; i++) {
                    updateProgress(i, N_ITERATIONS);

                    try {
                        Thread.sleep(50);
                    } catch (InterruptedException ignored) {
                    }
                }

                updateProgress(i, N_ITERATIONS);
                return null;
            }
        };

        progressIndicator.progressProperty().bind(task.progressProperty());

        Thread taskThread = new Thread(
                task,
                "task-thread-" + taskExecution.getAndIncrement()
        );
        taskThread.start();

        Thread restartThread = new Thread(() -> {
            try {
                Start.restart(hostTextField.getText(), portTextField.getText(), databaseNameTextField.getText(), userTextField.getText(), (passwordTextField.getText() != null && !passwordTextField.getText().trim().isEmpty()) ? "" : passwordTextField.getText());
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });

        ExecutorService service = Executors.newFixedThreadPool(1);
        Future<String> futureResult = (Future<String>) service.submit(restartThread);
        futureResult.get();

        alert.initOwner(stage);
        alert.showAndWait();
    }
}
