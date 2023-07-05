package com.example.hurtownia.domain.orderitem;

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
class OrderItemReportTest {

    static User user;
    @Mock
    LoginService loginService;
    @Mock
    OrderItemService orderItemService;
    @InjectMocks
    @Spy
    OrderItemReport orderItemReport;

    @BeforeEach
    void setUp() {
        List<OrderItemDTO> data = List.of(
                new OrderItemDTO(1L, 1L, 1L, 20, 100.00, 2000.00),
                new OrderItemDTO(2L, 5L, 5L, 50, 30.00, 1500.00),
                new OrderItemDTO(3L, 10L, 10L, 100, 5.00, 500.00)
        );
        List <OrderItemData> dataList = orderItemService.getOrderItemDataList(data);
        orderItemReport.setData(dataList);

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
        File file = new File(new File("").getAbsolutePath() + "/OrderItemTest.pdf");
        if (file.exists()) file.delete();
    }

    @Test
    void generateReport() throws IOException {
        when(loginService.getCurrentUserName()).thenReturn(user.getName() + " " + user.getSurname());
        File file = new File(new File("").getAbsolutePath() + "/OrderItemTest.pdf");
        orderItemReport.generateReport(file.getAbsolutePath(), "Order item report");
        assertTrue(file.exists());
    }
}