package page.se3.datingapp.domain.exception.abort;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import page.se3.datingapp.domain.core.util.StringUtils;

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
        this.httpResponse = httpResponse;
        this.errorType = StringUtils.pascalToSnakeCase(this.getClass().getSimpleName());
    }

    public int getStatus() {
        return httpResponse.getStatus();
    }
}
