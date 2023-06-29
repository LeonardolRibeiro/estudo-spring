package com.leolaia.estudospring.configs;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenApi(){
        return new OpenAPI()
                .info(new Info()
                        .title("Estudo Spring")
                        .version("v1")
                        .description("Projeto de exemplo para mostrar/exemplificar/exercitar/adquirir alguns conhecimentos")
                        .termsOfService("https://springdoc.org/v2/")//Geralmente passamos uma url com os termos de serviço
                        .license(
                                new License()
                                    .name("Apache 2.0")
                                    .url("https://springdoc.org/v2/")));
    //Essas configurações podem ser vistas em:
        // Postman: localhost:8080/v3/api-docs
        // Usuarios: localhost:8080/swagger-ui/index.html
    }
}
