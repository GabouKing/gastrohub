package com.example.gastrohub.infra.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("GastroHub API")
                        .description("API para gerenciamento de restaurantes, usuários e pedidos.")
                        .version("1.0.0")
                        .contact(new Contact()
                                .name("João Marcos")
                                .email("joao@email.com"))
                        .license(new License()
                                .name("MIT")));
    }

}
