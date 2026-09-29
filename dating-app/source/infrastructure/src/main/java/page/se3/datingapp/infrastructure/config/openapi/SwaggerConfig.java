package page.se3.datingapp.infrastructure.config.openapi;

import io.swagger.v3.core.converter.AnnotatedType;
import io.swagger.v3.core.converter.ModelConverters;
import io.swagger.v3.core.converter.ResolvedSchema;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityScheme;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.customizers.OpenApiCustomizer;
import org.springdoc.core.customizers.OperationCustomizer;
import org.springdoc.core.utils.SpringDocUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import page.se3.datingapp.domain.config.AppConfig;
import page.se3.datingapp.infrastructure.annotation.AuthIdentity;
import page.se3.datingapp.infrastructure.config.openapi.customizer.RootOperationCustomizer;
import page.se3.datingapp.infrastructure.controller.dto.ErrorDto;

@Configuration
@RequiredArgsConstructor
public class SwaggerConfig {

    private final AppConfig appConfig;

    static {
        SpringDocUtils.getConfig().addAnnotationsToIgnore(AuthIdentity.class);
    }

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI().info(new Info()
                .title("Dating App API")
                .version(appConfig.apiVersion())
        );
    }

    @Bean
    public OpenApiCustomizer openApiCustomizer() {
        return openApi -> {
            if (openApi.getComponents() == null) {
                openApi.setComponents(new Components());
            }

            openApi.getComponents().addSecuritySchemes(
                    "BearerAuth",
                    new SecurityScheme()
                            .type(SecurityScheme.Type.HTTP)
                            .scheme("bearer")
                            .bearerFormat("JWT")
            );

            ResolvedSchema resolvedSchema = ModelConverters.getInstance()
                    .resolveAsResolvedSchema(new AnnotatedType(ErrorDto.class));

            openApi.getComponents().addSchemas(
                    ErrorDto.class.getSimpleName(),
                    resolvedSchema.schema
            );
        };
    }

    @Bean
    public OperationCustomizer operationCustomizer() {
        return (operation, handlerMethod) -> new RootOperationCustomizer(operation, handlerMethod).customize();
    }
}
