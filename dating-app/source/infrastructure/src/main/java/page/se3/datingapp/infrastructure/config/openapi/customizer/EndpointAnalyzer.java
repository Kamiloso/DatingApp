package page.se3.datingapp.infrastructure.config.openapi.customizer;

import lombok.RequiredArgsConstructor;
import org.springframework.web.method.HandlerMethod;
import page.se3.datingapp.domain.exception.abort.AbortException;
import page.se3.datingapp.infrastructure.config.security.identity.IdentityParam;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

@RequiredArgsConstructor
public class EndpointAnalyzer {

    private final HandlerMethod handlerMethod;

    public boolean requiresValidation() {
        return Arrays.stream(handlerMethod.getMethodParameters())
                .flatMap(param -> Arrays.stream(param.getParameterAnnotations()))
                .anyMatch(annotation -> annotation.annotationType().getName().startsWith("jakarta.validation"));
    }

    public boolean usesAuthorization(Predicate<IdentityParam> condition) {
        return Arrays.stream(handlerMethod.getMethodParameters())
                .anyMatch(param -> condition.test(new IdentityParam(param)));
    }

    public List<AbortException> getDeclaredAbortExceptions() {
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
}
