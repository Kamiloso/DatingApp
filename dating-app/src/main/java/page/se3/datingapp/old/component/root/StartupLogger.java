package page.se3.datingapp.old.component.root;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import page.se3.datingapp.old.infrastructure.config.AppConfig;

@Slf4j
@Component
@RequiredArgsConstructor
public class StartupLogger {

    private final AppConfig appConfig;

    @EventListener(ApplicationReadyEvent.class)
    public void onApplicationReady() {
        log.info(appConfig.startMessage());
    }
}
