package page.se3.datingapp.model.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import page.se3.datingapp.model.Scheme;
import page.se3.datingapp.model.struct.post.MessagePost;

import java.time.Instant;

public record MsgSendRequest(
        @NotNull
        Long idChat,

        @NotBlank
        @Size(max = Scheme.LEN_2047)
        String message
) {

    public MessagePost upgrade(Instant sendTime, Long idUserSender) {
        return new MessagePost(message, sendTime, idUserSender, idChat);
    }
}
