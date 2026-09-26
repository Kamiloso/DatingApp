package page.se3.datingapp.infrastructure.rest;

import jakarta.validation.ConstraintViolationException;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import page.se3.datingapp.domain.exception.abort.AbortException;
import page.se3.datingapp.domain.exception.abort.reason.InvalidParameterOrBody;
import page.se3.datingapp.infrastructure.rest.dto.ProblemDto;

@RestControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler {

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<@NonNull ProblemDto> handleConstraintViolationException(ConstraintViolationException e) {
        return getResponseEntity(new InvalidParameterOrBody());
    }

    @ExceptionHandler(AbortException.class)
    public ResponseEntity<@NonNull ProblemDto> handleAbortException(AbortException e) {
        return getResponseEntity(e);
    }

    private static ResponseEntity<@NonNull ProblemDto> getResponseEntity(AbortException abortException) {
        int httpStatus = abortException.getStatus();
        String errorType = abortException.getErrorType();

        return ResponseEntity.status(httpStatus).body(new ProblemDto(httpStatus, errorType));
    }
}
