package com.example.hurtownia.authentication;

import com.example.hurtownia.domain.user.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@ExtendWith(MockitoExtension.class)
class LoginServiceTest {

    static User currentUser;
    @InjectMocks
    @Spy
    LoginService loginService;

    @BeforeEach
    void setUp() {
        currentUser = User.builder()
                .id(1L)
                .name("name")
                .surname("surname")
                .email("admin")
                .password("admin")
                .phoneNumber("phoneNumber")
                .isAdmin(Boolean.TRUE)
                .generatingReports(Boolean.TRUE)
                .grantingDiscounts(Boolean.TRUE)
                .build();

        loginService.setCurrentUser(currentUser);
    }

    @Test
    void logIn() {
    }

    @Test
    void logOut() {
    }

    @Test
    void getCurrentUser() {
        assertThat(loginService.getCurrentUser()).isEqualTo(currentUser);
    }

    @Test
    void getCurrentUserName() {
        assertThat(loginService.getCurrentUserName()).isEqualTo(currentUser.getName() + " " + currentUser.getSurname());
    }
}