package xyz.cryptohows.backend;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class OpenApiConfig {

    @Bean
    public OpenAPI openAPI(@Value("${springdoc.version}") String appVersion) {
        Info info = new Info().title("CryptoHows Backend API")
                .version(appVersion)
                .description("크립토하우스 백엔드 API 명세서");

        return new OpenAPI()
                .components(new Components())
                .info(info);
    }
}
