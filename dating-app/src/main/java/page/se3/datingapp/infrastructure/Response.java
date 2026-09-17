package page.se3.datingapp.infrastructure;

import lombok.NonNull;
import org.springframework.http.ResponseEntity;

import java.net.URI;

public final class Response {

    private Response() {}

    public static <T> ResponseEntity<@NonNull T> ok(T body) {
        return ResponseEntity.ok().body(body);
    }

    public static <T> ResponseEntity<@NonNull T> created(T body, URI location) {
        return ResponseEntity.created(location).body(body);
    }

    public static ResponseEntity<?> noContent() {
        return ResponseEntity.noContent().build();
    }
}
