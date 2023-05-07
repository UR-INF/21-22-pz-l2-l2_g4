package com.example.hurtownia.domain.supplier;

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
class SupplierReportTest {

    static User user;
    @Mock
    LoginService loginService;
    @InjectMocks
    @Spy
    SupplierReport supplierReport;

    @BeforeEach
    void setUp() {
        SupplierDTO supplier1 = SupplierDTO.builder()
                .id(1L)
                .email("email@email.com")
                .country("Polska")
                .place("Rzeszów")
                .street("Rejtana")
                .name("Dostawca 1")
                .nip("83728932")
                .build();

        SupplierDTO supplier2 = SupplierDTO.builder()
                .id(2L)
                .email("email2@email.com")
                .country("Polska")
                .place("Rzeszów")
                .street("Rejtana")
                .name("Dostawca 2")
                .nip("12345432")
                .build();

        SupplierDTO supplier3 = SupplierDTO.builder()
                .id(3L)
                .email("email3@email.com")
                .country("Polska")
                .place("Rzeszów")
                .street("Rejtana")
                .name("Dostawca 3")
                .nip("88664422")
                .build();

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

        List<SupplierDTO> data = List.of(supplier1, supplier2, supplier3);
        supplierReport.setData(data);
    }

    @AfterEach
    void clean() {
        File file = new File(new File("").getAbsolutePath() + "/SupplierTest.pdf");
        if (file.exists()) file.delete();
    }

    @Test
    void generateReport() throws IOException {
        when(loginService.getCurrentUserName()).thenReturn(user.getName() + " " + user.getSurname());
        File file = new File(new File("").getAbsolutePath() + "/SupplierTest.pdf");
        supplierReport.generateReport(file.getAbsolutePath(), "Supplier report");
        assertTrue(file.exists());
    }
}