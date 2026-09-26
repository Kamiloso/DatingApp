package page.se3.datingapp.infrastructure.rest.controller.info;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import page.se3.datingapp.infrastructure.rest.controller.info.dto.ApiVersionDto;
import page.se3.datingapp.domain.config.AppConfig;

@Service
@RequiredArgsConstructor
public class InfoProxy {

    private final AppConfig appConfig;

    public ApiVersionDto getApiVersion() {
        return new ApiVersionDto(appConfig.apiVersion());
    }
}
