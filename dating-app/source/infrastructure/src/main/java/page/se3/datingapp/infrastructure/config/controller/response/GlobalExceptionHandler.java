package page.se3.datingapp.infrastructure.config.controller.response;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolationException;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import page.se3.datingapp.domain.exception.abort.AbortException;
import page.se3.datingapp.domain.exception.abort.internal.InvalidParameterOrBody;
import page.se3.datingapp.infrastructure.config.controller.response.dto.ResponseError;

@RestControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<@NonNull ResponseError> handleConstraintViolationException(
            ConstraintViolationException ex,
            HttpServletRequest request
    ) {
        String uri = request.getRequestURI();
        return ResponseError.getFrom(new InvalidParameterOrBody(), uri).toResponseEntity();
    }

    @ExceptionHandler(AbortException.class)
    public ResponseEntity<@NonNull ResponseError> handleAbortException(
            AbortException ex,
            HttpServletRequest request
    ) {
        String uri = request.getRequestURI();
        return ResponseError.getFrom(ex, uri).toResponseEntity();
    }
}
