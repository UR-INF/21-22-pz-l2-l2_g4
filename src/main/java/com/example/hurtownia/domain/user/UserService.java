package com.example.hurtownia.domain.user;

import org.hibernate.ObjectNotFoundException;
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
    public List<User> findAll() {return userRepository.findAll();}

    /**
     * Pobiera użytkownika o podanym id.
     *
     * @param id identyfikator użytkownika
     * @return użytkownik
     */
    public User findById(Long id) {return userRepository.findById(id).orElseThrow(() -> new ObjectNotFoundException(id, "Nie znaleziono użytkownika"));}

    /**
     * Usuwa użytkownika.
     *
     * @param user usuwany użytkownik
     * @return true - jeśli pomyślnie usunięto;
     * false - jeśli wystąpiły błędy
     */
    public boolean delete(User user) {
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
    public User save(User user) {
        return userRepository.save(user);
    }

    /**
     * Aktualizuje użytkownika.
     *
     * @param newUser aktualizowany użytkownik
     */
    public void update(User newUser) {
        User user = findById(newUser.getId());
        user.setName(newUser.getName());
        user.setSurname(newUser.getSurname());
        user.setEmail(newUser.getEmail());
        user.setPassword(newUser.getPassword());
        user.setPhoneNumber(newUser.getPhoneNumber());
        user.setAdmin(newUser.isAdmin());
        user.setGeneratingReports(newUser.isGeneratingReports());
        user.setGrantingDiscounts(newUser.isGrantingDiscounts());

        userRepository.save(user);
    }
}
