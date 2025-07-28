package mvpproject.baseservice.hotelcrm.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "Property View API",
                version = "1.0",
                description = "API для управления отелями"
        )
)
public class OpenApiConfig {
        @Bean
        public GroupedOpenApi publicApi() {
                return GroupedOpenApi.builder()
                        .group("property-view")
                        .pathsToMatch("/property-view/**")
                        .build();
        }
}