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
                        .version("2.0.0")
                        .description("Sistema de gestão de usuários para plataforma compartilhada de restaurantes - Tech Challenge Fase 2.")
                        .contact(new Contact().name("Equipe GastroHub"))
                        .license(new License().name("Uso acadêmico")));
    }

}
