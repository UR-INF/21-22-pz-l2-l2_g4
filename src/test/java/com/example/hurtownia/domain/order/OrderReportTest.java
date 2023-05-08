package com.example.hurtownia.domain.order;

import com.example.hurtownia.authentication.LoginService;
import com.example.hurtownia.domain.user.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.File;
import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OrderReportTest {

    static User user;
    @Mock
    LoginService loginService;
    @InjectMocks
    @Spy
    OrderReport orderReport;

    @BeforeEach
    void setUp() {
        List<OrderDTO> data = List.of(
                new OrderDTO(1L, 1L, "2023-02-02", 0.1, "w przygotowaniu", 120.00),
                new OrderDTO(1L, 1L, "2023-02-02", 0.1, "w przygotowaniu", 120.00),
                new OrderDTO(1L, 1L, "2023-02-02", 0.1, "w przygotowaniu", 120.00)
        );
        orderReport.setData(data);

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

    @AfterEach
    void clean() {
        File file = new File(new File("").getAbsolutePath() + "/OrderTest.pdf");
        if (file.exists()) file.delete();
    }

    @Test
    void generateReport() throws IOException {
        when(loginService.getCurrentUserName()).thenReturn(user.getName() + " " + user.getSurname());
        File file = new File(new File("").getAbsolutePath() + "/OrderTest.pdf");
        orderReport.generateReport(file.getAbsolutePath(), "Order report");
        assertTrue(file.exists());
    }
}