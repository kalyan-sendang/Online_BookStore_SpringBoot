package com.task.lms.config;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI openAPI()
    {
        return new OpenAPI()
                .info(new Info().title("Online Book Store Api")
                        .description("Book Store Application")
                        .version("1.0").contact(new Contact().name("kalyan").email("kalyansendang10@gmail.com"))
                        .license(new License().name("Apache")))
                .externalDocs(new ExternalDocumentation().url("springboot.com").description("This is external url"));
    }
}
