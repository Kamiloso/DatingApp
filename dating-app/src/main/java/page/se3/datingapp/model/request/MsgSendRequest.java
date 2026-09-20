package page.se3.datingapp.model.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import page.se3.datingapp.infrastructure.util.Final;

@Schema(description = "Represents a request to send a message")
public record MsgSendRequest(
        @NotNull
        @Size(max = Final.LEN_2047)
        String message
) {}
