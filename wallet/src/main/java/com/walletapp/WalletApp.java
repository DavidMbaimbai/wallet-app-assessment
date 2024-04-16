package com.walletapp;

import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@OpenAPIDefinition(
        info = @Info(
                title = "Wallet App microservice REST API Documentation",
                description = "Wallet App microservice Rest API Documentation",
                version = "v1",
                contact = @Contact(
                        name = "",
                        email = "davymbaimbai@gmail.com",
                        url = "https://www.davidm.com"
                ),
                license = @License(
                        name = "Apache 2.0",
                        url = "https://www.davidm.com"
                )
        ),
        externalDocs = @ExternalDocumentation(
                description = "Wallet App microservice Rest API Documentation",
                url = "https://www.davidm.com"
        )
)
public class WalletApp {
    public static void main(String[] args) {
        SpringApplication.run(WalletApp.class, args);
    }
}
