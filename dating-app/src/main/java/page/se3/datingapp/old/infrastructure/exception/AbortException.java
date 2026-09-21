package page.se3.datingapp.old.infrastructure.exception;

import lombok.Getter;
import lombok.NonNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import page.se3.datingapp.old.core.util.StringUtils;
import page.se3.datingapp.old.infrastructure.struct.Problem;

@Getter
public abstract class AbortException extends Exception {

    private final HttpStatus httpStatus;
    private final String errorType;

    public AbortException(HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
        this.errorType = StringUtils.pascalToSnakeCase(this.getClass().getSimpleName());
    }

    public final ResponseEntity<@NonNull Problem> responseEntity() {
        return ResponseEntity.status(httpStatus).body(new Problem(httpStatus.value(), errorType));
    }
}
