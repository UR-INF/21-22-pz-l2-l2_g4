package Main;

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
        Parent root = FXMLLoader.load(getClass().getResource("/FXML/App.fxml"));
        Scene scene = new Scene(root);
        stage.setTitle("Hurtownia");
        stage.setScene(scene);
        stage.show();
        
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