package page.se3.datingapp.infrastructure;

import jakarta.validation.ConstraintViolationException;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import page.se3.datingapp.infrastructure.abort.AbortException;
import page.se3.datingapp.infrastructure.abort.reason.InvalidParameterOrBody;
import page.se3.datingapp.model.response.Problem;

@RestControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler {

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<@NonNull Problem> handleConstraintViolationException(ConstraintViolationException e) {
        return new InvalidParameterOrBody().responseEntity();
    }

    @ExceptionHandler(AbortException.class)
    public ResponseEntity<@NonNull Problem> handleAbortException(AbortException e) {
        return e.responseEntity();
    }
}
