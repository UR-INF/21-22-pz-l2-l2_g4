package com.example.hurtownia.domain.user;

import com.example.hurtownia.domain.user.request.UserCreateRequest;
import com.example.hurtownia.domain.user.request.UserUpdateRequest;
import org.hibernate.ObjectNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.naming.OperationNotSupportedException;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    static final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    static User user;
    static UserDTO userDTO;
    static List<User> listOfUser;
    static List<UserDTO> listOfUserDTO;
    static UserCreateRequest userCreateRequest;
    static UserUpdateRequest userUpdateRequest;
    static String rawPassword;
    @Mock
    UserMapper userMapper;
    @Mock
    UserRepository userRepository;
    @InjectMocks
    @Spy
    UserService userService;

    @BeforeEach
    void setUp() {
        rawPassword = "admin";

        user = User.builder()
                .id(1L)
                .name("name")
                .surname("surname")
                .email("admin")
                .password(passwordEncoder.encode(rawPassword))
                .phoneNumber("phoneNumber")
                .isAdmin(Boolean.TRUE)
                .generatingReports(Boolean.TRUE)
                .grantingDiscounts(Boolean.TRUE)
                .build();

        userDTO = UserDTO.builder()
                .id(1L)
                .name("name")
                .surname("surname")
                .email("admin")
                .password(passwordEncoder.encode(rawPassword))
                .phoneNumber("phoneNumber")
                .isAdmin(Boolean.TRUE)
                .generatingReports(Boolean.TRUE)
                .grantingDiscounts(Boolean.TRUE)
                .build();

        userCreateRequest = UserCreateRequest.builder()
                .name("name")
                .surname("surname")
                .email("admin")
                .password(rawPassword)
                .phoneNumber("phoneNumber")
                .isAdmin(Boolean.TRUE)
                .generatingReports(Boolean.TRUE)
                .grantingDiscounts(Boolean.TRUE)
                .build();

        userUpdateRequest = UserUpdateRequest.builder()
                .id(2L)
                .name("name2")
                .surname("surname2")
                .email("admin2")
                .password("admin2")
                .phoneNumber("phoneNumber2")
                .isAdmin(Boolean.FALSE)
                .generatingReports(Boolean.FALSE)
                .grantingDiscounts(Boolean.FALSE)
                .build();

        listOfUser = List.of(user);
        listOfUserDTO = List.of(userDTO);
    }

    @Test
    void findAll() {
        when(userRepository.findAll()).thenReturn(listOfUser);
        when(userMapper.mapListToDto(listOfUser)).thenReturn(listOfUserDTO);
        assertThat(userService.findAll()).isEqualTo(listOfUserDTO);
    }

    @Test
    void findById() {
        when(userRepository.findById(any())).thenReturn(Optional.empty());
        assertThatThrownBy(() -> userService.findById(any())).isInstanceOf(ObjectNotFoundException.class);

        when(userRepository.findById(any())).thenReturn(Optional.of(user));
        assertThat(userService.findById(any())).isEqualTo(user);
    }

    @Test
    void findByEmail() {
        when(userRepository.findByEmail(any())).thenReturn(Optional.of(user));
        assertThat(userService.findByEmail(any())).isEqualTo(user);
    }

    @Test
    void login() throws Exception {
        when(userRepository.findByEmail(any())).thenReturn(Optional.ofNullable(user));
        assertThat(userService.login(user.getEmail(), rawPassword)).isEqualTo(user);
    }

    @Test
    void delete() {
    }

    @Test
    void create() {
        when(userRepository.save(any())).thenAnswer(i -> i.getArgument(0));
        assertThat(userService.create(userCreateRequest))
                .satisfies(newUser -> {
                    assertThat(newUser.getName()).isEqualTo(userCreateRequest.getName());
                    assertThat(newUser.getSurname()).isEqualTo(userCreateRequest.getSurname());
                    assertThat(newUser.getEmail()).isEqualTo(userCreateRequest.getEmail());
                    assertThat(newUser.getPhoneNumber()).isEqualTo(userCreateRequest.getPhoneNumber());
                    assertThat(newUser.getIsAdmin()).isEqualTo(userCreateRequest.getIsAdmin());
                    assertThat(newUser.getGeneratingReports()).isEqualTo(userCreateRequest.getGeneratingReports());
                    assertThat(newUser.getGrantingDiscounts()).isEqualTo(userCreateRequest.getGrantingDiscounts());
                });
    }

    @Test
    void update() {
        when(userRepository.findById(any())).thenReturn(Optional.ofNullable(user));
        when(userRepository.save(any())).thenAnswer(i -> i.getArgument(0));
        assertThat(userService.update(userUpdateRequest))
                .satisfies(newUser -> {
                    assertThat(newUser.getName()).isEqualTo(userUpdateRequest.getName());
                    assertThat(newUser.getSurname()).isEqualTo(userUpdateRequest.getSurname());
                    assertThat(newUser.getEmail()).isEqualTo(userUpdateRequest.getEmail());
                    assertThat(newUser.getPhoneNumber()).isEqualTo(userUpdateRequest.getPhoneNumber());
                    assertThat(newUser.getIsAdmin()).isEqualTo(userUpdateRequest.getIsAdmin());
                    assertThat(newUser.getGeneratingReports()).isEqualTo(userUpdateRequest.getGeneratingReports());
                    assertThat(newUser.getGrantingDiscounts()).isEqualTo(userUpdateRequest.getGrantingDiscounts());
                });
    }
}