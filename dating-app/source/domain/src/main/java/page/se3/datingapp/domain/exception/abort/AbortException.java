package page.se3.datingapp.domain.exception.abort;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import page.se3.datingapp.domain.core.util.Naming;

@Getter
public abstract class AbortException extends Exception {

    @Getter
    @RequiredArgsConstructor
    public enum HttpResponse {
        BAD_REQUEST(400),
        UNAUTHORIZED(401),
        FORBIDDEN(403),
        NOT_FOUND(404),
        CONFLICT(409);

        private final int status;
    }

    private final HttpResponse httpResponse;
    private final String errorType;

    public AbortException(HttpResponse httpResponse) {
        this(httpResponse, "");
    }

    public AbortException(HttpResponse httpResponse, String message) {
        super(message);
        this.httpResponse = httpResponse;
        this.errorType = Naming.pascalToSnakeCase(getClass().getSimpleName());
    }

    public int getStatus() {
        return httpResponse.getStatus();
    }
}
