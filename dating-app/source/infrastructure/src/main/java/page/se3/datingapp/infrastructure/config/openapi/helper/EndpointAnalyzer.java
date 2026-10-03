package page.se3.datingapp.infrastructure.config.openapi.helper;

import lombok.RequiredArgsConstructor;
import org.springframework.web.method.HandlerMethod;
import page.se3.datingapp.domain.exception.abort.AbortException;
import page.se3.datingapp.infrastructure.foundation.identity.helper.IdentityParam;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

@RequiredArgsConstructor
public class EndpointAnalyzer {

    private final HandlerMethod handlerMethod;

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
