module com.example.hurtownia {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.hibernate.orm.core;
    requires java.xml;
    requires java.persistence;
    requires java.naming;
    requires java.desktop;
    requires org.hibernate.commons.annotations;
    requires java.sql;
    requires kernel;
    requires layout;

    opens Controllers to javafx.fxml;

    exports Controllers;
    exports Main;
    exports DatabaseAccess;
    exports DatabaseQueries;
    exports Singleton;
    exports PDFGeneration;
    exports domain.Customer;
    opens domain.Customer;
    exports domain.Supplier;
    opens domain.Supplier;
    exports domain.Product;
    opens domain.Product;
    exports domain.Order;
    opens domain.Order;
    exports domain.OrderItem;
    opens domain.OrderItem;
    exports domain.User;
    opens domain.User;
}