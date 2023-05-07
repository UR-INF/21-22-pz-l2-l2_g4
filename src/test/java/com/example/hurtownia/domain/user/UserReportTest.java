package com.example.hurtownia.domain.user;

import com.example.hurtownia.authentication.LoginService;
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
class UserReportTest {

    static User user;
    @Mock
    LoginService loginService;
    @InjectMocks
    @Spy
    UserReport userReport;

    @BeforeEach
    void setUp() {
        UserDTO user1 = UserDTO.builder()
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

        UserDTO user2 = UserDTO.builder()
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

        UserDTO user3 = UserDTO.builder()
                .id(3L)
                .name("name3")
                .surname("surname3")
                .email("admin3")
                .password("admin3")
                .phoneNumber("phoneNumber3")
                .isAdmin(Boolean.FALSE)
                .generatingReports(Boolean.FALSE)
                .grantingDiscounts(Boolean.TRUE)
                .build();

        List<UserDTO> data = List.of(user1, user2, user3);
        userReport.setData(data);

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
        File file = new File(new File("").getAbsolutePath() + "/UserTest.pdf");
        if (file.exists()) file.delete();
    }

    @Test
    void generateReport() throws IOException {
        when(loginService.getCurrentUserName()).thenReturn(user.getName() + " " + user.getSurname());
        File file = new File(new File("").getAbsolutePath() + "/UserTest.pdf");
        userReport.generateReport(file.getAbsolutePath(), "User report");
        assertTrue(file.exists());
    }
}