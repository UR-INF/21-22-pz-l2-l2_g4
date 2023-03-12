package Controllers;

import DatabaseAccess.DbAccess;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import java.net.URL;
import java.util.ResourceBundle;

public class MainController implements Initializable {

    @FXML
    private Label labelBaza;

    private DbAccess dbAccess;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        dbAccess = new DbAccess();
        labelBaza.setText(dbAccess.getDatabaseName());
    }
}
