package DatabaseQueries;

/**
 * Zawiera metody do obsługi logowania.
 */
public class Login {

    // tymczasowe pole do rozpoznania admina po logowaniu
    private boolean isAdmin;

    public boolean zaloguj(String login, String haslo){
        isAdmin=false;
        if(login.equals("user")&&haslo.equals("1234")) {
            return true;
        }
        else if(login.equals("admin")&&haslo.equals("1234")) {
            isAdmin = true;
            return true;
        }
        else
            return false;
    }
    public void wyloguj(){
        isAdmin = false;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }
}
