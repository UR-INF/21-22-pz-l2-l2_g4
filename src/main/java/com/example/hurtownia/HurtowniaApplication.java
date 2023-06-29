package com.example.hurtownia;

import javafx.application.Application;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.admin.SpringApplicationAdminJmxAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * Klasa odpowiedzialna za uruchomienie aplikacji.
 */
@EnableJpaRepositories
@EnableAutoConfiguration
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class HurtowniaApplication {

    public static void main(String[] args) {
        Application.launch(Start.class, args);
    }

}
