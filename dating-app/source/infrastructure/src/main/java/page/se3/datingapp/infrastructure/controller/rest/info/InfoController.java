package page.se3.datingapp.infrastructure.controller.rest.info;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import page.se3.datingapp.domain.exception.abort.reason.endpoint.DebugOnlyEndpoint;
import page.se3.datingapp.infrastructure.controller.rest.info.dto.ApiVersionDto;

@RestController
@Validated
@Tag(name = "Info")
@RequestMapping("/api/info")
@RequiredArgsConstructor
public class InfoController {

    private final InfoProxy infoProxy;

    @GetMapping("/version")
    @Operation(summary = "Returns current API version")
    public ApiVersionDto getApiVersion() {
        return infoProxy.getApiVersion();
    }

    @GetMapping("/crashing-resource")
    @Operation(summary = "Crashes the request and returns status 500")
    public ResponseEntity<@NonNull Void> crashRequest() throws DebugOnlyEndpoint {
        return infoProxy.crashRequest().get();
    }
}
