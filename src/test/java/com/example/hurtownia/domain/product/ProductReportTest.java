package com.example.hurtownia.domain.product;

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
class ProductReportTest {

    static User user;
    @Mock
    LoginService loginService;
    @InjectMocks
    @Spy
    ProductReport productReport;

    @BeforeEach
    void setUp() {
        ProductDTO product1 = ProductDTO.builder()
                .id(1L)
                .supplierId(1L)
                .name("Panel podłogowy")
                .country("Polska")
                .code("9dn2nd2d")
                .color("Grey")
                .maxNumber(10000)
                .price(82.6)
                .unitOfMeasurement("m^2")
                .number(12321)
                .build();

        ProductDTO product2 = ProductDTO.builder()
                .id(2L)
                .supplierId(2L)
                .name("Panel podłogowy 2")
                .country("Polska")
                .code("dfgbrgh")
                .color("Grey")
                .maxNumber(100)
                .price(53.25)
                .unitOfMeasurement("m^2")
                .number(156)
                .build();

        ProductDTO product3 = ProductDTO.builder()
                .id(3L)
                .supplierId(3L)
                .name("Panel podłogowy 3")
                .country("Polska")
                .code("sfgjyj")
                .color("Grey")
                .maxNumber(100)
                .price(50.0)
                .unitOfMeasurement("m^2")
                .number(147)
                .build();

        List<ProductDTO> data = List.of(product1, product2, product3);
        productReport.setData(data);

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
        File file = new File(new File("").getAbsolutePath() + "/ProductTest.pdf");
        if (file.exists()) file.delete();
    }

    @Test
    void generateReport() throws IOException {
        when(loginService.getCurrentUserName()).thenReturn(user.getName() + " " + user.getSurname());
        File file = new File(new File("").getAbsolutePath() + "/ProductTest.pdf");
        productReport.generateReport(file.getAbsolutePath(), "Product report");
        assertTrue(file.exists());
    }
}