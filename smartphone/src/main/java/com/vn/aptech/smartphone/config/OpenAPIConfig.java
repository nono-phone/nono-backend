package com.vn.aptech.smartphone.config;

import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.security.SecurityScheme;

@SecurityScheme(
        name = "refresh_token",
        scheme = "Bearer",
        type = SecuritySchemeType.HTTP,
        in = SecuritySchemeIn.HEADER)
@SecurityScheme(
        name = "access_token",
        scheme = "Bearer",
        type = SecuritySchemeType.HTTP,
        in = SecuritySchemeIn.HEADER)
@OpenAPIDefinition(
        info = @Info(
                title = "Spring Boot REST API Documentation",
                description = "Spring boot REST API Documentation",
                version = "v1.0",
                contact = @Contact(
                        name = "Your name",
                        email = "your email",
                        url = "url contact your"
                ),
                license = @License(
                        name = "Apache 2.0",
                        url = "jkdshfjsdhfjd"
                )
        ),
        externalDocs = @ExternalDocumentation(
                description = "",
                url = ""
        )
)
public class OpenAPIConfig {
}
