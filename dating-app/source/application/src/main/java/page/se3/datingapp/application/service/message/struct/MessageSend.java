package page.se3.datingapp.application.service.message.struct;

import page.se3.datingapp.domain.model.message.Message;

import java.time.Instant;

public record MessageSend(
        String message
) {

    public Message deriveInitial(Instant sendTime, Long idChat, Long idUserSender) {
        return Message.getInitial(message, sendTime, idChat, idUserSender);
    }
}
