package com.example.hurtownia.controllers;

import com.example.hurtownia.authentication.LoginService;
import com.example.hurtownia.domain.customer.CustomerRepository;
import com.example.hurtownia.domain.customer.CustomerService;
import com.example.hurtownia.domain.user.User;
import com.example.hurtownia.domain.user.UserService;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.testfx.framework.junit5.ApplicationTest;
import org.testfx.framework.junit5.Start;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MainControllerTest {

    static User user;
    @Mock
    Tab customerTab, orderTab, userTab;
    @Mock
    AnchorPane loginPane;
    @Mock
    Text clockLabel, userNameLabel, loginErrorLabel;
    @Mock
    LoginService loginService;
    @Mock
    UserService userService;
    @InjectMocks
    @Spy
    MainController mainController;

    @BeforeEach
    void setUp() {
        user = User.builder()
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
    }

    @Test
    void initialize() {
        mainController.initialize(any(), any());
    }

    @Test
    void btnLogInClicked() {
        when(loginService.getCurrentUser()).thenReturn(user);
        mainController.btnLogInClicked(any());
    }

    @Test
    void btnLogOutClicked() {
    }

    @Test
    void btnExitClicked() {
    }
}