package com.example.hurtownia;

import com.example.hurtownia.controllers.MainController;
import com.example.hurtownia.domain.user.User;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.Properties;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Klasa odpowiedzialna za załadowanie widoku aplikacji.
 */
public class Start extends Application {
    public static ConfigurableApplicationContext applicationContext;
    private static Stage currentStage;
    private static String databaseNameDefault = "hurtownia";
    private static String hostDefault = "localhost";
    private static String portDefault = "3306";
    private static String userNameDefault = "root";
    private static String passwordDefault = "";
    private static String databaseName = "hurtownia";
    private static String host = "localhost";
    private static String port = "3306";
    private static String username = "root";
    private static String password = "";
    private boolean showLoginPane = true;
    public static User user;
    public static String rawUserPassword;

    /**
     * Restartuje połączenie z bazą danych uwzględniając nowe parametry.
     *
     * @param newHost
     * @param newPort
     * @param newDatabaseName
     * @param newUserName
     * @param newPassword
     * @throws InterruptedException
     */
    public static void restart(String newHost, String newPort, String newDatabaseName, String newUserName, String newPassword) throws InterruptedException {
        String url = applicationContext.getEnvironment().getProperty("spring.datasource.url");
        userNameDefault = applicationContext.getEnvironment().getProperty("spring.datasource.username");
        passwordDefault = applicationContext.getEnvironment().getProperty("spring.datasource.password");

        String cut1 = url.substring(url.indexOf("/") + 2);
        hostDefault = cut1.substring(0, cut1.indexOf(":"));

        String cut2 = cut1.substring(cut1.indexOf(":") + 1);
        portDefault = cut2.substring(0, cut2.indexOf("/"));

        String cut3 = cut2.substring(cut2.indexOf("/") + 1);
        try {
            databaseNameDefault = cut3.substring(0, cut3.indexOf("?"));
        } catch (StringIndexOutOfBoundsException e) {
            databaseNameDefault = cut3;
        }

        try {
            Thread.sleep(3000);
        } catch (InterruptedException ex) {
            throw new RuntimeException(ex);
        }

        Platform.runLater(() -> {
            try {
                new Start().start(newHost, newPort, newDatabaseName, newUserName, newPassword);
            } catch (Exception e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                Stage alertStage = (Stage) alert.getDialogPane().getScene().getWindow();
                alertStage.setAlwaysOnTop(true);
                alert.setTitle("ERROR");
                alert.setHeaderText("ERROR");
                alert.setContentText("Niepoprawna konfiguracja. Powrót do obecnego połączenia.");
                alert.showAndWait();

                try {
                    new Start().start(hostDefault, portDefault, databaseNameDefault, userNameDefault, passwordDefault);
                } catch (Exception ex) {
                    alert = new Alert(Alert.AlertType.ERROR);
                    alertStage = (Stage) alert.getDialogPane().getScene().getWindow();
                    alertStage.setAlwaysOnTop(true);
                    alert.setTitle("ERROR");
                    alert.setHeaderText("ERROR");
                    alert.setContentText("Błąd połączenia z domyślną bazą danych.");
                    alert.showAndWait();
                }
            }
        });
    }

    /**
     * Uruchamia nowy stage po zmianie domyślnej bazy danych.
     *
     * @param host
     * @param port
     * @param databaseName
     * @param username
     * @param password
     * @throws Exception
     */
    private void start(String host, String port, String databaseName, String username, String password) throws Exception {
        this.databaseName = databaseName;
        this.host = host;
        this.port = port;
        this.username = username;
        this.password = password;
        showLoginPane = false;

        currentStage.close();
        init();
        start(new Stage());
    }

    @Override
    public void init() {
        SpringApplication application = new SpringApplication(HurtowniaApplication.class);

        Properties properties = new Properties();
        properties.put("spring.datasource.url", "jdbc:mysql://" + host + ":" + port + "/" + databaseName + "?createDatabaseIfNotExist=true");
        properties.put("spring.datasource.username", username);
        properties.put("spring.datasource.password", password);
        properties.put("spring.datasource.driver-class-name", "com.mysql.cj.jdbc.Driver");
        properties.put("spring.jpa.database-platform", "org.hibernate.dialect.MySQL5InnoDBDialect");
        properties.put("spring.jpa.hibernate.ddl-auto", "update");
        properties.put("spring.main.allow-circular-references", "true");
        properties.put("spring.sql.init.continue-on-error", "true");
        properties.put("spring.datasource.hikari.minimum-idle", 0);
        properties.put("spring.datasource.hikari.initialization-fail-timeout", -1);
        properties.put("spring.datasource.hikari.max-lifetime", 1000);
        properties.put("spring.datasource.hikari.connection-timeout", 1000);
        properties.put("spring.datasource.hikari.validation-timeout", 1000);
        properties.put("spring.datasource.hikari.maximum-pool-size", 1000);
        application.setDefaultProperties(properties);

        applicationContext = application.run();
    }

    /**
     * Ładuje główny widok aplikacji.
     *
     * @param stage
     * @throws Exception
     */
    @Override
    public void start(Stage stage) throws Exception {
        currentStage = stage;

        applicationContext.publishEvent(new StageReadyEvent(stage));
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/FXML/hurtownia-view.fxml"));
        fxmlLoader.setControllerFactory(applicationContext::getBean);
        Parent root = fxmlLoader.load();
        MainController currentMainController = (MainController) fxmlLoader.getController();
        Scene scene = new Scene(root);
        stage.setTitle("Hurtownia");
        stage.setScene(scene);
        stage.show();

        if (!showLoginPane) {
            currentMainController.loginPane.setVisible(false);
            currentMainController.loginService.setCurrentUser(user);
            currentMainController.loginService.logIn(user.getEmail(), rawUserPassword);
            currentMainController.userNameLabel.setText(user.getName() + " " + user.getSurname());
            currentMainController.checkPermissions(user);
        }

        stage.setOnCloseRequest(t -> {
            Platform.exit();
            System.exit(0);
        });
    }

    /**
     * Zamyka aplikację.
     */
    @Override
    public void stop() {
        applicationContext.close();
        Platform.exit();
    }

    static class StageReadyEvent extends ApplicationEvent {
        public StageReadyEvent(Stage stage) {
            super(stage);
        }
    }
}