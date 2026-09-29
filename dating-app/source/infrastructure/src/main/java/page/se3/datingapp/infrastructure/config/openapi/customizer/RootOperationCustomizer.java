package page.se3.datingapp.infrastructure.config.openapi.customizer;

import io.swagger.v3.oas.models.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.method.HandlerMethod;
import page.se3.datingapp.domain.exception.abort.AbortException;
import page.se3.datingapp.domain.exception.abort.reason.account.AccountNotFound;
import page.se3.datingapp.domain.exception.abort.reason.AuthorizationFailed;
import page.se3.datingapp.domain.exception.abort.reason.InvalidParameterOrBody;
import page.se3.datingapp.infrastructure.config.security.identity.IdentityParam;

import java.util.List;

@RequiredArgsConstructor
public class RootOperationCustomizer {

    private final Operation operation;
    private final EndpointAnalyzer endpointAnalyzer;
    private final ErrorInfoRegistry errorInfoRegistry;

    public RootOperationCustomizer(Operation operation, HandlerMethod handlerMethod) {
        this.operation = operation;
        this.endpointAnalyzer = new EndpointAnalyzer(handlerMethod);
        this.errorInfoRegistry = new ErrorInfoRegistry(operation);
    }

    public Operation customize() {
        if (endpointAnalyzer.requiresValidation()) {
            errorInfoRegistry.addDocumentation(new InvalidParameterOrBody());
        }

        boolean usesAuthorization = endpointAnalyzer.usesAuthorization(IdentityParam::isValid);
        boolean usesAuthKey = endpointAnalyzer.usesAuthorization(IdentityParam::usesAuthKey);

        if (usesAuthorization) {
            errorInfoRegistry.addDocumentation(new AuthorizationFailed());
            errorInfoRegistry.addSecurityRequirement("BearerAuth");

            if (usesAuthKey) {
                errorInfoRegistry.addDocumentation(new AccountNotFound());
            }
        }

        List<AbortException> exceptions = endpointAnalyzer.getDeclaredAbortExceptions();
        exceptions.forEach(errorInfoRegistry::addDocumentation);

        return operation;
    }
}
