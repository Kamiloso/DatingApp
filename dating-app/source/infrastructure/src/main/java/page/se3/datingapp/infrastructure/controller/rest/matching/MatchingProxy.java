package page.se3.datingapp.infrastructure.controller.rest.matching;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import page.se3.datingapp.application.annotation.AutoRetry;
import page.se3.datingapp.application.service.matching.MatchingUseCase;
import page.se3.datingapp.domain.core.util.Reporting;
import page.se3.datingapp.domain.exception.abort.reason.endpoint.DebugOnlyEndpoint;
import page.se3.datingapp.infrastructure.controller.service.Aborting;
import page.se3.datingapp.infrastructure.controller.service.Response;

@Slf4j
@Service
@RequiredArgsConstructor
public class MatchingProxy {

    private final LocalProxy localProxy;
    private final Aborting aborting;

    public ResponseEntity<@NonNull Void> runMatchingFromController() throws DebugOnlyEndpoint {
        aborting.throwOnProduction();

        Reporting.logRunToConsole(
                "Manual matching",
                localProxy::runMatching, log::info, log::error
        );

        return Response.noContent();
    }

    public void runMatchingFromSchedule() {
        Reporting.logRunToConsole(
                "Matching schedule",
                localProxy::runMatching, log::info, log::error
        );
    }

    @Component
    @RequiredArgsConstructor
    private static class LocalProxy {

        private final MatchingUseCase matchingUseCase;

        @AutoRetry
        public void runMatching() {
            matchingUseCase.execute();
        }
    }
}
