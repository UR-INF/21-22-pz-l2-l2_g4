package com.example.hurtownia.domain.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Zawiera metody dla tabeli 'uzytkownik'.
 */
@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    /**
     * Pobiera wszystkich użytkowników.
     *
     * @return lista wszystkich użytkowników
     */
    public List<User> getUsers() {return userRepository.findAll();}

    /**
     * Usuwa użytkownika.
     *
     * @param user usuwany użytkownik
     * @return true - jeśli pomyślnie usunięto;
     * false - jeśli wystąpiły błędy
     */
    public boolean deleteUser(User user) {
        try {
            userRepository.delete(user);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Dodaje nowego użytkownika.
     *
     * @param user nowy użytkownik
     * @return dodany użytkownik
     */
    public User saveUser(User user) {
        return userRepository.save(user);
    }

    /**
     * Aktualizuje użytkownika.
     *
     * @param user aktualizowany użytkownik
     */
    public void updateUser(User user) {userRepository.save(user);}
}
