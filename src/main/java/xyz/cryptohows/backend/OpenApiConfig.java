package xyz.cryptohows.backend;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class OpenApiConfig {

    @Value("${swagger-docs.url}")
    private String url;

    @Bean
    public OpenAPI openAPI(@Value("${springdoc.version}") String appVersion) {
        Components components = new Components()
                .addSecuritySchemes("basicScheme", new SecurityScheme().type(SecurityScheme.Type.HTTP).scheme("basic"));

        Info info = new Info().title("CryptoHows Backend API")
                .version(appVersion)
                .description("크립토하우스 백엔드 API 명세서");

        return new OpenAPI()
                .addServersItem(new Server().url(url))
                .components(components)
                .info(info);
    }
}
