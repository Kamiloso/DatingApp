package page.se3.datingapp.model.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import page.se3.datingapp.model.DataScheme;
import page.se3.datingapp.model.struct.post.MessagePost;

import java.time.Instant;

@Schema(description = "Represents a request to send a message")
public record MsgSendRequest(
        @NotNull
        @Size(max = DataScheme.LEN_2047)
        String message
) {

    public MessagePost upgrade(Instant sendTime, Long idUserSender, Long idChat) {
        return new MessagePost(message, sendTime, idUserSender, idChat);
    }
}
