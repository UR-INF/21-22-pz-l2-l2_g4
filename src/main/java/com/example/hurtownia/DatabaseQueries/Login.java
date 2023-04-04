package com.example.hurtownia.DatabaseQueries;

/**
 * Zawiera metody do obsługi logowania.
 */
public class Login {

    private boolean isAdmin;

    private String login;


    public boolean zaloguj(String login, String haslo) {
        isAdmin = false;
        if (login.equals("user") && haslo.equals("1234")) {
            this.login = login;
            return true;
        } else if (login.equals("admin") && haslo.equals("1234")) {
            this.login = login;
            isAdmin = true;
            return true;
        } else return false;
    }

    public void wyloguj() {
        isAdmin = false;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }
}
