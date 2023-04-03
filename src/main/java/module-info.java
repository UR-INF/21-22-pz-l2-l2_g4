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
    opens Entities;

    exports Controllers;
    exports Main;
    exports DatabaseAccess;
    exports DatabaseQueries;
    exports Entities;
    exports Singleton;
    exports PDFGeneration;
}