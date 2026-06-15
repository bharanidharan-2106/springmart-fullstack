package com.springmart.product.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.Contact;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI productOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("SpringMart Product Service API")
                        .description("API documentation for the Product microservice in SpringMart")
                        .version("1.0.0")
                        .contact(new Contact()
                                .name("SpringMart Team")
                                .email("support@springmart.com")));
    }
}
