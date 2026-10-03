package page.se3.datingapp.infrastructure.controller.rest.matching;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import page.se3.datingapp.domain.exception.abort.reason.endpoint.DebugOnlyEndpoint;

@Slf4j
@RestController
@Validated
@Tag(name = "Matching")
@RequestMapping("/api/matching")
@RequiredArgsConstructor
public class MatchingController {

    private final MatchingProxy matchingProxy;

    @GetMapping("/next")
    @Operation(summary = "Runs next matching manually")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<@NonNull Void> runMatching() throws DebugOnlyEndpoint {
        return matchingProxy.runMatchingFromDebugController().get();
    }
}
