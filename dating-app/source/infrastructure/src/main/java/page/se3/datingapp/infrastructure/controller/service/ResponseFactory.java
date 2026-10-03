package page.se3.datingapp.infrastructure.controller.service;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import page.se3.datingapp.infrastructure.controller.service.helper.Response;

import java.net.URI;

@Service
public final class ResponseFactory {

    public <T> Response<T> created(T body) {
        return new Response<>(ResponseEntity.created(Uri.current()).body(body));
    }

    public <T> Response<T> created(T body, Long id) {
        return new Response<>(ResponseEntity.created(Uri.withId(id)).body(body));
    }

    public Response<Void> noContent() {
        return new Response<>(ResponseEntity.noContent().build());
    }

    public Response<Void> accepted() {
        return new Response<>(ResponseEntity.accepted().build());
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
