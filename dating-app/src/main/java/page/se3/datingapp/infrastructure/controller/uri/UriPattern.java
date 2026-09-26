package page.se3.datingapp.infrastructure.controller.uri;

import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

public final class UriPattern {

    private UriPattern() {}

    public static URI current() {
        return ServletUriComponentsBuilder.fromCurrentRequest()
                .build()
                .toUri();
    }

    public static URI append(String path, Object... uriVariableValues) {
        return ServletUriComponentsBuilder.fromCurrentRequest()
                .path(path)
                .buildAndExpand(uriVariableValues)
                .toUri();
    }
}
