package com.example.hurtownia.domain.user;

import com.example.hurtownia.domain.user.request.UserCreateRequest;
import com.example.hurtownia.domain.user.request.UserUpdateRequest;
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
        User user = User.builder()
                .name(userCreateRequest.getName())
                .surname(userCreateRequest.getSurname())
                .email(userCreateRequest.getEmail())
                .password(userCreateRequest.getPassword())
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
        User user = User.builder()
                .name(userUpdateRequest.getName())
                .surname(userUpdateRequest.getSurname())
                .email(userUpdateRequest.getEmail())
                .password(userUpdateRequest.getPassword())
                .phoneNumber(userUpdateRequest.getPhoneNumber())
                .isAdmin(userUpdateRequest.getIsAdmin())
                .generatingReports(userUpdateRequest.getGeneratingReports())
                .grantingDiscounts(userUpdateRequest.getGrantingDiscounts())
                .build();
        return userRepository.save(user);
    }
}
