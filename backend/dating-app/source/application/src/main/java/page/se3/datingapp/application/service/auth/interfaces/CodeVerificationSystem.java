package page.se3.datingapp.application.service.auth.interfaces;

import java.time.Duration;
import java.util.Optional;

public interface CodeVerificationSystem {

    String obtainAndRemember(String email, Duration duration);

    Optional<String> verifyAndTakeEmail(String code);
}
