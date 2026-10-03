package page.se3.datingapp.infrastructure.controller.rest.info;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import page.se3.datingapp.application.annotation.AutoRetry;
import page.se3.datingapp.domain.Final;
import page.se3.datingapp.domain.exception.abort.reason.endpoint.DebugOnlyEndpoint;
import page.se3.datingapp.infrastructure.controller.rest.info.dto.ApiVersionDto;
import page.se3.datingapp.infrastructure.controller.service.FailFast;
import page.se3.datingapp.infrastructure.controller.struct.Response;

@Service
@AutoRetry
@RequiredArgsConstructor
public class InfoProxy {

    private final FailFast failFast;

    public ApiVersionDto getApiVersion() {
        return new ApiVersionDto(Final.API_VERSION);
    }

    public Response<Void> crashRequest() throws DebugOnlyEndpoint {
        failFast.abortOnProduction();

        throw new IllegalStateException("Every state is illegal by definition");
    }
}
