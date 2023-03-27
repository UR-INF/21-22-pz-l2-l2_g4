package Main;

import Controllers.MainController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import static DatabaseAccess.DbAccess.CONNECTION;

/**
 * Klasa odpowiedzialna za uruchomienie aplikacji.
 */
public class Start extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/FXML/hurtownia-view.fxml"));
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root);
        stage.setTitle("Hurtownia");
        stage.setScene(scene);
        stage.show();

        MainController myController = fxmlLoader.getController();
        myController.setScene(scene);
        
        if (CONNECTION==false) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("ERROR");
            alert.setHeaderText("ERROR");
            alert.setContentText("Błąd połączenia z bazą!");
            alert.showAndWait();
        }
    }

    public static void main(String[] args) {
        launch();
    }

}