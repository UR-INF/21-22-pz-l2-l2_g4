package com.example.hurtownia.domain.user;

import com.example.hurtownia.domain.user.request.UserCreateRequest;
import com.example.hurtownia.domain.user.request.UserUpdateRequest;
import org.hibernate.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.naming.OperationNotSupportedException;
import java.util.List;

/**
 * Zawiera metody dla tabeli 'uzytkownik'.
 */
@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserMapper userMapper;

    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    /**
     * Pobiera wszystkich użytkowników.
     *
     * @return lista wszystkich użytkowników
     */
    public List<UserDTO> findAll() {
        return userMapper.mapListToDto(userRepository.findAll());
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

    public User findByEmail(String email) {
        return userRepository.findByEmail(email).orElse(null);
    }

    /**
     * Obsługuje procedurę logowania łącząc się z bazą danych.
     *
     * @param email    email użytkownika
     * @param password hasło użytkownika
     * @return użytkownik
     */
    public User login(String email, String password) throws Exception {
        User user = userRepository.findByEmail(email).orElseThrow(() -> new ObjectNotFoundException(email, "Błędne dane logowania"));
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new Exception();
        }
        return user;
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
    public User create(UserCreateRequest userCreateRequest) {
        if (findByEmail(userCreateRequest.getEmail()) != null) {
            throw new UnsupportedOperationException();
        }

        String password = passwordEncoder.encode(userCreateRequest.getPassword());
        User user = User.builder()
                .name(userCreateRequest.getName())
                .surname(userCreateRequest.getSurname())
                .email(userCreateRequest.getEmail())
                .password(password)
                .phoneNumber(userCreateRequest.getPhoneNumber())
                .isAdmin(userCreateRequest.getIsAdmin())
                .generatingReports(userCreateRequest.getGeneratingReports())
                .grantingDiscounts(userCreateRequest.getGrantingDiscounts())
                .build();
        return userRepository.save(user);
    }

    /**
     * Aktualizuje użytkownika.
     *
     * @param userUpdateRequest aktualizowany użytkownik
     */
    public User update(UserUpdateRequest userUpdateRequest) {
        User user = findById(userUpdateRequest.getId());

        if (!userUpdateRequest.getEmail().equals(user.getEmail())) {
            if (findByEmail(userUpdateRequest.getEmail()) != null) {
                throw new UnsupportedOperationException();
            }
        }

        String password = passwordEncoder.encode(userUpdateRequest.getPassword());
        user.setName(userUpdateRequest.getName());
        user.setSurname(userUpdateRequest.getSurname());
        user.setEmail(userUpdateRequest.getEmail());
        user.setPassword(password);
        user.setPhoneNumber(userUpdateRequest.getPhoneNumber());
        user.setIsAdmin(userUpdateRequest.getIsAdmin());
        user.setGeneratingReports(userUpdateRequest.getGeneratingReports());
        user.setGrantingDiscounts(userUpdateRequest.getGrantingDiscounts());
        return userRepository.save(user);
    }
}
