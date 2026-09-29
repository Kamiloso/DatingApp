package page.se3.datingapp.infrastructure.controller;

import jakarta.validation.ConstraintViolationException;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import page.se3.datingapp.domain.exception.abort.AbortException;
import page.se3.datingapp.domain.exception.abort.reason.InvalidParameterOrBody;
import page.se3.datingapp.infrastructure.controller.dto.ErrorDto;

@RestControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler {

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<@NonNull ErrorDto> handleConstraintViolationException(ConstraintViolationException e) {
        return getResponseEntity(new InvalidParameterOrBody());
    }

    @ExceptionHandler(AbortException.class)
    public ResponseEntity<@NonNull ErrorDto> handleAbortException(AbortException e) {
        return getResponseEntity(e);
    }

    private static ResponseEntity<@NonNull ErrorDto> getResponseEntity(AbortException abortException) {
        int httpStatus = abortException.getStatus();
        String errorType = abortException.getErrorType();

        return ResponseEntity.status(httpStatus).body(new ErrorDto(httpStatus, errorType));
    }
}
