package page.se3.datingapp.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import page.se3.datingapp.infrastructure.Response;

@RestController
@Validated
@Tag(name = "Ping")
@RequestMapping("/api/ping")
@RequiredArgsConstructor
public class PingController {

    @GetMapping
    @Operation(summary = "Checks if the server is alive")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<?> ping() {
        return Response.noContent();
    }
}
