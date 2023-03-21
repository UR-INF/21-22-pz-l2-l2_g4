package DatabaseQueries;

import Entities.Uzytkownik;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;

/**
 * Zawiera metody do obsługi logowania.
 */
public class Login {

    // tymczasowe pole do rozpoznania admina po logowaniu
    public boolean isAdmin;

    //
    public boolean zaloguj(String login, String haslo){
        isAdmin=false;
        if(login.equals("user")&&haslo.equals("1234"))
        {
            return true;
        }
        else if(login.equals("admin")&&haslo.equals("1234"))
        {
            isAdmin = true;
            return true;
        }
        else
            return false;
    }
    public void wyloguj(){
        isAdmin=false;
    }


}
