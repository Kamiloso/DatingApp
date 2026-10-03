package page.se3.datingapp.infrastructure.controller.service;

import lombok.NonNull;
import org.springframework.http.ResponseEntity;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

public final class Response {

    private Response() {}

    public static <T> ResponseEntity<@NonNull T> ok(T body) {
        return ResponseEntity.ok().body(body);
    }

    public static <T> ResponseEntity<@NonNull T> created(T body) {
        return ResponseEntity.created(Uri.current()).body(body);
    }

    public static <T> ResponseEntity<@NonNull T> created(T body, Long id) {
        return ResponseEntity.created(Uri.withId(id)).body(body);
    }

    public static ResponseEntity<@NonNull Void> noContent() {
        return ResponseEntity.noContent().build();
    }

    private interface Uri {

        static URI current() {
            return ServletUriComponentsBuilder.fromCurrentRequest()
                    .build()
                    .toUri();
        }

        static URI withId(Long id) {
            return ServletUriComponentsBuilder.fromCurrentRequest()
                    .pathSegment(String.valueOf(id))
                    .build()
                    .toUri();
        }
    }
}
