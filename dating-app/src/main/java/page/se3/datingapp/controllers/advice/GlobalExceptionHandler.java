package page.se3.datingapp.controllers.advice;

import jakarta.validation.ConstraintViolationException;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;
import page.se3.datingapp.aborting.AbortResponseException;
import page.se3.datingapp.aborting.AbortType;

@RestControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler {

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<@NonNull String> handleInvalidParameterOrBody(ConstraintViolationException e) {
        return AbortType.INVALID_PARAMETER_OR_BODY.responseEntity();
    }

    /*@ExceptionHandler(RequestNotPermitted.class)
    public ResponseEntity<@NonNull String> handleTooManyRequests(RequestNotPermitted e) {
        return AbortType.TOO_MANY_REQUESTS.responseEntity();
    }*/

    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<@NonNull String> handleAbortResponseException(AbortResponseException e) {
        return e.responseEntity();
    }
}
