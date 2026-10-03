package page.se3.datingapp.infrastructure.config.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import page.se3.datingapp.domain.exception.abort.AbortException;

@RestControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(AbortException.class)
    public ProblemDetail handleAbortException(AbortException ex) {
        HttpStatus httpStatus = HttpStatus.valueOf(ex.getStatus());

        ProblemDetail problem = ProblemDetail.forStatusAndDetail(httpStatus, ex.getMessage());
        problem.setProperty("code", ex.getCode());

        return problem;
    }
}
