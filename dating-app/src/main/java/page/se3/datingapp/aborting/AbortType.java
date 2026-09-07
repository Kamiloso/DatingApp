package page.se3.datingapp.aborting;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Getter
@RequiredArgsConstructor
public enum AbortType {
    INVALID_PARAMETER_OR_BODY(
            HttpStatus.BAD_REQUEST,
            "Invalid parameter or body"
    ),
    TOO_MANY_REQUESTS(
            HttpStatus.TOO_MANY_REQUESTS,
            "Too many requests"
    ),
    USER_ALREADY_EXISTS(
            HttpStatus.CONFLICT,
            "User already exists"
    ),
    USER_NOT_FOUND(
            HttpStatus.NOT_FOUND,
            "User not found"
    ),
    CHAT_NOT_FOUND(
            HttpStatus.NOT_FOUND,
            "Chat not found"
    );

    private final HttpStatus httpStatus;
    private final String message;

    public ResponseEntity<@NonNull String> responseEntity() {
        return new AbortResponseException(this).responseEntity();
    }
}
