package com.example.hurtownia.domain.order;

import com.example.hurtownia.authentication.LoginService;
import com.example.hurtownia.domain.customer.Customer;
import com.example.hurtownia.domain.customer.CustomerDTO;
import com.example.hurtownia.domain.orderitem.OrderItem;
import com.example.hurtownia.domain.product.Product;
import com.example.hurtownia.domain.supplier.Supplier;
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
class InvoiceReportTest {

    static User user;
    @Mock
    LoginService loginService;
    @InjectMocks
    @Spy
    InvoiceReport invoiceReport;

    @BeforeEach
    void setUp() {
        Supplier supplier = Supplier.builder()
                .id(1L)
                .email("email@email.com")
                .country("Polska")
                .place("Rzeszów")
                .street("Rejtana")
                .name("Dostawca X")
                .nip("83728932")
                .build();

        Product product = Product.builder()
                .id(1L)
                .supplier(supplier)
                .name("Panel podłogowy")
                .country("Polska")
                .code("9dn2nd2d")
                .color("Grey")
                .maxNumber(10000)
                .price(82.6)
                .unitOfMeasurement("m^2")
                .number(12321)
                .build();

        Customer customer = Customer.builder()
                .id(1L)
                .name("Customer")
                .surname("Surname")
                .pesel("21973812L")
                .email("email@com.pl")
                .place("Place")
                .phoneNumber("32873782")
                .apartmentNumber(1)
                .buildingNumber(1)
                .street("Street")
                .build();

        Order order = Order.builder()
                .id(1L)
                .customer(customer)
                .date("23.12.3233")
                .state("state")
                .discount(10.0)
                .build();

        OrderItem orderItem = OrderItem.builder()
                .id(1L)
                .order(order)
                .amount(100)
                .product(product)
                .pricePerUnit(1.00)
                .build();

        List<OrderItem> items = List.of(orderItem);

        InvoiceData invoiceData = InvoiceData.builder()
                .name("name")
                .surname("surname")
                .place("place")
                .street("street")
                .buildingNumber(1)
                .apartmentNumber(1)
                .phoneNumber("123456789")
                .date("2023-02-02")
                .value("120.00")
                .discount("0.1")
                .valueAfterDiscount("108.00")
                .items(items)
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

        invoiceReport.setData(invoiceData);
    }

    @AfterEach
    void clean() {
        File file = new File(new File("").getAbsolutePath() + "/InvoiceTest.pdf");
        if (file.exists()) file.delete();
    }

    @Test
    void generateReport()  throws IOException {
        when(loginService.getCurrentUserName()).thenReturn(user.getName()+ " " + user.getSurname());
        File file = new File(new File("").getAbsolutePath()+"/InvoiceTest.pdf");
        invoiceReport.generateReport(file.getAbsolutePath(), "Invoice");
        assertTrue(file.exists());
    }
}