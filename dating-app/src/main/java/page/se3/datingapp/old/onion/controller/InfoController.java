package page.se3.datingapp.old.onion.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import page.se3.datingapp.old.infrastructure.struct.ApiVersionInfo;
import page.se3.datingapp.old.infrastructure.util.Final;

@RestController
@Validated
@Tag(name = "Info")
@RequestMapping("/api/info")
@RequiredArgsConstructor
public class InfoController {

    @GetMapping("/version")
    @Operation(summary = "Returns current API version")
    public ApiVersionInfo getVersionInfo() {
        return new ApiVersionInfo(Final.API_VERSION);
    }
}
