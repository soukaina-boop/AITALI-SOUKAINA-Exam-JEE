package org.aitali.soukaina.examjee.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "Gestion des Credits Bancaires API",
                version = "1.0",
                description = "Documentation OpenAPI des services REST de gestion des credits bancaires"
        )
)
public class OpenApiConfig {
}
