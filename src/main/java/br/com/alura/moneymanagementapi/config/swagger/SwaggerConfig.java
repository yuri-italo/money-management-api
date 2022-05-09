package br.com.alura.moneymanagementapi.config.swagger;

import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {
    @Configuration
    @OpenAPIDefinition(info = @Info(title = "Money Management API", description = "API for money management",
    contact = @Contact(name = "Yuri Italo", email = "yuri.italo94@gmail.com",url = "https://github.com/yuri-italo"),version = "1.0.0"),
    externalDocs = @ExternalDocumentation(description = "APACHE LICENSE, VERSION 2.0",url = "https://www.apache.org/licenses/LICENSE-2.0"))
    public static class Swagger {
        @Bean
        public OpenAPI customOpenAPI() {
            return new OpenAPI().components(new Components().addSecuritySchemes("bearerAuth",
                    new SecurityScheme().type(SecurityScheme.Type.HTTP).scheme("bearer").bearerFormat("JWT")));
        }
    }
}
