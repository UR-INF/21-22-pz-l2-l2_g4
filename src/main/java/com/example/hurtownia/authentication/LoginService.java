package com.example.hurtownia.authentication;

import com.example.hurtownia.domain.user.User;
import com.example.hurtownia.domain.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Zawiera metody do obsługi logowania.
 */
@Service
public class LoginService {

    @Autowired
    private UserService userService;
    private User currentUser;

    /**
     * Procedura logowania dla wersji użytkowej aplikacji.
     * Ustawiany jest aktualny użytkownik
     * Zwracane jest true w przypadku powodzenia, natomiast false w przyapdku błędnych danych logowania.
     *
     * @param login
     * @param password
     * @return true/false
     */
    public boolean logIn(String login, String password) throws Exception {
        currentUser = userService.login(login, password);
        return currentUser != null;
    }

    /**
     * Obsługa procedury wylogowania.
     * Bieżący użytkownik ustawiany jest jako null
     */
    public void logOut() {
        currentUser = null;
    }

    /**
     * Zwracany jest bieżący użytkownik
     *
     * @return isAdmin
     */
    public User getCurrentUser() {
        return currentUser;
    }

    /**
     * Ustawia bieżacego użytkownika.
     */
    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
    }

    /**
     * Zwraca imię i nazwisko bieżącego użytkownika
     *
     * @return Imie i nazwisko
     */
    public String getCurrentUserName() {
        return currentUser.getName() + " " + currentUser.getSurname();
    }
}
