package com.example.hurtownia.controllers;

import com.example.hurtownia.domain.customer.Customer;
import com.example.hurtownia.domain.customer.CustomerDTO;
import com.example.hurtownia.domain.customer.CustomerService;
import com.example.hurtownia.domain.order.Order;
import com.example.hurtownia.domain.order.OrderDTO;
import com.example.hurtownia.domain.order.OrderService;
import com.example.hurtownia.domain.orderitem.OrderItem;
import com.example.hurtownia.domain.orderitem.OrderItemService;
import com.example.hurtownia.domain.product.Product;
import com.example.hurtownia.domain.product.ProductDTO;
import com.example.hurtownia.domain.product.ProductService;
import com.example.hurtownia.domain.supplier.Supplier;
import com.example.hurtownia.domain.supplier.SupplierDTO;
import com.example.hurtownia.domain.supplier.SupplierService;
import com.example.hurtownia.domain.user.User;
import com.example.hurtownia.domain.user.UserService;
import com.opencsv.CSVWriter;
import com.opencsv.exceptions.CsvValidationException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.testfx.framework.junit5.ApplicationTest;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ImportCSVControllerTest extends ApplicationTest {
    static SupplierDTO supplierDTO;
    static Product product;
    static ProductDTO productDTO;
    static Supplier supplier;
    static Customer customer;
    static CustomerDTO customerDTO;
    static User user;
    static OrderItem orderItem;
    static Order order;
    static OrderDTO orderDTO;
    static List<SupplierDTO> listOfSupplierDTO;
    static List<CustomerDTO> listOfCustomerDTO;
    static List<OrderDTO> listOfOrderDTO;
    static List<ProductDTO> listOfProductDTO;
    @Mock
    SupplierService supplierService;
    @Mock
    ProductService productService;
    @Mock
    CustomerService customerService;
    @Mock
    UserService userService;
    @Mock
    OrderService orderService;
    @Mock
    OrderItemService orderItemService;
    @InjectMocks
    @Spy
    ImportCSVController importCSVController;

    @BeforeEach
    void setUp() {
        supplierDTO = SupplierDTO.builder()
                .id(1L)
                .email("email@email.com")
                .country("Polska")
                .place("Rzeszów")
                .street("Rejtana")
                .name("Dostawca X")
                .nip("83728932")
                .build();

        supplier = Supplier.builder()
                .id(1L)
                .email("email@email.com")
                .country("Polska")
                .place("Rzeszów")
                .street("Rejtana")
                .name("Dostawca X")
                .nip("83728932")
                .build();

        product = Product.builder()
                .id(1L)
                .supplier(supplier)
                .name("Panel podłogowy")
                .country("Polska")
                .code("9dn2nd2d")
                .color("Grey")
                .maxNumber(10000)
                .price(82.6)
                .unitOfMeasurement("m^2")
                .number(100)
                .build();

        productDTO = ProductDTO.builder()
                .id(1L)
                .supplierId(1L)
                .name("Panel podłogowy")
                .country("Polska")
                .code("9dn2nd2d")
                .color("Grey")
                .maxNumber(10000)
                .price(82.6)
                .unitOfMeasurement("m^2")
                .number(100)
                .build();

        customer = Customer.builder()
                .name("Customer")
                .surname("Surname")
                .pesel("21973812L")
                .email("email@com.pl")
                .place("Place")
                .zipCode("30-300")
                .phoneNumber("32873782")
                .apartmentNumber(1)
                .buildingNumber(1)
                .street("Street")
                .build();

        customerDTO = CustomerDTO.builder()
                .name("Customer")
                .surname("Surname")
                .pesel("21973812L")
                .email("email@com.pl")
                .place("Place")
                .zipCode("30-300")
                .phoneNumber("32873782")
                .apartmentNumber(1)
                .buildingNumber(1)
                .street("Street")
                .build();

        user = User.builder()
                .id(1L)
                .name("name")
                .surname("surname")
                .email("admin")
                .password("password")
                .phoneNumber("phoneNumber")
                .isAdmin(Boolean.TRUE)
                .generatingReports(Boolean.TRUE)
                .grantingDiscounts(Boolean.TRUE)
                .build();

        order = Order.builder()
                .id(1L)
                .customer(customer)
                .date("23.12.3233")
                .state("state")
                .discount(10.0)
                .build();

        orderDTO = OrderDTO.builder()
                .id(1L)
                .customerId(1L)
                .date("23.12.3233")
                .state("state")
                .discount(10.0)
                .build();

        orderItem = OrderItem.builder()
                .id(1L)
                .order(order)
                .amount(100)
                .product(product)
                .pricePerUnit(1.00)
                .build();

        listOfSupplierDTO = List.of(supplierDTO);
        listOfCustomerDTO = List.of(customerDTO);
        listOfOrderDTO = List.of(orderDTO);
        listOfProductDTO = List.of(productDTO);

        importCSVController.setController(new MainController());
        importCSVController.getMainController().supplierService = supplierService;
        importCSVController.getMainController().productService = productService;
        importCSVController.getMainController().customerService = customerService;
        importCSVController.getMainController().userService = userService;
        importCSVController.getMainController().orderService = orderService;
        importCSVController.getMainController().orderItemService = orderItemService;
    }

    @AfterEach
    void clean() {
        File file = new File(new File("").getAbsolutePath() + "/Products.csv");
        if (file.exists()) file.delete();

        file = new File(new File("").getAbsolutePath() + "/Suppliers.csv");
        if (file.exists()) file.delete();

        file = new File(new File("").getAbsolutePath() + "/Customers.csv");
        if (file.exists()) file.delete();

        file = new File(new File("").getAbsolutePath() + "/Users.csv");
        if (file.exists()) file.delete();

        file = new File(new File("").getAbsolutePath() + "/Orders.csv");
        if (file.exists()) file.delete();

        file = new File(new File("").getAbsolutePath() + "/OrderItems.csv");
        if (file.exists()) file.delete();
    }

    @Test
    void importProducts() throws CsvValidationException, IOException {
        File file = new File(new File("").getAbsolutePath() + "/Products.csv");
        try {
            FileWriter outputFile = new FileWriter(file);
            CSVWriter writer = new CSVWriter(outputFile, ',',
                    CSVWriter.NO_QUOTE_CHARACTER,
                    CSVWriter.DEFAULT_ESCAPE_CHARACTER,
                    CSVWriter.DEFAULT_LINE_END);

            String[] header = {"id", "kodProduktu", "kolor", "ilość", "maxIlość", "cena", "jednostka", "kraj", "nazwa", "idDostawcy"};
            writer.writeNext(header);

            String[] data = {"1", "645", "black", "10", "20", "12.50", "sztuka", "Polska", "Produkt1", "1"};
            writer.writeNext(data);

            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        importCSVController.selectedFile = new File(new File("").getAbsolutePath() + "/Products.csv");

        when(supplierService.findAll()).thenReturn(listOfSupplierDTO);
        when(productService.create(any())).thenReturn(product);

        importCSVController.importProducts();
        verify(importCSVController.getMainController().productService, times(1)).create(any());
    }

    @Test
    void importSuppliers() throws CsvValidationException, IOException {
        File file = new File(new File("").getAbsolutePath() + "/Suppliers.csv");
        try {
            FileWriter outputFile = new FileWriter(file);
            CSVWriter writer = new CSVWriter(outputFile, ',',
                    CSVWriter.NO_QUOTE_CHARACTER,
                    CSVWriter.DEFAULT_ESCAPE_CHARACTER,
                    CSVWriter.DEFAULT_LINE_END);

            String[] header = {"id", "email", "kraj", "miejscowosc", "nazwa", "ulica", "nip"};
            writer.writeNext(header);

            String[] data = {"1", "email@post.pl", "Polska", "Warszawa", "Dostawca1", "Kwiatowa", "125964875"};
            writer.writeNext(data);

            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        importCSVController.selectedFile = new File(new File("").getAbsolutePath() + "/Suppliers.csv");

        when(supplierService.create(any())).thenReturn(supplier);

        importCSVController.importSuppliers();
        verify(importCSVController.getMainController().supplierService, times(1)).create(any());
    }

    @Test
    void importCustomers() throws CsvValidationException, IOException {
        File file = new File(new File("").getAbsolutePath() + "/Customers.csv");
        try {
            FileWriter outputFile = new FileWriter(file);
            CSVWriter writer = new CSVWriter(outputFile, ',',
                    CSVWriter.NO_QUOTE_CHARACTER,
                    CSVWriter.DEFAULT_ESCAPE_CHARACTER,
                    CSVWriter.DEFAULT_LINE_END);

            String[] header = {"id", "email", "imie", "nazwisko", "kod pocztowy", "miejscowosc", "nr budynku", "nr mieszkania", "ulica", "nr telefonu", "pesel"};
            writer.writeNext(header);

            String[] data = {"1", "email@post.pl", "Adam", "Nowak", "30-302", "Warszawa", "2", "12", "Kwiatowa", "125364895", "14523659874"};
            writer.writeNext(data);

            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        importCSVController.selectedFile = new File(new File("").getAbsolutePath() + "/Customers.csv");

        when(customerService.create(any())).thenReturn(customer);

        importCSVController.importCustomers();
        verify(importCSVController.getMainController().customerService, times(1)).create(any());
    }

    @Test
    void importUsers() throws CsvValidationException, IOException {
        File file = new File(new File("").getAbsolutePath() + "/Users.csv");
        try {
            FileWriter outputFile = new FileWriter(file);
            CSVWriter writer = new CSVWriter(outputFile, ',',
                    CSVWriter.NO_QUOTE_CHARACTER,
                    CSVWriter.DEFAULT_ESCAPE_CHARACTER,
                    CSVWriter.DEFAULT_LINE_END);

            String[] header = {"id", "imie", "nazwisko", "email", "haslo", "admin", "nr telefonu", "gen raportow", "udz rabatow"};
            writer.writeNext(header);

            String[] data = {"1", "Adam", "Nowak", "email@post.pl", "haslo", "true", "125364895", "true", "true"};
            writer.writeNext(data);

            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        importCSVController.selectedFile = new File(new File("").getAbsolutePath() + "/Users.csv");

        when(userService.create(any())).thenReturn(user);

        importCSVController.importUsers();
        verify(importCSVController.getMainController().userService, times(1)).create(any());
    }

    @Test
    void importOrders() throws CsvValidationException, IOException {
        File file = new File(new File("").getAbsolutePath() + "/Orders.csv");
        try {
            FileWriter outputFile = new FileWriter(file);
            CSVWriter writer = new CSVWriter(outputFile, ',',
                    CSVWriter.NO_QUOTE_CHARACTER,
                    CSVWriter.DEFAULT_ESCAPE_CHARACTER,
                    CSVWriter.DEFAULT_LINE_END);

            String[] header = {"id", "data", "idKlienta", "rabat", "statusZamowienia"};
            writer.writeNext(header);

            String[] data = {"1", "10-10-2023", "1", "0.1", "w przygotowaniu"};
            writer.writeNext(data);

            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        importCSVController.selectedFile = new File(new File("").getAbsolutePath() + "/Orders.csv");

        when(customerService.findAll()).thenReturn(listOfCustomerDTO);
        when(orderService.create(any())).thenReturn(order);

        importCSVController.importOrders();
        verify(importCSVController.getMainController().orderService, times(1)).create(any());
    }

    @Test
    void importOrderItems() throws CsvValidationException, IOException, InvocationTargetException, IllegalAccessException {
        File file = new File(new File("").getAbsolutePath() + "/OrderItems.csv");
        try {
            FileWriter outputFile = new FileWriter(file);
            CSVWriter writer = new CSVWriter(outputFile, ',',
                    CSVWriter.NO_QUOTE_CHARACTER,
                    CSVWriter.DEFAULT_ESCAPE_CHARACTER,
                    CSVWriter.DEFAULT_LINE_END);

            String[] header = {"id", "liczba", "id produktu", "id zamowienia"};
            writer.writeNext(header);

            String[] data = {"1", "3", "1", "1"};
            writer.writeNext(data);

            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        importCSVController.selectedFile = new File(new File("").getAbsolutePath() + "/OrderItems.csv");

        when(orderService.findAll()).thenReturn(listOfOrderDTO);
        when(productService.findAll()).thenReturn(listOfProductDTO);
        when(orderItemService.create(any())).thenReturn(orderItem);

        importCSVController.importOrderItems();
        verify(importCSVController.getMainController().orderItemService, times(1)).create(any());
    }
}