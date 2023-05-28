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
     * Obsługa procedury logowania.
     *
     * @param login login użytkownika
     * @param password hasło użytkownika
     * @return true - jeśli logowanie przebiegło pomyślnie;
     * false - jeśli wystąpiły błędy
     */
    public boolean logIn(String login, String password) throws Exception {
        currentUser = userService.login(login, password);
        return currentUser != null;
    }

    /**
     * Obsługa procedury wylogowania.
     */
    public void logOut() {
        currentUser = null;
    }

    /**
     * Zwraca aktualnie zalogowanego użytkownika.
     *
     * @return aktualnie zalogowany użytkownik
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
     * Zwraca imię i nazwisko aktualnie zalogowanego użytkownika.
     *
     * @return imię i nazwisko aktualnie zalogowanego użytkownika
     */
    public String getCurrentUserName() {
        return currentUser.getName() + " " + currentUser.getSurname();
    }
}
