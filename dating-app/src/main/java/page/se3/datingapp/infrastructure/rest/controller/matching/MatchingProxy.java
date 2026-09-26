package page.se3.datingapp.infrastructure.rest.controller.matching;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import page.se3.datingapp.application.service.matching.MatchingUseCase;
import page.se3.datingapp.infrastructure.annotation.AutoRetry;

@Service
@RequiredArgsConstructor
public class MatchingProxy {

    private final MatchingUseCase matchingUseCase;
    
    @AutoRetry
    public void runMatchingSchedule() {
        matchingUseCase.execute();
    }
}
