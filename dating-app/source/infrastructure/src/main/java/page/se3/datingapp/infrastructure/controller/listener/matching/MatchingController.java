package page.se3.datingapp.infrastructure.controller.listener.matching;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import page.se3.datingapp.domain.core.util.ReportUtils;
import page.se3.datingapp.infrastructure.controller.dto.EmptyDto;

@Slf4j
@RestController
@Validated
@Tag(name = "Matching")
@RequestMapping("/internal/matching")
@RequiredArgsConstructor
public class MatchingController {

    private final MatchingProxy matchingProxy;

    @PostMapping("/next")
    @Operation(summary = "Runs matching schedule manually")
    public ResponseEntity<@NonNull Void> runMatchingSchedule(
            @RequestBody
            EmptyDto emptyDto
    ) {
        return ReportUtils.logRunToConsole(
                "Manual matching",
                matchingProxy::runMatching, log::info, log::error
        );
    }
}
