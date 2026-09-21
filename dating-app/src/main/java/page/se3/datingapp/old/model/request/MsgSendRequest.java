package page.se3.datingapp.old.model.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import page.se3.datingapp.old.infrastructure.util.Final;
import page.se3.datingapp.old.model.struct.post.MessagePost;

import java.time.Instant;

@Schema(description = "Represents a request to send a message")
public record MsgSendRequest(
        @NotNull
        @Size(max = Final.LEN_2047)
        String message
) {

    public MessagePost derivePost(Long idUserSender, Long idChat) {
        Instant sendTime = Instant.now();
        return new MessagePost(message, sendTime, idUserSender, idChat);
    }
}
