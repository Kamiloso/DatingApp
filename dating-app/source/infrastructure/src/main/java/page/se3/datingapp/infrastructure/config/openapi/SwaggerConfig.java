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
import page.se3.datingapp.domain.config.app.AppConfig;
import page.se3.datingapp.infrastructure.config.openapi.helper.RootOperationCustomizer;
import page.se3.datingapp.infrastructure.controller.annotation.AuthIdentity;
import page.se3.datingapp.infrastructure.config.controller.response.dto.ResponseError;
import page.se3.datingapp.domain.Final;

@Configuration
@RequiredArgsConstructor
public class SwaggerConfig {

    private static final String BEARER_AUTH = "BearerAuth";

    private final AppConfig appConfig;

    static {
        SpringDocUtils.getConfig().addAnnotationsToIgnore(AuthIdentity.class);
    }

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI().info(new Info()
                .title(appConfig.applicationDisplayName() + " API")
                .version(Final.API_VERSION)
        );
    }

    @Bean
    public OpenApiCustomizer openApiCustomizer() {
        return openApi -> {
            if (openApi.getComponents() == null) {
                openApi.setComponents(new Components());
            }

            openApi.getComponents().addSecuritySchemes(BEARER_AUTH,
                    new SecurityScheme()
                            .type(SecurityScheme.Type.HTTP)
                            .scheme("bearer")
                            .bearerFormat("JWT")
            );

            ResolvedSchema resolvedSchema = ModelConverters.getInstance()
                    .resolveAsResolvedSchema(new AnnotatedType(ResponseError.class));

            openApi.getComponents().addSchemas(
                    ResponseError.class.getSimpleName(),
                    resolvedSchema.schema
            );
        };
    }

    @Bean
    public OperationCustomizer operationCustomizer() {
        return (operation, handlerMethod) -> new RootOperationCustomizer(operation, handlerMethod)
                .customize(BEARER_AUTH);
    }
}
