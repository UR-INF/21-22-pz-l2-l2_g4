package com.example.hurtownia.authentication;

import org.springframework.stereotype.Service;

/**
 * Zawiera metody do obsługi logowania.
 */
@Service
public class LoginService {

    private boolean isAdmin;
    private String login;

    public boolean logIn(String login, String password) {
        isAdmin = false;
        if (login.equals("user") && password.equals("1234")) {
            this.login = login;
            return true;
        } else if (login.equals("admin") && password.equals("1234")) {
            this.login = login;
            isAdmin = true;
            return true;
        } else return false;
    }

    public void logOut() {
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
