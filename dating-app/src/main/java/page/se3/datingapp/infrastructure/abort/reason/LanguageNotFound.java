package page.se3.datingapp.infrastructure.abort.reason;

import org.springframework.http.HttpStatus;
import page.se3.datingapp.infrastructure.abort.AbortException;

public final class LanguageNotFound extends AbortException {

    public LanguageNotFound() {
        super(HttpStatus.NOT_FOUND, "Language not found");
    }
}
