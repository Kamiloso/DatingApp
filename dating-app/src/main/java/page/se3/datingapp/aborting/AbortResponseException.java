package page.se3.datingapp.aborting;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Getter
@RequiredArgsConstructor
public class AbortResponseException extends RuntimeException {

    private final HttpStatus httpStatus;
    private final String message;

    public AbortResponseException(AbortType abortType) {
        this(
                abortType.getHttpStatus(),
                abortType.getMessage()
        );
    }

    public ResponseEntity<@NonNull String> responseEntity() {
        return ResponseEntity.status(httpStatus).body(message);
    }
}
