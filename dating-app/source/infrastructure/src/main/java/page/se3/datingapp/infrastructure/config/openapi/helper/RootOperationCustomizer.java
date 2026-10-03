package page.se3.datingapp.infrastructure.config.openapi.helper;

import io.swagger.v3.oas.models.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.method.HandlerMethod;
import page.se3.datingapp.domain.exception.abort.AbortException;
import page.se3.datingapp.domain.exception.abort.reason.account.AccountNotFound;
import page.se3.datingapp.domain.exception.abort.internal.AuthorizationFailed;
import page.se3.datingapp.infrastructure.foundation.identity.helper.IdentityParam;

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

    public Operation customize(String securitySchemeName) {
        boolean usesAuthorization = endpointAnalyzer.usesAuthorization(IdentityParam::isValid);
        boolean usesAuthKey = endpointAnalyzer.usesAuthorization(IdentityParam::usesAuthKey);

        if (usesAuthorization) {
            errorInfoRegistry.addDocumentation(new AuthorizationFailed());
            errorInfoRegistry.addSecurityRequirement(securitySchemeName);

            if (usesAuthKey) {
                errorInfoRegistry.addDocumentation(new AccountNotFound());
            }
        }

        List<AbortException> exceptions = endpointAnalyzer.getDeclaredAbortExceptions();
        exceptions.forEach(errorInfoRegistry::addDocumentation);

        return operation;
    }
}
