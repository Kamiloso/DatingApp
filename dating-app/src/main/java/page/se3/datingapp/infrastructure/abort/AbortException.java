package page.se3.datingapp.infrastructure.abort;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Getter
@RequiredArgsConstructor
public abstract class AbortException extends Exception {

    private final HttpStatus httpStatus;
    private final String message;

    public ResponseEntity<@NonNull String> responseEntity() {
        return ResponseEntity.status(httpStatus).body(message);
    }
}
