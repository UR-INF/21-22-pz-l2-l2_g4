package com.example.hurtownia.domain.customer;

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

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CustomerReportTest {

    static User user;
    @Mock
    LoginService loginService;
    @InjectMocks
    @Spy
    CustomerReport customerReport;

    @BeforeEach
    void setUp() {
        List<CustomerDTO> data = List.of(
                new CustomerDTO(1L, "Customer", "Surname", "2345678978", "32873782", "email@com.pl", "Place", "Street", 1, 1),
                new CustomerDTO(2L, "Customer2", "Surname2", "23356743211", "132456321", "email2@com.pl", "Place2", "Street2", 2, 20),
                new CustomerDTO(3L, "Customer3", "Surname3", "56935489733", "569854758", "email3@com.pl", "Place3", "Street3", 3, 30)
        );
        customerReport.setData(data);

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
    void clean(){
        File file = new File(new File("").getAbsolutePath()+"/CustomerTest.pdf");
        if (file.exists()) file.delete();
    }

    @Test
    void generateReport() throws IOException {
        when(loginService.getCurrentUserName()).thenReturn(user.getName()+ " " + user.getSurname());
        File file = new File(new File("").getAbsolutePath()+"/CustomerTest.pdf");
        customerReport.generateReport(file.getAbsolutePath(), "Customer report");
        assertTrue(file.exists());
    }
}