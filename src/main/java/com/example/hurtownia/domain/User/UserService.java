package com.example.hurtownia.domain.user;

import org.hibernate.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Zawiera metody dla tabeli 'uzytkownik'.
 */
@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserMapper mapper;

    /**
     * Pobiera wszystkich użytkowników.
     *
     * @return lista wszystkich użytkowników
     */
    public List<UserTableViewDTO> findAll() {
        return userRepository.findAll().stream()
                .map(user -> mapper.toDTO(user))
                .collect(Collectors.toList());
    }

    /**
     * Pobiera użytkownika o podanym id.
     *
     * @param id identyfikator użytkownika
     * @return użytkownik
     */
    public User findById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new ObjectNotFoundException(id, "Nie znaleziono użytkownika"));
    }

    /**
     * Usuwa użytkownika.
     *
     * @param userTableViewDTO usuwany użytkownik
     * @return true - jeśli pomyślnie usunięto;
     * false - jeśli wystąpiły błędy
     */
    public boolean delete(UserTableViewDTO userTableViewDTO) {
        try {
            userRepository.delete(mapper.toEntity(userTableViewDTO));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Dodaje nowego użytkownika.
     *
     * @param userCreateDTO nowy użytkownik
     */
    public void save(UserCreateDTO userCreateDTO) {
        userRepository.save(mapper.toEntity(userCreateDTO));
    }

    /**
     * Aktualizuje użytkownika.
     *
     * @param userTableViewDTO aktualizowany użytkownik
     */
    public void update(UserTableViewDTO userTableViewDTO) {userRepository.save(mapper.toEntity(userTableViewDTO));}
}
