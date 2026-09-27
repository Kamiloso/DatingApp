package page.se3.datingapp.infrastructure.controller.listener.info;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import page.se3.datingapp.application.annotation.AutoRetry;
import page.se3.datingapp.infrastructure.controller.listener.info.dto.ApiVersionDto;
import page.se3.datingapp.domain.config.AppConfig;

@Service
@AutoRetry
@RequiredArgsConstructor
public class InfoProxy {

    private final AppConfig appConfig;

    public ApiVersionDto getApiVersion() {
        return new ApiVersionDto(appConfig.apiVersion());
    }
}
