package page.se3.datingapp.infrastructure.controller.listener.matching;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import page.se3.datingapp.application.annotation.AutoRetry;
import page.se3.datingapp.application.service.matching.MatchingUseCase;

@Service
@AutoRetry
@RequiredArgsConstructor
public class MatchingProxy {

    private final MatchingUseCase matchingUseCase;

    public void runMatchingSchedule() {
        matchingUseCase.execute();
    }
}
