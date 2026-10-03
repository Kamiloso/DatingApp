package page.se3.datingapp.infrastructure.controller.service.helper;

import lombok.NonNull;
import org.springframework.http.ResponseEntity;

public record Response<T>(
        ResponseEntity<@NonNull T> get
) {}
