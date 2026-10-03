package page.se3.datingapp.infrastructure.controller.rest.matching;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import page.se3.datingapp.application.annotation.AutoRetry;
import page.se3.datingapp.application.service.matching.MatchingUseCase;
import page.se3.datingapp.domain.core.util.Reporting;
import page.se3.datingapp.domain.exception.abort.reason.endpoint.DebugOnlyEndpoint;
import page.se3.datingapp.infrastructure.controller.service.FailFast;
import page.se3.datingapp.infrastructure.controller.service.ResponseFactory;
import page.se3.datingapp.infrastructure.controller.struct.Response;

@Slf4j
@Service
@RequiredArgsConstructor
public class MatchingProxy {

    private final LocalProxy localProxy;
    private final ResponseFactory responseFactory;
    private final FailFast failFast;

    public Response<Void> runMatchingFromDebugController() throws DebugOnlyEndpoint {
        failFast.abortOnProduction();

        runMatchingInternal("Debug matching");
        return responseFactory.noContent();
    }

    public void runMatchingFromSchedule() {
        runMatchingInternal("Matching schedule");
    }

    private void runMatchingInternal(String subject) {
        Reporting.logRunToConsole(subject, localProxy::runMatching, log::info, log::error);
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
