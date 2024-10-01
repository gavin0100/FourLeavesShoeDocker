package com.data.filtro.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;

// http://localhost:8080/swagger-ui/index.html#/
@OpenAPIDefinition(
        info = @Info(
                contact = @Contact(
                        name = "",
                        email = "",
                        url = ""
                ),
                description = "FOUR LEAVES SHOES",
                title = "",
                version = "",
                license = @License(
                        name = "",
                        url = ""
                ),
                termsOfService = ""
        ),
        servers = {
                @Server(
                        description = "",
                        url = "http://localhost:8080"
                ),
                @Server(
                        description = "",
                        url = "#"
                ),
        }
)
@SecurityScheme(
        name = "Authorization",
        description = "JWT Token",
        type = SecuritySchemeType.HTTP,
        bearerFormat = "JWT",
        scheme = "bearer",
        in = SecuritySchemeIn.HEADER
)

public class OpenAIConfig {

}
