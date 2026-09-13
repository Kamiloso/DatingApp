package page.se3.datingapp.infrastructure;

import jakarta.validation.ConstraintViolationException;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;
import page.se3.datingapp.infrastructure.abort.AbortException;
import page.se3.datingapp.infrastructure.abort.reason.InvalidParameterOrBody;

@RestControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler {

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<@NonNull String> handleInvalidParameterOrBody(ConstraintViolationException e) {
        return new InvalidParameterOrBody().responseEntity();
    }

    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<@NonNull String> handleAbortResponseException(AbortException e) {
        return e.responseEntity();
    }
}
