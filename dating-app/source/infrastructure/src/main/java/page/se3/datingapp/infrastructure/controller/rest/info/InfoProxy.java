package page.se3.datingapp.infrastructure.controller.rest.info;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import page.se3.datingapp.application.annotation.AutoRetry;
import page.se3.datingapp.domain.Final;
import page.se3.datingapp.infrastructure.controller.rest.info.dto.ApiVersionDto;

@Service
@AutoRetry
@RequiredArgsConstructor
public class InfoProxy {

    public ApiVersionDto getApiVersion() {
        return new ApiVersionDto(Final.API_VERSION);
    }
}
