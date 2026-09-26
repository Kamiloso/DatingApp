package page.se3.datingapp.infrastructure.controller.listener.info;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import page.se3.datingapp.domain.exception.abort.reason.ChatNotFound;
import page.se3.datingapp.infrastructure.controller.listener.info.dto.ApiVersionDto;

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
}
