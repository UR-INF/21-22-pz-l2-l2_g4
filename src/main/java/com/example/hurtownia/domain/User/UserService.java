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
    public List<UserDTO> findAll() {
        return userRepository.findAll().stream()
                .map(user -> mapper.mapToDto(user))
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
     * @param id identyfikator usuwanego użytkownika
     * @return true - jeśli pomyślnie usunięto;
     * false - jeśli wystąpiły błędy
     */
    public boolean delete(Long id) {
        try {
            userRepository.delete(findById(id));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Dodaje nowego użytkownika.
     *
     * @param userCreateRequest nowy użytkownik
     */
    public User save(UserCreateRequest userCreateRequest) {
        return userRepository.save(mapper.mapToEntity(userCreateRequest));
    }

    /**
     * Aktualizuje użytkownika.
     *
     * @param userUpdateRequest aktualizowany użytkownik
     */
    public User update(UserUpdateRequest userUpdateRequest) {
        return userRepository.save(mapper.mapToEntity(userUpdateRequest));
    }
}
