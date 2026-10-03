package page.se3.datingapp.infrastructure.config.controller.response.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.NonNull;
import lombok.experimental.FieldNameConstants;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import page.se3.datingapp.domain.exception.abort.AbortException;

import java.time.Instant;

@FieldNameConstants
@Schema(description = "Represents response error information")
public record ResponseError(
        @NotNull
        Instant timestamp,

        @NotNull
        Integer status,

        @NotNull
        String error,

        @NotNull
        String errorType,

        @NotNull
        String path,

        @NotNull
        String detail
) {

    public static ResponseError getFrom(AbortException abortException, String uri) {
        int status = abortException.getStatus();

        HttpStatus httpStatus = HttpStatus.resolve(status);
        String error = httpStatus != null ? httpStatus.getReasonPhrase() : "Unknown Status";

        return new ResponseError(
                Instant.now(),
                status, error,
                abortException.getErrorType(), uri,
                abortException.getMessage()
        );
    }

    public ResponseEntity<@NonNull ResponseError> toResponseEntity() {
        return ResponseEntity.status(status).body(this);
    }
}
