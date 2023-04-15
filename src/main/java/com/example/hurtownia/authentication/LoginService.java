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
    UserService userService;
    private User currentUser;

    /**
     * Procedura logowania dla wersji użytkowej aplikacji.
     * Ustawiany jest aktualny użytkownik
     * Zwracane jest true w przypadku powodzenia, natomiast false w przyapdku błędnych danych logowania.
     * @param login
     * @param password
     * @return true/false
     */
    public boolean logIn(String login, String password) {
        currentUser = userService.login(login,password);
        return currentUser != null;
    }

    /**
     * Obsługa procedury wylogowania.
     * Bieżący użytkownik ustawiany jest jako null
     */
    public void logOut() {
        currentUser=null;
    }

    /**
     * Zwracane są uprawnienia admina bieżącego użytkownika
     * @return isAdmin
     */
    public User getCurrentUser()
    {
        return currentUser;
    }

    /**
     * Zwraca imię i nazwisko bieżącego użytkownika
     * @return Imie i nazwisko
     */
    public String getLogin() {
        return currentUser.getName()+" "+currentUser.getSurname();
    }
}
