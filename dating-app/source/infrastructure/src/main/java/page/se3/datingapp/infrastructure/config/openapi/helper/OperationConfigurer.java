package page.se3.datingapp.infrastructure.config.openapi.helper;

import io.swagger.v3.oas.models.Operation;
import io.swagger.v3.oas.models.media.Content;
import io.swagger.v3.oas.models.media.MediaType;
import io.swagger.v3.oas.models.media.Schema;
import io.swagger.v3.oas.models.responses.ApiResponse;
import io.swagger.v3.oas.models.responses.ApiResponses;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import org.springframework.http.HttpStatus;
import org.springframework.web.method.HandlerMethod;
import page.se3.datingapp.domain.exception.abort.AbortException;
import page.se3.datingapp.domain.exception.abort.internal.AuthorizationFailed;
import page.se3.datingapp.domain.exception.abort.reason.account.AccountNotRegistered;
import page.se3.datingapp.infrastructure.foundation.identity.helper.IdentityParam;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

public class OperationConfigurer {

    private final Operation operation;
    private final HandlerMethod handlerMethod;
    private final ApiResponses apiResponses;

    public OperationConfigurer(Operation operation, HandlerMethod handlerMethod) {
        this.operation = operation;
        this.handlerMethod = handlerMethod;
        this.apiResponses = operation.getResponses();
    }

    public Operation configure(String securitySchemeName) {
        boolean usesAuthorization = usesAuthorization(IdentityParam::isValid);
        boolean usesAuthKey = usesAuthorization(IdentityParam::usesAuthKey);

        if (usesAuthorization) {
            addDocumentation(new AuthorizationFailed());
            addSecurityRequirement(securitySchemeName);

            if (usesAuthKey) {
                addDocumentation(new AccountNotRegistered());
            }
        }

        List<AbortException> exceptions = getDeclaredAbortExceptions();
        exceptions.forEach(this::addDocumentation);

        return operation;
    }

    private boolean usesAuthorization(Predicate<IdentityParam> condition) {
        return Arrays.stream(handlerMethod.getMethodParameters())
                .anyMatch(param -> condition.test(new IdentityParam(param)));
    }

    private List<AbortException> getDeclaredAbortExceptions() {
        List<AbortException> declaredExceptions = new ArrayList<>();

        for (Class<?> exceptionType : handlerMethod.getMethod().getExceptionTypes()) {
            if (AbortException.class.isAssignableFrom(exceptionType)) {
                try {
                    declaredExceptions.add((AbortException) exceptionType.getDeclaredConstructor().newInstance());
                } catch (Exception ignored) {}
            }
        }

        return declaredExceptions;
    }

    private void addDocumentation(AbortException abortException) {
        int httpStatus = abortException.getStatus();

        String httpStatusKey = String.valueOf(httpStatus);
        String reasonPhrase = HttpStatus.valueOf(httpStatus).getReasonPhrase();
        String code = abortException.getCode();
        String message = abortException.getMessage();

        ApiResponse apiResponse = apiResponses.computeIfAbsent(httpStatusKey, key -> new ApiResponse());

        String description = apiResponse.getDescription();
        if (description == null || description.isEmpty()) {
            description = reasonPhrase;
        }

        description += "\n\n`" + code + "` *- " + message + "*";
        apiResponse.setDescription(description);

        if (apiResponse.getContent() == null) {
            String schemaName = ResponseErrorScheme.class.getSimpleName();
            Schema<?> schema = new Schema<>().$ref("#/components/schemas/" + schemaName);

            MediaType mediaType = new MediaType().schema(schema);
            Content content = new Content().addMediaType("application/json", mediaType);

            apiResponse.setContent(content);
        }
    }

    private void addSecurityRequirement(String schemeName) {
        operation.addSecurityItem(new SecurityRequirement().addList(schemeName));
    }
}
