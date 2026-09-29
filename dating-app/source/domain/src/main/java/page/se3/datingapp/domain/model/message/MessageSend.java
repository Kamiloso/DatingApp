package page.se3.datingapp.domain.model.message;

import java.time.Instant;

public record MessageSend(
        String message
) {

    public Message deriveInitial(Instant sendTime, Long idChat, Long idUserSender) {
        return Message.getInitial(message, sendTime, idChat, idUserSender);
    }
}
