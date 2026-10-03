package page.se3.datingapp.infrastructure.controller.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import page.se3.datingapp.domain.config.app.limiting.LimitingConfig;
import page.se3.datingapp.domain.exception.abort.reason.endpoint.DebugOnlyEndpoint;

@Service
@RequiredArgsConstructor
public class FailFast {

    private final LimitingConfig limitingConfig;

    public void abortOnProduction() throws DebugOnlyEndpoint {
        if (!limitingConfig.allowDebugEndpoints()) {
            throw new DebugOnlyEndpoint();
        }
    }
}
