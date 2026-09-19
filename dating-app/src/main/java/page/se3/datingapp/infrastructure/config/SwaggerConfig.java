package page.se3.datingapp.infrastructure.config;

import io.swagger.v3.core.converter.AnnotatedType;
import io.swagger.v3.core.converter.ModelConverters;
import io.swagger.v3.core.converter.ResolvedSchema;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.Operation;
import io.swagger.v3.oas.models.media.Content;
import io.swagger.v3.oas.models.media.MediaType;
import io.swagger.v3.oas.models.media.Schema;
import io.swagger.v3.oas.models.responses.ApiResponse;
import io.swagger.v3.oas.models.responses.ApiResponses;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springdoc.core.customizers.OpenApiCustomizer;
import org.springdoc.core.customizers.OperationCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.method.HandlerMethod;
import page.se3.datingapp.infrastructure.abort.reason.AuthorizationFailed;
import page.se3.datingapp.model.response.Problem;
import page.se3.datingapp.infrastructure.abort.AbortException;
import page.se3.datingapp.infrastructure.abort.reason.InvalidParameterOrBody;

import java.util.Arrays;

@Configuration
public class SwaggerConfig {

    private static final String SECURITY_SCHEME_NAME = "BearerAuth";

    @Bean
    public OpenApiCustomizer openApiCustomizer() {
        return openApi -> {
            if (openApi.getComponents() == null) {
                openApi.setComponents(new Components());
            }

            openApi.getComponents().addSecuritySchemes(SECURITY_SCHEME_NAME,
                    new SecurityScheme()
                            .type(SecurityScheme.Type.HTTP)
                            .scheme("bearer")
                            .bearerFormat("JWT")
            );

            ResolvedSchema resolvedSchema = ModelConverters.getInstance()
                    .resolveAsResolvedSchema(new AnnotatedType(Problem.class));

            openApi.getComponents().addSchemas(
                    Problem.class.getSimpleName(),
                    resolvedSchema.schema
            );
        };
    }

    @Bean
    public OperationCustomizer operationCustomizer() {
        return (Operation operation, HandlerMethod handlerMethod) -> {
            ApiResponses apiResponses = operation.getResponses();

            if (requiresValidation(handlerMethod)) {
                registerApiResponse(apiResponses, new InvalidParameterOrBody());
            }

            if (requiresAuth(handlerMethod)) {
                registerApiResponse(apiResponses, new AuthorizationFailed());
                operation.addSecurityItem(new SecurityRequirement().addList(SECURITY_SCHEME_NAME));
            }

            Class<?>[] checkedExceptionTypes = handlerMethod.getMethod().getExceptionTypes();

            for (Class<?> exceptionType : checkedExceptionTypes) {
                if (AbortException.class.isAssignableFrom(exceptionType)) {
                    AbortException abortException;

                    try {
                        abortException = (AbortException) exceptionType.getDeclaredConstructor().newInstance();
                    } catch (Exception e) {
                        continue;
                    }

                    registerApiResponse(apiResponses, abortException);
                }
            }

            return operation;
        };
    }

    private void registerApiResponse(ApiResponses apiResponses, AbortException abortException) {
        HttpStatus httpStatus = abortException.getHttpStatus();
        String errorType = abortException.getErrorType();

        String httpStatusKey = String.valueOf(httpStatus.value());
        String reasonPhrase = httpStatus.getReasonPhrase();

        ApiResponse targetResponse = apiResponses.containsKey(httpStatusKey)
                ? apiResponses.get(httpStatusKey)
                : new ApiResponse();

        String oldDescription = targetResponse.getDescription();

        targetResponse.setDescription(oldDescription == null || oldDescription.isEmpty()
                ? reasonPhrase + "\n\n`" + errorType + "`"
                : oldDescription + ", `" + errorType + "`"
        );

        if (targetResponse.getContent() == null) {
            Schema<?> schemaReference = new Schema<>().$ref("#/components/schemas/Problem");

            MediaType mediaType = new MediaType().schema(schemaReference);
            Content content = new Content().addMediaType("application/json", mediaType);

            targetResponse.setContent(content);
        }

        apiResponses.addApiResponse(httpStatusKey, targetResponse);
    }

    private boolean requiresAuth(HandlerMethod handlerMethod) {
        return Arrays.stream(handlerMethod.getMethodParameters())
                .anyMatch(param -> param.getParameterType().equals(Jwt.class));
    }

    private boolean requiresValidation(HandlerMethod handlerMethod) {
        return Arrays.stream(handlerMethod.getMethodParameters())
                .flatMap(param -> Arrays.stream(param.getParameterAnnotations()))
                .anyMatch(annotation -> annotation.annotationType().getName().startsWith("jakarta.validation"));
    }
}
