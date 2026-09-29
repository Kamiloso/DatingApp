package page.se3.datingapp.infrastructure.controller.listener.matching;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import page.se3.datingapp.application.annotation.AutoRetry;
import page.se3.datingapp.application.service.matching.MatchingUseCase;
import page.se3.datingapp.infrastructure.controller.uri.Response;

@Slf4j
@Service
@AutoRetry
@RequiredArgsConstructor
public class MatchingProxy {

    private final MatchingUseCase matchingUseCase;

    public ResponseEntity<@NonNull Void> runMatching() {
        matchingUseCase.execute();
        return Response.noContent();
    }
}
