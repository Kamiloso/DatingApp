package page.se3.datingapp.infrastructure.controller.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import page.se3.datingapp.domain.config.app.AppConfig;
import page.se3.datingapp.domain.exception.abort.reason.endpoint.DevelopmentOnlyEndpoint;

@Service
@RequiredArgsConstructor
public class FailFast {

    private final AppConfig appConfig;

    public void abortOnProduction() throws DevelopmentOnlyEndpoint {
        if (!appConfig.isDevelopmentMode()) {
            throw new DevelopmentOnlyEndpoint();
        }
    }
}
