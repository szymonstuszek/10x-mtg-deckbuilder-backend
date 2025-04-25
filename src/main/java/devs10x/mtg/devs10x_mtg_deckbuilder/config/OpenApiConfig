package devs10x.mtg.devs10x_mtg_deckbuilder.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI().info(new Info()
            .title("10x MTG Deckbuilder API")
            .version("1.0")
            .description("API specification for the 10x MTG Deckbuilder service."));
    }
}